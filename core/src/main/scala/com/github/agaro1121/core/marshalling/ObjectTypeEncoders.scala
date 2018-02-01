package com.github.agaro1121.core.marshalling

import com.github.agaro1121.core.models._
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

trait ObjectTypeEncoders {

  implicit lazy val ChannelEncoder: Encoder[Channel] = deriveEncoder[Channel]
  implicit lazy val InitialCommentEncoder: Encoder[InitialComment] = deriveEncoder[InitialComment]
  implicit lazy val ReactionsEncoder: Encoder[Reactions] = deriveEncoder[Reactions]
  implicit lazy val FileEncoder: Encoder[File] = deriveEncoder[File]
  implicit lazy val InstantMessageEncoder: Encoder[InstantMessage] = deriveEncoder[InstantMessage]
  implicit lazy val LatestEncoder: Encoder[Latest] = deriveEncoder[Latest]
  implicit lazy val MultipartyInstantMessageEncoder: Encoder[MultipartyInstantMessage] = deriveEncoder[MultipartyInstantMessage]
  implicit lazy val ProfileEncoder: Encoder[Profile] = deriveEncoder[Profile]
  implicit lazy val TopicEncoder: Encoder[Topic] = deriveEncoder[Topic]
  implicit lazy val UserEncoder: Encoder[User] = deriveEncoder[User]
  implicit lazy val PrefsEncoder: Encoder[Prefs] = deriveEncoder[Prefs]
  implicit lazy val UserGroupEncoder: Encoder[UserGroup] = deriveEncoder[UserGroup]

}

object ObjectTypeEncoders extends ObjectTypeEncoders