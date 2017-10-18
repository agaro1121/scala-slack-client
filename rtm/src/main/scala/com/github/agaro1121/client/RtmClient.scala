package com.github.agaro1121.client

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message, WebSocketRequest}
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import com.github.agaro1121.exceptions.HttpError
import scala.concurrent.Future
import com.github.agaro1121.models._
import events._
import com.typesafe.scalalogging.LazyLogging
import cats.data.EitherT
import cats.implicits._
import shapeless._

class RtmClient(implicit val actorSystem: ActorSystem, val mat: Materializer)
  extends LazyLogging with AkkaStreamsComponents with AbilityToConnectToRtm {

  private def request(webSocketUrl: String) = WebSocketRequest(uri = webSocketUrl)

  private def getWebSocketUrl: Future[Either[HttpError, String]] =
    EitherT(rtmConnect()).map(_.url).value


  /**
    * @param usersActor Your actor that handles any of the slack RTM events you want
    *                   This actor needs to extend [[AbilityToRespondToRtm]] so
    *                   it can reply to slack
    **/
  def connect(usersActor: ActorRef): Future[Either[HttpError, RtmStatus]] = {
    /*
    * Actors reply with [[Object]] type
    * this will handle the transition to a proper type
    * */
    val actorReply2SlackEvent: Flow[Object, SlackRtmEvent, NotUsed] =
      Flow[Object].map {
        case event: GeneralEvent =>
          Coproduct[SlackRtmEvent](event)

        case event: RtmApiEvent =>
          Coproduct[SlackRtmEvent](event)
      }

    val sink: Sink[Message, NotUsed] = {
        wsMessage2SlackEvent.map {
          /*
          * This must be really bad
          * Check AkkaStreamsComponents.superRecover
          * */
          case Left(error: io.circe.Error) => error
          /*
          * Pulls the value out of the coproduct
          * And sends it to your actor
          * */
          case Right(event) => event.unify
        }
    }.to(Sink.actorRef(usersActor, PoisonPill))

    val source: Source[Message, ActorRef] = {
      Source.actorRef[SlackRtmEvent](0, OverflowStrategy.fail) //TODO: change to actorRefWithAck for backpressure support
        .mapMaterializedValue { actorThatTalksToSlack =>
          logger.info("Switching your actor to receive messages...")
          usersActor ! AbilityToRespondToRtm.ConnectedTo(actorThatTalksToSlack)
          actorThatTalksToSlack
        }.via(actorReply2SlackEvent).via(slackEvent2WsMessage)
    }

    connect(Flow.fromSinkAndSource(sink, source))
  }


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
