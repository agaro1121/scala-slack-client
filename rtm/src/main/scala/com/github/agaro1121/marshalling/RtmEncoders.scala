package com.github.agaro1121.marshalling

import io.circe.Encoder
import io.circe.generic.extras.semiauto._
import com.github.agaro1121.models.events._
import com.github.agaro1121.models._
import com.github.agaro1121.utils.JsonUtils
import com.github.agaro1121.marshalling.ObjectTypeEncoders.ChannelEncoder
import GeneralEventEncoders._

trait RtmEncoders extends JsonUtils {

  implicit lazy val AccountsChangedEncoder: Encoder[AccountsChanged.type] = deriveEncoder[AccountsChanged.type]
  implicit lazy val IconsEncoder: Encoder[Icons] = deriveEncoder[Icons]
  implicit lazy val BotEncoder: Encoder[Bot] = deriveEncoder[Bot]
  implicit lazy val BotAddedEncoder: Encoder[BotAdded] = deriveEncoder[BotAdded]
  implicit lazy val BotChangedEncoder: Encoder[BotChanged] = deriveEncoder[BotChanged]
  implicit lazy val ChannelJoinedEncoder: Encoder[ChannelJoined] = deriveEncoder[ChannelJoined]
  implicit lazy val ChannelLeftEncoder: Encoder[ChannelLeft] = deriveEncoder[ChannelLeft]
  implicit lazy val ChannelMarkedEncoder: Encoder[ChannelMarked] = deriveEncoder[ChannelMarked]
  implicit lazy val CommandsChangedEncoder: Encoder[CommandsChanged] = deriveEncoder[CommandsChanged]
  implicit lazy val GoodbyeEncoder: Encoder[Goodbye.type] = deriveEncoder[Goodbye.type]
  implicit lazy val GroupJoinedEncoder: Encoder[GroupJoined] = deriveEncoder[GroupJoined]
  implicit lazy val GroupLeftEncoder: Encoder[GroupLeft] = deriveEncoder[GroupLeft]
  implicit lazy val GroupMarkedEncoder: Encoder[GroupMarked] = deriveEncoder[GroupMarked]
  implicit lazy val HelloEncoder: Encoder[Hello.type] = deriveEncoder[Hello.type]
  implicit lazy val ImMarkedEncoder: Encoder[ImMarked] = deriveEncoder[ImMarked]
  implicit lazy val ManaualPresenceChangeEncoder: Encoder[ManaualPresenceChange] = deriveEncoder[ManaualPresenceChange]
  implicit lazy val PrefChangeEncoder: Encoder[PrefChange] = deriveEncoder[PrefChange]
  implicit lazy val PresenceChangeEncoder: Encoder[PresenceChange] = deriveEncoder[PresenceChange]
  implicit lazy val PresenceSubEncoder: Encoder[PresenceSub] = deriveEncoder[PresenceSub]
  implicit lazy val ReconnectUrlEncoder: Encoder[ReconnectUrl.type] = deriveEncoder[ReconnectUrl.type]
  implicit lazy val TeamMigrationStartedEncoder: Encoder[TeamMigrationStarted.type] = deriveEncoder[TeamMigrationStarted.type]
  implicit lazy val TeamPlanChangeEncoder: Encoder[TeamPlanChange] = deriveEncoder[TeamPlanChange]
  implicit lazy val TeamPrefChangeEncoder: Encoder[TeamPrefChange] = deriveEncoder[TeamPrefChange]
  implicit lazy val TeamProfileChangeEncoder: Encoder[TeamProfileChange] = deriveEncoder[TeamProfileChange]
  implicit lazy val TeamProfileDeleteEncoder: Encoder[TeamProfileDelete] = deriveEncoder[TeamProfileDelete]
  implicit lazy val TeamProfileReorderEncoder: Encoder[TeamProfileReorder] = deriveEncoder[TeamProfileReorder]
  implicit lazy val UserTypingEncoder: Encoder[UserTyping] = deriveEncoder[UserTyping]
  implicit lazy val ErrorEncoder: Encoder[Error] = deriveEncoder[Error]
  implicit lazy val ErrorEventEncoder: Encoder[ErrorEvent] = deriveEncoder[ErrorEvent]


  implicit lazy val AckEncoder: Encoder[Ack] = deriveEncoder[Ack]
  implicit lazy val DesktopNotificationEncoder: Encoder[DesktopNotification] = deriveEncoder[DesktopNotification]
}

object RtmEncoders extends RtmEncoders