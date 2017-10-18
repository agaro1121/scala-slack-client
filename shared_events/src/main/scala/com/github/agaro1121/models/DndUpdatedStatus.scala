package com.github.agaro1121.models

case class DndUpdatedStatus(
  dnd_enabled: Boolean,
  next_dnd_start_ts: Long,
  next_dnd_end_ts: Long,
  snooze_enabled: Boolean,
  snooze_endtime: Long
)

