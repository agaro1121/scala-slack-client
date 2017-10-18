package com.github.agaro1121.client

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.github.agaro1121.config.WebApiConfig
import com.github.agaro1121.exceptions.HttpError
import com.github.agaro1121.http.HttpClientPlumbing
import com.github.agaro1121.marshalling.AllDecoders
import com.github.agaro1121.models.ListUsers
import scala.concurrent.Future

/**
 * @param webConfig   An instance of [[WebApiConfig]]
 * @param actorSystem An implicit [[ActorSystem]]
 * @param mat         An implicit Materializer (usually [[akka.stream.ActorMaterializer]])
 */
class SlackWebApiClient(webConfig: WebApiConfig)(implicit val actorSystem: ActorSystem, val mat: Materializer)
  extends HttpClientPlumbing with AllDecoders {

  import webConfig.endpoints

  private def makeAuthenticatedCall(endpoint: String) =
    getAndHandleResponse(endpoint, Option(Map("token" -> slackClientConfig.botToken)))

  /**
   * This method returns a list of all users in the team.
   * This includes deleted/deactivated users.
   */
  //TODO: Add params
  def listUsers: Future[Either[HttpError, ListUsers]] = {
    makeAuthenticatedCall(endpoints.listUsers).as[ListUsers]
  }

}

object SlackWebApiClient {
  def apply(webConfig: WebApiConfig = WebApiConfig.default)(implicit actorSystem: ActorSystem, mat: Materializer) =
    new SlackWebApiClient(webConfig)
}
