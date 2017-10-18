package com.github.agaro1121.marshalling

import com.github.agaro1121.models.{ListUsers, WebApiResponse}
import io.circe.Encoder
import io.circe.generic.semiauto._

trait WebApiEncoders extends ObjectTypeEncoders {

  implicit val ListUsersEncoder: Encoder[ListUsers] = deriveEncoder[ListUsers]
  implicit val WebApiResponseEncoder: Encoder[WebApiResponse] = deriveEncoder[WebApiResponse]

}
