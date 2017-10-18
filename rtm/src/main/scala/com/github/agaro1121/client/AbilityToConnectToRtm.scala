package com.github.agaro1121.client

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.github.agaro1121.config.RtmConfig
import com.github.agaro1121.exceptions.HttpError
import com.github.agaro1121.http.HttpClientPlumbing
import com.github.agaro1121.marshalling.RtmConnectResponseDecoders
import com.github.agaro1121.models.RtmConnectResponse
import scala.concurrent.Future

trait AbilityToConnectToRtm
  extends HttpClientPlumbing
  with RtmConnectResponseDecoders {

  val rtmConfig: RtmConfig = RtmConfig.default
  implicit protected def actorSystem: ActorSystem
  implicit protected def mat: Materializer

  private val slackBotToken: String = slackClientConfig.botToken
  private val slackBotTokenQueryParam = Some(Map("token" -> slackBotToken))

  /**
    * @param presenceSub        Only deliver presence events
    *                           when requested by subscription.
    *                           See presence subscriptions.
    *                           default=false
    * @param batchPresenceAware Group presence change notices
    *                           as presence_change_batch events
    *                           when possible. See batching.
    *                           default=false
    *
    **/
  def rtmConnect(
                  presenceSub: Option[Boolean] = None,
                  batchPresenceAware: Option[Int] = None
                ): Future[Either[HttpError, RtmConnectResponse]] =
    getAndHandleResponse(rtmConfig.rtmConnect, slackBotTokenQueryParam).as[RtmConnectResponse]

}
