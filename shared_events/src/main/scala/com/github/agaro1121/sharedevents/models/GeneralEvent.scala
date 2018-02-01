package com.github.agaro1121.sharedevents.models

import com.github.agaro1121.core.models.{Channel, File, User}

sealed trait GeneralEvent extends SlackEvent

case class ChannelArchive(channel: ChannelId, user: UserId) extends GeneralEvent

case class ChannelCreated(channel: Channel) extends GeneralEvent

case class ChannelDeleted(channel: ChannelId) extends GeneralEvent

case class ChannelHistoryChanged(
                                  latest: String, //TODO: "1358877455.000010" - TS ??
                                  ts: String, //"1361482916.000003"
                                  event_ts: String //TODO: "1361482916.000004"
                                ) extends GeneralEvent

case class ChannelRename(channel: String) extends GeneralEvent

case class ChannelUnarchive(channel: ChannelId, user: UserId) extends GeneralEvent

case class EmailDomainChanged(
                               email_domain: String,
                               event_ts: String //ts
                             ) extends GeneralEvent

case class EmojiChanged(
                         subtype: String, //TODO: add/remove
                         name: Option[String],
                         names: Option[List[String]], //TODO: not sure if i need this
                         event_ts: String
                       ) extends GeneralEvent

case class FileChange(file_id: String,
                      user_id: UserId,
                      file: FileEventFile,
                      event_ts: String) extends GeneralEvent

case class FileCommentAdded(file: FileEventFile, comment: FileComment) extends GeneralEvent

case class FileCommentDeleted(file: File, comment: String) extends GeneralEvent

case class FileCommentEdited(file: File, comment: String) extends GeneralEvent

case class FileCreated(file: File) extends GeneralEvent

case class FileDeleted(
                        file_id: String,
                        event_ts: String
                      ) extends GeneralEvent

case class FilePublic(file_id: String,
                      user_id: String,
                      file: FileEventFile, //TODO: jsonObject
                      event_ts: String
                     ) extends GeneralEvent

case class FileShared(file_id: String,
                      user_id: String,
                      file: FileEventFile,
                      event_ts: String) extends GeneralEvent

case class FileUnshared(file: File) extends GeneralEvent

case class GroupArchive(channel: ChannelId) extends GeneralEvent

case class UserChange(
                       user: User
                     ) extends GeneralEvent

case class GroupChannel(id: ChannelId, name: String, crated: Long) extends GeneralEvent

case class GroupClose(user: UserId, channel: ChannelId) extends GeneralEvent

case class GroupHistoryChanged(

                                latest: String, //ts
                                ts: String,
                                event_ts: String
                              ) extends GeneralEvent

case class GroupOpen(

                      user: UserId,
                      channel: ChannelId
                    ) extends GeneralEvent

case class GroupRename(

                        channel: GroupChannel //TODO: declared somewhere else?
                      ) extends GeneralEvent

case class GroupUnarchive(

                           channel: ChannelId
                         ) extends GeneralEvent

case class ImClose(
                    user: UserId,
                    channel: ChannelId
                  ) extends GeneralEvent

case class ImCreated(
                      user: UserId,
                      channel: ImChannel,
                      event_ts: String
                    ) extends GeneralEvent

case class ImHistoryChanged(
                             latest: String, //ts
                             ts: String, //ts
                             event_ts: String //ts
                           ) extends GeneralEvent

case class ImOpen(
                   user: UserId,
                   channel: ChannelId
                 ) extends GeneralEvent

case class MemberJoinedChannel(
                                user: UserId,
                                channel: ChannelId,
                                channel_type: String,
                                inviter: UserId
                              ) extends GeneralEvent

case class MemberLeftChannel(
                              user: UserId,
                              channel: ChannelId,
                              channel_type: String
                            ) extends GeneralEvent

case class TeamRename(
                       name: String
                     ) extends GeneralEvent

case class PinAdded(
                     user: UserId,
                     channel_id: String,
                     item: String,
                     event_ts: String
                   ) extends GeneralEvent

case class PinRemoved(
                       user: UserId,
                       channel_id: String,
                       item: String,
                       has_pins: Boolean,
                       event_ts: String
                     ) extends GeneralEvent

case class ReactionAdded(
                          user: UserId,
                          reaction: String,
                          item_user: Option[String],
                          item: Item, //TODO: what to do with this?
                          event_ts: String
                        ) extends GeneralEvent

case class ReactionRemoved(
                            user: UserId,
                            reaction: String,
                            item_user: Option[String],
                            item: Item,
                            event_ts: String
                          ) extends GeneralEvent

case class TeamJoin(user: User, cache_ts: Long, event_ts: String) extends GeneralEvent

case class StarAdded(
                      user: UserId,
                      item: String, //TODO: what is this?
                      event_ts: String
                    ) extends GeneralEvent
case class StarRemoved(
                        user: UserId,
                        item: String, //TODO: what is this?
                        event_ts: String
                      ) extends GeneralEvent
case class SubteamCreated(
                           subteam: Subteam
                         ) extends GeneralEvent
case class SubteamSelfAdded(
                             subteam_id: String
                           ) extends GeneralEvent
case class SubteamSelfRemoved(
                               subteam_id: String
                             ) extends GeneralEvent
case class SubteamUpdated(
                           subteam: Subteam
                         ) extends GeneralEvent
case class TeamDomainChange(
                             url: String,
                             domain: String
                           ) extends GeneralEvent
case class DndUpdatedUser( user: UserId, dnd_status: DndUpdatedUserStatus) extends GeneralEvent
case class DndUpdated(user: UserId, dnd_status: DndUpdatedStatus) extends GeneralEvent

case class EditedMessage(
                               message: MessageEdited,
                               subtype: String,
                               hidden: Boolean,
                               channel: String,
                               previous_message: PreviousMessage,
                               event_ts: String,
                               ts: String
                             ) extends GeneralEvent
case class Message(
                       reply_to: Option[Long],
                       channel: ChannelId,
                       user: UserId,
                       text: String,
                       ts: String,
                       source_team: Option[String],
                       team: Option[String]
                     ) extends GeneralEvent {
  def replyWithMessage(msg: String): Message = copy(text = msg)
}

case class BotMessage(
                       text: String,
                       bot_id: String,
                       attachments: List[Attachment],
                       subtype: String,
                       team: String,
                       channel: String,
                       event_ts: String,
                       ts: String
                     ) extends GeneralEvent