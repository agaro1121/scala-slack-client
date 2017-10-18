package com.github.agaro1121.exceptions

import akka.http.scaladsl.model.StatusCode

sealed trait HttpError
final case class GeneralHttpException(msg: String) extends HttpError
final case class BadHttpStatus(statusCode: StatusCode, msg: String) extends HttpError