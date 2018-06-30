package com.github.agaro1121.rtmlite.models

import io.circe.generic.extras.Configuration
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._

case class Message(reply_to: Option[Long],
                    channel: String,
                    user: String,
                    text: String,
                    ts: String,
                    source_team: Option[String],
                    team: Option[String]) {
  def replyWithMessage(msg: String): Message = copy(text = msg)
}

object Message {
  implicit lazy val config: Configuration = Configuration.default.withDiscriminator("type")
  implicit lazy val MessageDecoder: Decoder[Message] = deriveDecoder[Message]
  implicit lazy val MessageEncoder: Encoder[Message] = deriveEncoder[Message]
}
