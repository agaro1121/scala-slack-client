package com.github.agaro1121.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.Materializer
import cats.data.EitherT
import cats.implicits._
import com.github.agaro1121.config.SlackClientConfig
import com.github.agaro1121.exceptions.{BadHttpStatus, GeneralHttpException, HttpError}
import com.typesafe.scalalogging.LazyLogging
import io.circe.Decoder
import scala.concurrent.{ExecutionContext, Future}

trait HttpClientPlumbing extends LazyLogging {

  protected implicit def genericFromJsonConverter = HttpClientPlumbing.syntax.GenericFromJsonConverter _
  protected def httpClient: HttpExt = Http()
  protected def slackClientConfig: SlackClientConfig = SlackClientConfig.fromReference
  protected implicit def actorSystem: ActorSystem
  protected implicit def mat: Materializer
  protected implicit def ec: ExecutionContext = actorSystem.dispatcher

  protected def getAndHandleResponse(endpoint: String, queryParams: Option[Map[String, String]] = None): Future[Either[Future[HttpError], ResponseEntity]] =
    handleResponse(getResponse(endpoint, queryParams))

  protected def handleResponse(response: Future[HttpResponse]): Future[Either[Future[HttpError], ResponseEntity]] = {
    response.map { httpResponse =>
        httpResponse.status match {
          case StatusCodes.OK =>
            httpResponse.entity.asRight

          case _ =>
            Unmarshal(httpResponse.entity)
              .to[String]
              .map(BadHttpStatus(httpResponse.status, _)).asLeft
        }
    }
    .recover {
      case throwable =>
        Future.successful(GeneralHttpException(throwable.getMessage)).asLeft
    }
  }

  protected def getResponse(endpoint: String, queryParams: Option[Map[String, String]]): Future[HttpResponse] =
      httpClient.singleRequest(request = createHttpRequest(slackClientConfig.apiUrl, endpoint, queryParams))

  protected def createHttpRequest(apiUrl: String, endpoint: String, queryParams: Option[Map[String, String]]): HttpRequest = {
    val uri = Uri(apiUrl).withPath(Path(endpoint)).withQuery(Query(queryParams.getOrElse(Map.empty[String, String])))
    logger.info(s"Calling url=$uri")
    HttpRequest(uri = uri)
  }

}

object HttpClientPlumbing {
  object syntax {
    implicit class GenericFromJsonConverter(val response: Future[Either[Future[HttpError], ResponseEntity]]) extends AnyVal {
      def as[T: Decoder](implicit mat: Materializer, um: Unmarshaller[ResponseEntity, T], ec: ExecutionContext)
      :Future[Either[HttpError, T]] = {
        EitherT(response)
          .map(Unmarshal(_).to[T])
          .value
          .flatMap(_.bisequence) //TODO: Recover here
      }
    }
  }
}