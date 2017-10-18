package com.github.agaro1121.marshalling

import io.circe._
import io.circe.generic.semiauto._
import com.github.agaro1121.models._
import cats.syntax.functor._

trait ObjectTypeDecoders {

  implicit lazy val ChannelDecoder: Decoder[Channel] = deriveDecoder[Channel]
  implicit lazy val InitialCommentDecoder: Decoder[InitialComment] = deriveDecoder[InitialComment]
  implicit lazy val ReactionsDecoder: Decoder[Reactions] = deriveDecoder[Reactions]
  implicit lazy val FileDecoder: Decoder[File] = deriveDecoder[File]
  implicit lazy val InstantMessageDecoder: Decoder[InstantMessage] = deriveDecoder[InstantMessage]
  implicit lazy val LatestDecoder: Decoder[Latest] = deriveDecoder[Latest]
  implicit lazy val MultipartyInstantMessageDecoder: Decoder[MultipartyInstantMessage] = deriveDecoder[MultipartyInstantMessage]
  implicit lazy val TopicDecoder: Decoder[Topic] = deriveDecoder[Topic]
  implicit lazy val UserDecoder: Decoder[User] = deriveDecoder[User]
  implicit lazy val PrefsDecoder: Decoder[Prefs] = deriveDecoder[Prefs]
  implicit lazy val UserGroupDecoder: Decoder[UserGroup] = deriveDecoder[UserGroup]
  implicit val BotProfileDecoder: Decoder[BotProfile] = deriveDecoder[BotProfile]
  implicit val HumanProfileDecoder: Decoder[HumanProfile] = deriveDecoder[HumanProfile]
  implicit val ProfileDecoder: Decoder[Profile] =
    List[Decoder[Profile]](
      Decoder[BotProfile].widen,
      Decoder[HumanProfile].widen
    ).reduceLeft(_ or _)

}

object ObjectTypeDecoders extends ObjectTypeDecoders
