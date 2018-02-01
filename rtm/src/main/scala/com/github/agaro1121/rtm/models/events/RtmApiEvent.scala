package com.github.agaro1121.rtm.models.events

import com.github.agaro1121.core.models.{Channel, Profile}
import com.github.agaro1121.sharedevents.models.{ChannelId, UserId}
import com.github.agaro1121.rtm.models.Error

sealed trait RtmApiEvent

case class Undefined(s: String) extends RtmApiEvent
case object AccountsChanged extends RtmApiEvent
case class BotAdded(bot: Bot) extends RtmApiEvent
case class BotChanged(bot: Bot) extends RtmApiEvent
case class ChannelJoined(channel: Channel) extends RtmApiEvent
case class ChannelLeft(channelName: ChannelId) extends RtmApiEvent
case class ChannelMarked(channel: ChannelId, ts: String) extends RtmApiEvent
case class CommandsChanged(event_ts: String) extends RtmApiEvent
case class ErrorEvent(error: Error) extends RtmApiEvent
case object Goodbye extends RtmApiEvent
case class GroupJoined(channel: Channel) extends RtmApiEvent
case class GroupLeft(channel: ChannelId) extends RtmApiEvent
case class GroupMarked(channel: ChannelId, ts: String) extends RtmApiEvent
case object Hello extends RtmApiEvent
case class ImMarked(channel: ChannelId, ts: String) extends RtmApiEvent
case class ManaualPresenceChange(presence: String) extends RtmApiEvent
case class PresenceChange(user: UserId, presence: String) extends RtmApiEvent
case class PrefChange(name: String, value: String) extends RtmApiEvent
case class PresenceSub(ids: List[UserId]) extends RtmApiEvent
case object ReconnectUrl extends RtmApiEvent
case object TeamMigrationStarted extends RtmApiEvent
case class TeamPlanChange(plan: String) extends RtmApiEvent
case class TeamPrefChange(name: String, value: Boolean) extends RtmApiEvent
case class TeamProfileChange(profile: Profile) extends RtmApiEvent
case class TeamProfileDelete(profile: Profile) extends RtmApiEvent
case class TeamProfileReorder(profile: String) extends RtmApiEvent
case class UserTyping(channel: ChannelId, user: UserId) extends RtmApiEvent

//TODO: does this go here?
case class Ack(ok: Boolean, reply_to: Long, ts: String, text: String) extends RtmApiEvent

case class DesktopNotification(
  title: String,
  subtitle: String,
  msg: String,
  content: String,
  channel: String,
  launchUri: String,
  avatarImage: String,
  ssbFilename: String,
  imageUri: Option[String],
  is_shared: Boolean,
  event_ts: String
) extends RtmApiEvent