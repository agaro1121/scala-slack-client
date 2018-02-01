package com.github.agaro1121.core.models


case class Channel(
  id: String,
  name: String,
  is_channel: String,
  created: Double,
  creator: String,
  is_archived: Boolean,
  is_general: Boolean,
  members: List[String],
  topic: Topic,
  purpose: Topic,
  is_member: Boolean,
  last_read: String,
  latest: Latest,
  unread_count: Double,
  unread_count_display: Double,
  previous_names: Option[List[String]] //TODO: What is this?
)
