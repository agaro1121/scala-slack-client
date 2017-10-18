package com.github.agaro1121.marshalling

import com.github.agaro1121.models._
import io.circe.Decoder
import io.circe.generic.extras.semiauto._
import cats.syntax.functor._
import ObjectTypeDecoders._
import com.github.agaro1121.utils.JsonUtils
import io.circe.shapes._
import io.circe.generic.extras.auto._

trait GeneralEventDecoders extends JsonUtils {

  implicit lazy val ChannelArchiveDecoder: Decoder[ChannelArchive] = deriveDecoder[ChannelArchive]
  implicit lazy val ChannelCreatedDecoder: Decoder[ChannelCreated] = deriveDecoder[ChannelCreated]
  implicit lazy val ChannelDeletedDecoder: Decoder[ChannelDeleted] = deriveDecoder[ChannelDeleted]
  implicit lazy val ChannelHistoryChangedDecoder: Decoder[ChannelHistoryChanged] = deriveDecoder[ChannelHistoryChanged]
  implicit lazy val ChannelRenameDecoder: Decoder[ChannelRename] = deriveDecoder[ChannelRename]
  implicit lazy val ChannelUnarchiveDecoder: Decoder[ChannelUnarchive] = deriveDecoder[ChannelUnarchive]
  implicit lazy val DndUpdatedDnDStatusDecoder: Decoder[DndUpdatedStatus] = deriveDecoder[DndUpdatedStatus]
  implicit lazy val DndUpdatedDecoder: Decoder[DndUpdated] = deriveDecoder[DndUpdated]
  implicit lazy val DndUpdatedUserDnDStatusDecoder: Decoder[DndUpdatedUserStatus] = deriveDecoder[DndUpdatedUserStatus]
  implicit lazy val DndUpdatedUserDecoder: Decoder[DndUpdatedUser] = deriveDecoder[DndUpdatedUser]
  implicit lazy val EmailDomainChangedDecoder: Decoder[EmailDomainChanged] = deriveDecoder[EmailDomainChanged]
  implicit lazy val EmojiChangedDecoder: Decoder[EmojiChanged] = deriveDecoder[EmojiChanged]
  implicit lazy val FileChangeFileDecoder: Decoder[FileEventFile] = deriveDecoder[FileEventFile]
  implicit lazy val FileChangeDecoder: Decoder[FileChange] = deriveDecoder[FileChange]
  implicit lazy val FileCommentDecoder: Decoder[FileComment] = deriveDecoder[FileComment]
  implicit lazy val FileCommentAddedDecoder: Decoder[FileCommentAdded] = deriveDecoder[FileCommentAdded]
  implicit lazy val FileCommentDeletedDecoder: Decoder[FileCommentDeleted] = deriveDecoder[FileCommentDeleted]
  implicit lazy val FileCommentEditedDecoder: Decoder[FileCommentEdited] = deriveDecoder[FileCommentEdited]
  implicit lazy val FileCreatedDecoder: Decoder[FileCreated] = deriveDecoder[FileCreated]
  implicit lazy val FileDeletedDecoder: Decoder[FileDeleted] = deriveDecoder[FileDeleted]
  implicit lazy val FilePublicDecoder: Decoder[FilePublic] = deriveDecoder[FilePublic]
  implicit lazy val FileSharedDecoder: Decoder[FileShared] = deriveDecoder[FileShared]
  implicit lazy val FileUnsharedDecoder: Decoder[FileUnshared] = deriveDecoder[FileUnshared]
  implicit lazy val GroupArchiveDecoder: Decoder[GroupArchive] = deriveDecoder[GroupArchive]
  implicit lazy val GroupChannelDecoder: Decoder[GroupChannel] = deriveDecoder[GroupChannel]
  implicit lazy val GroupCloseDecoder: Decoder[GroupClose] = deriveDecoder[GroupClose]
  implicit lazy val GroupHistoryChangedDecoder: Decoder[GroupHistoryChanged] = deriveDecoder[GroupHistoryChanged]
  implicit lazy val GroupOpenDecoder: Decoder[GroupOpen] = deriveDecoder[GroupOpen]
  implicit lazy val GroupRenameDecoder: Decoder[GroupRename] = deriveDecoder[GroupRename]
  implicit lazy val GroupUnarchiveDecoder: Decoder[GroupUnarchive] = deriveDecoder[GroupUnarchive]
  implicit lazy val ImCloseDecoder: Decoder[ImClose] = deriveDecoder[ImClose]
  implicit lazy val ImChannelDecoder: Decoder[ImChannel] = deriveDecoder[ImChannel]
  implicit lazy val ImCreatedDecoder: Decoder[ImCreated] = deriveDecoder[ImCreated]
  implicit lazy val ImHistoryChangedDecoder: Decoder[ImHistoryChanged] = deriveDecoder[ImHistoryChanged]
  implicit lazy val ImOpenDecoder: Decoder[ImOpen] = deriveDecoder[ImOpen]
  implicit lazy val MemberJoinedChannelDecoder: Decoder[MemberJoinedChannel] = deriveDecoder[MemberJoinedChannel]
  implicit lazy val MemberLeftChannelDecoder: Decoder[MemberLeftChannel] = deriveDecoder[MemberLeftChannel]
  implicit lazy val PinAddedDecoder: Decoder[PinAdded] = deriveDecoder[PinAdded]
  implicit lazy val PinRemovedDecoder: Decoder[PinRemoved] = deriveDecoder[PinRemoved]
  implicit lazy val MessageItemDecoder: Decoder[MessageItem] = deriveDecoder[MessageItem]
  implicit lazy val FileItemDecoder: Decoder[FileItem] = deriveDecoder[FileItem]
  implicit lazy val FileCommentItemDecoder: Decoder[FileCommentItem] = deriveDecoder[FileCommentItem]
  implicit lazy val ItemDecoder: Decoder[Item] =
    List[Decoder[Item]](
      Decoder[MessageItem].widen,
      Decoder[FileItem].widen,
      Decoder[FileCommentItem].widen
    ).reduceLeft(_ or _)
  implicit lazy val ReactionAddedDecoder: Decoder[ReactionAdded] = deriveDecoder[ReactionAdded]
  implicit lazy val ReactionRemovedDecoder: Decoder[ReactionRemoved] = deriveDecoder[ReactionRemoved]
  implicit lazy val StarAddedDecoder: Decoder[StarAdded] = deriveDecoder[StarAdded]
  implicit lazy val StarRemovedDecoder: Decoder[StarRemoved] = deriveDecoder[StarRemoved]
  implicit lazy val SubteamDecoder: Decoder[Subteam] = deriveDecoder[Subteam]
  implicit lazy val SubteamCreatedDecoder: Decoder[SubteamCreated] = deriveDecoder[SubteamCreated]
  implicit lazy val SubteamSelfAddedDecoder: Decoder[SubteamSelfAdded] = deriveDecoder[SubteamSelfAdded]
  implicit lazy val SubteamSelfRemovedDecoder: Decoder[SubteamSelfRemoved] = deriveDecoder[SubteamSelfRemoved]
  implicit lazy val SubteamUpdatedDecoder: Decoder[SubteamUpdated] = deriveDecoder[SubteamUpdated]
  implicit lazy val TeamDomainChangeDecoder: Decoder[TeamDomainChange] = deriveDecoder[TeamDomainChange]
  implicit lazy val TeamJoinDecoder: Decoder[TeamJoin] = deriveDecoder[TeamJoin]
  implicit lazy val TeamRenameDecoder: Decoder[TeamRename] = deriveDecoder[TeamRename]
  implicit lazy val UserChangeDecoder: Decoder[UserChange] = deriveDecoder[UserChange]
  implicit lazy val EditedDecoder: Decoder[Edited] = deriveDecoder[Edited]
  implicit lazy val MessageEditedDecoder: Decoder[MessageEdited] = deriveDecoder[MessageEdited]
  implicit lazy val PreviousMessageDecoder: Decoder[PreviousMessage] = deriveDecoder[PreviousMessage]
  implicit lazy val MessageDecoder: Decoder[Message] = deriveDecoder[Message]
  implicit lazy val EditedMessageDecoder: Decoder[EditedMessage] = deriveDecoder[EditedMessage]
  implicit lazy val BotMessageDecoder: Decoder[BotMessage] = deriveDecoder[BotMessage]

}

object GeneralEventDecoders extends GeneralEventDecoders