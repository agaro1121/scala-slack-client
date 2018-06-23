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
  extends LazyLogging
    with AbilityToConnectToRtm
    with AkkaStreamsComponents
    with UntypedActorStreamComponents {

  private def request(webSocketUrl: String) = WebSocketRequest(uri = webSocketUrl)

  private def getWebSocketUrl: Future[Either[HttpError, String]] =
    EitherT(rtmConnect()).map(_.url).value

  def connectUsingPF(pf: PartialFunction[models.Message, models.Message]): Future[Either[HttpError, RtmStatus]] =
    connectUsingPFAsync(pf.andThen(Future.successful))

  def connectUsingPFAsync(pf: PartialFunction[models.Message, Future[models.Message]]): Future[Either[HttpError, RtmStatus]] =
    connect(
      wsMessage2Json
        .via(json2SlackMessage)
        .mapAsync(Runtime.getRuntime.availableProcessors)(pf)
        .via(slackMessage2Json)
        .via(json2WsMessage)
    )

  def connectUsingUntypedActor(actorRef: ActorRef): Future[Either[HttpError, RtmStatus]] =
    connect(untypedActorFlow(actorRef))

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
