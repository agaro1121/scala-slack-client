package com.github.agaro1121.core.exceptions

import akka.http.scaladsl.model.StatusCode

sealed trait HttpError {
  def msg: String
}
final case class GeneralHttpException(msg: String) extends HttpError
final case class BadHttpStatus(statusCode: StatusCode, msg: String) extends HttpError