package com.github.agaro1121.core.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.Materializer
import cats.syntax.either._
import com.github.agaro1121.core.config.SlackClientConfig
import com.github.agaro1121.core.exceptions.{BadHttpStatus, GeneralHttpException, HttpError}
import com.typesafe.scalalogging.LazyLogging
import io.circe.Decoder

import scala.concurrent.{ExecutionContext, Future}

trait HttpClientPlumbing extends LazyLogging {

  protected implicit val genericFromJsonConverter = HttpClientPlumbing.GenericFromJsonConverter _
  protected val httpClient: HttpExt = Http()
  protected val slackClientConfig: SlackClientConfig = SlackClientConfig.fromReference
  protected implicit def actorSystem: ActorSystem
  protected implicit def mat: Materializer
  protected implicit lazy val ec: ExecutionContext = actorSystem.dispatcher

  protected def getAndHandleResponse(endpoint: String, queryParams: Option[Map[String, String]] = None): Future[Either[HttpError, ResponseEntity]] = {
    def createHttpRequest(apiUrl: String): HttpRequest = {
      val uri = Uri(apiUrl)
        .withPath(Path(endpoint))
        .withQuery(Query(queryParams.getOrElse(Map.empty[String, String])))
      logger.info(s"Calling url=$uri")
      HttpRequest(uri = uri)
    }

    httpClient.singleRequest(request = createHttpRequest(slackClientConfig.apiUrl))
      .flatMap { httpResponse =>
        httpResponse.status match {
          case StatusCodes.OK =>
            Future.successful(httpResponse.entity.asRight[HttpError])

          case _ =>
            Unmarshal(httpResponse.entity)
              .to[String]
              .map(BadHttpStatus(httpResponse.status, _).asLeft[ResponseEntity])
        }
      }
      .recover {
        case throwable =>
          GeneralHttpException(throwable.getMessage).asLeft
      }
  }

}

object HttpClientPlumbing {

    implicit class GenericFromJsonConverter(val response: Future[Either[HttpError, ResponseEntity]]) extends AnyVal {

      def as[T: Decoder](implicit mat: Materializer,
                         um: Unmarshaller[ResponseEntity, T],
                         ec: ExecutionContext): Future[Either[HttpError, T]] = {
        import cats.implicits._
        response.flatMap { errorOrEntity =>
          errorOrEntity
            .bimap(Future.successful, Unmarshal(_).to[T])
            .bisequence
        }
      }
    }
}
