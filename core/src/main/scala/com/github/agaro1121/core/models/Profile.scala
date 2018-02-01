package com.github.agaro1121.core.models

import io.circe.{Json, JsonObject}

import scala.util.parsing.json.JSONArray


sealed trait Profile

case class BotProfile(
                       bot_id: String,
                       api_app_id: String,
                       always_active: Boolean,
                       first_name: String,
                       avatar_hash: String,
                       real_name: String,
                       real_name_normalized: String,
                       image_24: String,
                       image_32: String,
                       image_48: String,
                       image_72: String,
                       image_192: String,
                       image_512: String,
                       fields: Option[Json] //TODO - so far it's been array or object?
                     ) extends Profile

case class HumanProfile(
                         avatar_hash: String,
                         current_status: Option[String],
                         first_name: Option[String],
                         last_name: Option[String],
                         real_name: String,
                         email: Option[String],
                         skype: Option[String],
                         phone: Option[String],
                         image_24: String,
                         image_32: String,
                         image_48: String,
                         image_72: String,
                         image_192: String,
                         image_512: String,
                         fields: Option[Json] //TODO
                       ) extends Profile

/*object Profile {
  import cats.syntax.functor._
  import io.circe.Decoder
  import io.circe.generic.semiauto.deriveDecoder

  implicit val BotProfileDecoder = deriveDecoder[BotProfile]

  implicit val HumanProfileDecoder = deriveDecoder[HumanProfile]


   implicit val decodeProfile: Decoder[Profile] =
    List[Decoder[Profile]](
      Decoder[BotProfile].widen,
      Decoder[HumanProfile].widen
    ).reduceLeft(_ or _)
}*/
