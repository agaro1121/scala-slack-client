package com.github.agaro1121.sharedevents.models

import com.github.agaro1121.core.models.Prefs

case class Subteam(
  eventid: String,
  team_id: String,
  is_usergroup: Boolean,
  name: String,
  description: String,
  handle: String,
  is_external: Boolean,
  date_create: Long,
  date_update: Long,
  date_delete: Long,
  auto_type: String, //TODO: example was null
  created_by: String,
  updated_by: String,
  deleted_by: String, //TODO: example was null
  prefs: Prefs,
  user_count: String
)
