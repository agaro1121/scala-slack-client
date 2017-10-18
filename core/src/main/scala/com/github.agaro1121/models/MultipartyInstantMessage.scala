package com.github.agaro1121.models


case class MultipartyInstantMessage(
  id: String,
  name: String,
  is_mpim: Boolean,
  is_group: Boolean,
  created: Double,
  creator: String,
  members: List[String],
  last_read: String,
  latest: Latest,
  unread_count: Double,
  unread_count_display: Double
)
