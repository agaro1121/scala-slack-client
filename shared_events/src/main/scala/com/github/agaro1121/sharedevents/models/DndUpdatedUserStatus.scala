package com.github.agaro1121.sharedevents.models

case class DndUpdatedUserStatus(
  dnd_enabled: Boolean,
  next_dnd_start_ts: Long,
  next_dnd_end_ts: Long
)

