package com.github.agaro1121.rtmlite.client

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.stream.{Materializer, OverflowStrategy}
import cats.data.EitherT
import cats.implicits._
import com.github.agaro1121.core.exceptions.HttpError
import com.github.agaro1121.core.utils.JsonUtils
import com.github.agaro1121.sharedevents.marshalling.GeneralEventDecoders.MessageDecoder
import com.github.agaro1121.sharedevents.marshalling.GeneralEventEncoders.MessageEncoder
import com.github.agaro1121.sharedevents.models
import com.typesafe.scalalogging.LazyLogging
import io.circe.parser.parse
import io.circe.syntax.EncoderOps
import io.circe.{Json, ParsingFailure}

import scala.concurrent.Future

class RtmClient(implicit val actorSystem: ActorSystem, val mat: Materializer)
  extends LazyLogging with AbilityToConnectToRtm {

  private def request(webSocketUrl: String) = WebSocketRequest(uri = webSocketUrl)

  private def getWebSocketUrl: Future[Either[HttpError, String]] =
    EitherT(rtmConnect()).map(_.url).value

  val wsMessage2Json: Flow[Message, Either[ParsingFailure, Json], NotUsed] =
    Flow[Message]
      .map { msg =>
        val json: String = msg.asTextMessage.getStrictText
        parse(json).map(JsonUtils.convertTypeFieldToCapitalCamel)
      }

  val json2WsMessage: Flow[Json, Message, NotUsed] =
    Flow[Json].map { json =>
      TextMessage.Strict(json.toString)
    }

  val slackMessage2Json: Flow[models.Message, Json, NotUsed] =
    Flow[models.Message].map{ msg =>
      msg.asJson.deepMerge(Json.obj(("type", Json.fromString("message"))))
    }

  val json2SlackMessage: Flow[Either[ParsingFailure, Json], models.Message, NotUsed] =
    Flow[Either[ParsingFailure, Json]].map {
      _.flatMap(_.as[models.Message])
    }.collect{ case t if t.isRight => t.right.get }

  /**
    * @param usersActor Your actor that handles Slack messages.
    *                   This actor needs to extend [[AbilityToRespondToRtm]] so
    *                   it can reply to slack
    **/
  def connect(usersActor: ActorRef): Future[Either[HttpError, RtmStatus]] = {

    /*
    * Actors reply with [[Object]] type
    * this will handle the transition to a proper type
    * */
    val actorReply2SlackMessage: Flow[Object, Json, NotUsed] =
      Flow[Object].map {
        case event: models.Message =>
          event.asJson
      }


    val sink: Sink[Message, NotUsed] = {
      wsMessage2Json.via(json2SlackMessage)
    }.to(Sink.actorRef(usersActor, PoisonPill))

    val source: Source[Message, ActorRef] = {
      Source.actorRef[Message](0, OverflowStrategy.fail) //TODO: change to actorRefWithAck for backpressure support
        .mapMaterializedValue { actorThatTalksToSlack =>
        logger.info("Switching your actor to receive messages...")
        usersActor ! AbilityToRespondToRtm.ConnectedTo(actorThatTalksToSlack)
        actorThatTalksToSlack
      }.via(actorReply2SlackMessage).via(json2WsMessage)
    }

    connect(Flow.fromSinkAndSource(sink, source))
  }


  def connectUsingPF(pf: PartialFunction[models.Message, models.Message]) =
    connectUsingFlow(Flow[models.Message].collect(pf))


  def connectUsingFlow(flow: Flow[models.Message, models.Message, NotUsed]) = connect(
      wsMessage2Json
        .via(json2SlackMessage)
        .via(flow)
        .via(slackMessage2Json)
        .via(json2WsMessage)
    )


  private def connect(flow: Flow[Message, Message, NotUsed]): Future[Either[HttpError, RtmStatus]] = {
    EitherT(getWebSocketUrl).map { rtmUrl =>
      httpClient.singleWebSocketRequest(request(rtmUrl), flow)._1
        .map { wsu =>
          if (wsu.response.status == StatusCodes.SwitchingProtocols) {
            logger.info("successfully connected to Slack.Rtm")
            RtmStatus.Success
          } else {
            logger.error("Could not connect to Slack.Rtm")
            RtmStatus.Failure
          }
        }
    }.leftMap(Future.successful).value.flatMap(_.bisequence)
  }



}

object RtmClient {
  def apply()(implicit actorSystem: ActorSystem, mat: Materializer): RtmClient =
    new RtmClient()
}