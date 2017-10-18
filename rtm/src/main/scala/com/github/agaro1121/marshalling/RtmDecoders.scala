package com.github.agaro1121.marshalling

import io.circe.Decoder
import io.circe.generic.extras.semiauto._
import com.github.agaro1121.models.events._
import com.github.agaro1121.models._
import io.circe.generic.extras.Configuration
import ObjectTypeDecoders._
import com.github.agaro1121.utils.JsonUtils

trait RtmDecoders extends JsonUtils {

  implicit lazy val AccountsChangedDecoder: Decoder[AccountsChanged.type] = deriveDecoder[AccountsChanged.type]
  implicit lazy val IconsDecoder: Decoder[Icons] = deriveDecoder[Icons]
  implicit lazy val BotDecoder: Decoder[Bot] = deriveDecoder[Bot]
  implicit lazy val BotAddedDecoder: Decoder[BotAdded] = deriveDecoder[BotAdded]
  implicit lazy val BotChangedDecoder: Decoder[BotChanged] = deriveDecoder[BotChanged]
  implicit lazy val ChannelJoinedDecoder: Decoder[ChannelJoined] = deriveDecoder[ChannelJoined]
  implicit lazy val ChannelLeftDecoder: Decoder[ChannelLeft] = deriveDecoder[ChannelLeft]
  implicit lazy val ChannelMarkedDecoder: Decoder[ChannelMarked] = deriveDecoder[ChannelMarked]
  implicit lazy val CommandsChangedDecoder: Decoder[CommandsChanged] = deriveDecoder[CommandsChanged]
  implicit lazy val GoodbyeDecoder: Decoder[Goodbye.type] = deriveDecoder[Goodbye.type]
  implicit lazy val GroupJoinedDecoder: Decoder[GroupJoined] = deriveDecoder[GroupJoined]
  implicit lazy val GroupLeftDecoder: Decoder[GroupLeft] = deriveDecoder[GroupLeft]
  implicit lazy val GroupMarkedDecoder: Decoder[GroupMarked] = deriveDecoder[GroupMarked]
  implicit lazy val HelloDecoder: Decoder[Hello.type] = deriveDecoder[Hello.type]
  implicit lazy val ImMarkedDecoder: Decoder[ImMarked] = deriveDecoder[ImMarked]
  implicit lazy val ManaualPresenceChangeDecoder: Decoder[ManaualPresenceChange] = deriveDecoder[ManaualPresenceChange]
  implicit lazy val PrefChangeDecoder: Decoder[PrefChange] = deriveDecoder[PrefChange]
  implicit lazy val PresenceChangeDecoder: Decoder[PresenceChange] = deriveDecoder[PresenceChange]
  implicit lazy val PresenceSubDecoder: Decoder[PresenceSub] = deriveDecoder[PresenceSub]
  implicit lazy val ReconnectUrlDecoder: Decoder[ReconnectUrl.type] = deriveDecoder[ReconnectUrl.type]
  implicit lazy val TeamMigrationStartedDecoder: Decoder[TeamMigrationStarted.type] = deriveDecoder[TeamMigrationStarted.type]
  implicit lazy val TeamPlanChangeDecoder: Decoder[TeamPlanChange] = deriveDecoder[TeamPlanChange]
  implicit lazy val TeamPrefChangeDecoder: Decoder[TeamPrefChange] = deriveDecoder[TeamPrefChange]
  implicit lazy val TeamProfileChangeDecoder: Decoder[TeamProfileChange] = deriveDecoder[TeamProfileChange]
  implicit lazy val TeamProfileDeleteDecoder: Decoder[TeamProfileDelete] = deriveDecoder[TeamProfileDelete]
  implicit lazy val TeamProfileReorderDecoder: Decoder[TeamProfileReorder] = deriveDecoder[TeamProfileReorder]
  implicit lazy val UserTypingDecoder: Decoder[UserTyping] = deriveDecoder[UserTyping]
  implicit lazy val ErrorDecoder: Decoder[Error] = deriveDecoder[Error]
  implicit lazy val ErrorEventDecoder: Decoder[ErrorEvent] = deriveDecoder[ErrorEvent]

  implicit lazy val AckDecoder: Decoder[Ack] = deriveDecoder[Ack]
  implicit lazy val DesktopNotificationDecoder: Decoder[DesktopNotification] = deriveDecoder[DesktopNotification]

}

object RtmDecoders extends RtmDecoders