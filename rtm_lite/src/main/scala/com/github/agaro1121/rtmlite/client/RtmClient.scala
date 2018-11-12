package com.github.agaro1121.rtmlite.client

import akka.{Done, NotUsed}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws
import akka.http.scaladsl.model.ws.WebSocketRequest
import akka.stream.scaladsl.{Flow, Keep}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Materializer, Supervision}
import cats.data.EitherT
import cats.implicits._
import com.github.agaro1121.core.exceptions.HttpError
import com.github.agaro1121.sharedevents.models.SlackMessage
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.util.control.NonFatal

class RtmClient(implicit val actorSystem: ActorSystem)
  extends LazyLogging
    with AbilityToConnectToRtm
    with AkkaStreamsComponents
    with UntypedActorStreamComponents {

  val decider: Supervision.Decider = {
    case _: scala.MatchError => Supervision.resume
    case t if NonFatal(t) => Supervision.resume
    case _ => Supervision.stop
  }

  implicit val mat: Materializer = ActorMaterializer(
    ActorMaterializerSettings(actorSystem)
      .withSupervisionStrategy(decider))

  private def request(webSocketUrl: String): WebSocketRequest =
    WebSocketRequest(uri = webSocketUrl)

  private def getWebSocketUrl: Future[Either[HttpError, String]] =
    EitherT(rtmConnect()).map(_.url).value

  def connectWithPF(pf: PartialFunction[SlackMessage, SlackMessage]): Future[(RtmStatus, Future[Done])] =
    connectWithFlow(
      wsMessage2Json
        .via(json2SlackMessage)
        .collect(pf)
        .via(slackMessage2Json)
        .via(json2WsMessage)
    )

  def connectWithPFAsync(pf: PartialFunction[SlackMessage, Future[SlackMessage]]): Future[(RtmStatus, Future[Done])] =
    connectWithFlow(
      wsMessage2Json
        .via(json2SlackMessage)
        .mapAsyncUnordered(Runtime.getRuntime.availableProcessors())(pf)
        .via(slackMessage2Json)
        .via(json2WsMessage)
    )

  /**
    * You can respond to Slack messages with an actor
    * that extends [[AbilityToRespondToRtm]]
    *
    * @param actorRef Your actor that responds to [[SlackMessage]]
    * */
  def connectWithUntypedActor(actorRef: ActorRef): Future[(RtmStatus, Future[Done])] =
    connectWithFlow(untypedActorFlow(actorRef))

  def connectWithFlow(flow: Flow[ws.Message, ws.Message, NotUsed]): Future[(RtmStatus, Future[Done])] = {
    getWebSocketUrl.flatMap{
      case Right(rtmUrl) =>
        val (status, streamDone) = httpClient.singleWebSocketRequest(request(rtmUrl), flow.watchTermination()(Keep.right))
        status.map { wsu =>
          if (wsu.response.status == StatusCodes.SwitchingProtocols) {
            logger.info("successfully connected to Slack.Rtm")
            (RtmStatus.Success, streamDone)
          } else {
            logger.error("Could not connect to Slack.Rtm")
            (RtmStatus.Failure, streamDone)
          }
        }

      case Left(error) =>
        logger.error(error.msg)
        Future.successful( (RtmStatus.Failure, Future.successful(Done)) )
    }
  }

}

object RtmClient {
  def apply()(implicit actorSystem: ActorSystem): RtmClient = new RtmClient()
}
