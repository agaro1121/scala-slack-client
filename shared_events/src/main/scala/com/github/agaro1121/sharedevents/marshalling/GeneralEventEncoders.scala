package com.github.agaro1121.sharedevents.marshalling

import com.github.agaro1121.core.marshalling.ObjectTypeEncoders
import com.github.agaro1121.core.utils.JsonUtils
import com.github.agaro1121.sharedevents.models._
import io.circe.Encoder
import io.circe.generic.extras.semiauto.deriveEncoder

trait GeneralEventEncoders extends ObjectTypeEncoders with JsonUtils {

  implicit lazy val ChannelArchiveEncoder: Encoder[ChannelArchive] = deriveEncoder[ChannelArchive]
  implicit lazy val ChannelCreatedEncoder: Encoder[ChannelCreated] = deriveEncoder[ChannelCreated]
  implicit lazy val ChannelDeletedEncoder: Encoder[ChannelDeleted] = deriveEncoder[ChannelDeleted]
  implicit lazy val ChannelHistoryChangedEncoder: Encoder[ChannelHistoryChanged] = deriveEncoder[ChannelHistoryChanged]
  implicit lazy val ChannelRenameEncoder: Encoder[ChannelRename] = deriveEncoder[ChannelRename]
  implicit lazy val ChannelUnarchiveEncoder: Encoder[ChannelUnarchive] = deriveEncoder[ChannelUnarchive]
  implicit lazy val DndUpdatedDnDStatusEncoder: Encoder[DndUpdatedStatus] = deriveEncoder[DndUpdatedStatus]
  implicit lazy val DndUpdatedEncoder: Encoder[DndUpdated] = deriveEncoder[DndUpdated]
  implicit lazy val DndUpdatedUserDnDStatusEncoder: Encoder[DndUpdatedUserStatus] = deriveEncoder[DndUpdatedUserStatus]
  implicit lazy val DndUpdatedUserEncoder: Encoder[DndUpdatedUser] = deriveEncoder[DndUpdatedUser]
  implicit lazy val EmailDomainChangedEncoder: Encoder[EmailDomainChanged] = deriveEncoder[EmailDomainChanged]
  implicit lazy val EmojiChangedEncoder: Encoder[EmojiChanged] = deriveEncoder[EmojiChanged]
  implicit lazy val FileChangeFileEncoder: Encoder[FileEventFile] = deriveEncoder[FileEventFile]
  implicit lazy val FileChangeEncoder: Encoder[FileChange] = deriveEncoder[FileChange]
  implicit lazy val FileCommentEncoder: Encoder[FileComment] = deriveEncoder[FileComment]
  implicit lazy val FileCommentAddedEncoder: Encoder[FileCommentAdded] = deriveEncoder[FileCommentAdded]
  implicit lazy val FileCommentDeletedEncoder: Encoder[FileCommentDeleted] = deriveEncoder[FileCommentDeleted]
  implicit lazy val FileCommentEditedEncoder: Encoder[FileCommentEdited] = deriveEncoder[FileCommentEdited]
  implicit lazy val FileCreatedEncoder: Encoder[FileCreated] = deriveEncoder[FileCreated]
  implicit lazy val FileDeletedEncoder: Encoder[FileDeleted] = deriveEncoder[FileDeleted]
  implicit lazy val FilePublicEncoder: Encoder[FilePublic] = deriveEncoder[FilePublic]
  implicit lazy val FileSharedEncoder: Encoder[FileShared] = deriveEncoder[FileShared]
  implicit lazy val FileUnsharedEncoder: Encoder[FileUnshared] = deriveEncoder[FileUnshared]
  implicit lazy val GroupArchiveEncoder: Encoder[GroupArchive] = deriveEncoder[GroupArchive]
  implicit lazy val GroupChannelEncoder: Encoder[GroupChannel] = deriveEncoder[GroupChannel]
  implicit lazy val GroupCloseEncoder: Encoder[GroupClose] = deriveEncoder[GroupClose]
  implicit lazy val GroupHistoryChangedEncoder: Encoder[GroupHistoryChanged] = deriveEncoder[GroupHistoryChanged]
  implicit lazy val GroupOpenEncoder: Encoder[GroupOpen] = deriveEncoder[GroupOpen]
  implicit lazy val GroupRenameEncoder: Encoder[GroupRename] = deriveEncoder[GroupRename]
  implicit lazy val GroupUnarchiveEncoder: Encoder[GroupUnarchive] = deriveEncoder[GroupUnarchive]
  implicit lazy val ImCloseEncoder: Encoder[ImClose] = deriveEncoder[ImClose]
  implicit lazy val ImChannelEncoder: Encoder[ImChannel] = deriveEncoder[ImChannel]
  implicit lazy val ImCreatedEncoder: Encoder[ImCreated] = deriveEncoder[ImCreated]
  implicit lazy val ImHistoryChangedEncoder: Encoder[ImHistoryChanged] = deriveEncoder[ImHistoryChanged]
  implicit lazy val ImOpenEncoder: Encoder[ImOpen] = deriveEncoder[ImOpen]
  implicit lazy val MemberJoinedChannelEncoder: Encoder[MemberJoinedChannel] = deriveEncoder[MemberJoinedChannel]
  implicit lazy val MemberLeftChannelEncoder: Encoder[MemberLeftChannel] = deriveEncoder[MemberLeftChannel]
  implicit lazy val MessageEncoder: Encoder[Message] = deriveEncoder[Message]
  implicit lazy val PinAddedEncoder: Encoder[PinAdded] = deriveEncoder[PinAdded]
  implicit lazy val PinRemovedEncoder: Encoder[PinRemoved] = deriveEncoder[PinRemoved]
  implicit lazy val ItemEncoder: Encoder[Item] = deriveEncoder[Item]
  implicit lazy val ReactionAddedEncoder: Encoder[ReactionAdded] = deriveEncoder[ReactionAdded]
  implicit lazy val ReactionRemovedEncoder: Encoder[ReactionRemoved] = deriveEncoder[ReactionRemoved]
  implicit lazy val StarAddedEncoder: Encoder[StarAdded] = deriveEncoder[StarAdded]
  implicit lazy val StarRemovedEncoder: Encoder[StarRemoved] = deriveEncoder[StarRemoved]
  implicit lazy val SubteamEncoder: Encoder[Subteam] = deriveEncoder[Subteam]
  implicit lazy val SubteamCreatedEncoder: Encoder[SubteamCreated] = deriveEncoder[SubteamCreated]
  implicit lazy val SubteamSelfAddedEncoder: Encoder[SubteamSelfAdded] = deriveEncoder[SubteamSelfAdded]
  implicit lazy val SubteamSelfRemovedEncoder: Encoder[SubteamSelfRemoved] = deriveEncoder[SubteamSelfRemoved]
  implicit lazy val SubteamUpdatedEncoder: Encoder[SubteamUpdated] = deriveEncoder[SubteamUpdated]
  implicit lazy val TeamDomainChangeEncoder: Encoder[TeamDomainChange] = deriveEncoder[TeamDomainChange]
  implicit lazy val TeamJoinEncoder: Encoder[TeamJoin] = deriveEncoder[TeamJoin]
  implicit lazy val TeamRenameEncoder: Encoder[TeamRename] = deriveEncoder[TeamRename]
  implicit lazy val UserChangeEncoder: Encoder[UserChange] = deriveEncoder[UserChange]

}

object GeneralEventEncoders extends GeneralEventEncoders