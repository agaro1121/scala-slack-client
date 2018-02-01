package com.github.agaro1121.sharedevents.models

sealed trait Item
case class MessageItem(`type`: String, channel: ChannelId, ts: String) extends Item
case class FileItem(`type`: String, file: String) extends Item
case class FileCommentItem(`type`: String, file_comment: String, file: String) extends Item