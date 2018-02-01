package com.github.agaro1121.core.models

import io.circe.JsonObject

case class Prefs(
                  channels: List[Channel],
                  groups: List[/*Group*/JsonObject] // TODO: in rtm
)

case class UserGroup(
  id: String,
  team_id: String,
  is_usergroup: Boolean,
  name: String,
  description: String,
  handle: String,
  is_external: Boolean,
  date_create: Double,
  date_update: Double,
  date_delete: Double,
  auto_type: String,
  created_by: String,
  updated_by: String,
  deleted_by: String,
  prefs: Prefs,
  users: List[String],
  user_count: String
)
