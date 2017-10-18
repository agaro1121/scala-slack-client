package com.github.agaro1121.marshalling

import com.github.agaro1121.models.{ListUsers, WebApiResponse}
import io.circe.Decoder
import io.circe.generic.semiauto._

trait WebApiDecoders extends ObjectTypeDecoders {

  implicit val ListUsersDecoder: Decoder[ListUsers] = deriveDecoder[ListUsers]
  implicit val WebApiResponseDecoder: Decoder[WebApiResponse] = deriveDecoder[WebApiResponse]

}
