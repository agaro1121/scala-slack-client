package com.github.agaro1121.rtmlite.client

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws
import akka.http.scaladsl.model.ws.WebSocketRequest
import akka.stream.scaladsl.Flow
import akka.stream.Materializer
import cats.data.EitherT
import cats.implicits._
import com.github.agaro1121.core.exceptions.HttpError
import com.github.agaro1121.rtmlite.models
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

class RtmClient(implicit val actorSystem: ActorSystem, val mat: Materializer)
  extends LazyLogging
    with AbilityToConnectToRtm
    with AkkaStreamsComponents
    with UntypedActorStreamComponents {

  private def request(webSocketUrl: String): WebSocketRequest =
    WebSocketRequest(uri = webSocketUrl)

  private def getWebSocketUrl: Future[Either[HttpError, String]] =
    EitherT(rtmConnect()).map(_.url).value

  def connectWithPF(pf: PartialFunction[models.Message, models.Message]): Future[Either[HttpError, RtmStatus]] =
    connectWithPFAsync(pf.andThen(Future.successful))

  def connectWithPFAsync(pf: PartialFunction[models.Message, Future[models.Message]]): Future[Either[HttpError, RtmStatus]] =
    connectWithFlow(
      wsMessage2Json
        .via(json2SlackMessage)
        .mapAsync(Runtime.getRuntime.availableProcessors)(pf)
        .via(slackMessage2Json)
        .via(json2WsMessage)
    )

  /**
    * You can respond to Slack messages with an actor
    * that extends [[AbilityToRespondToRtm]]
    *
    * @param actorRef Your actor that responds to [[models.Message]]
    * */
  def connectWithUntypedActor(actorRef: ActorRef): Future[Either[HttpError, RtmStatus]] =
    connectWithFlow(untypedActorFlow(actorRef))

  def connectWithFlow(flow: Flow[ws.Message, ws.Message, NotUsed]): Future[Either[HttpError, RtmStatus]] = {
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
