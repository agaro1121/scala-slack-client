package com.github.agaro1121.models

case class User(
                 id: String,
                 team_id: String,
                 name: String,
                 deleted: Boolean,
                 color: Option[String],
                 real_name: Option[String],
                 tz: Option[String],
                 tz_label: Option[String],
                 tz_offset: Option[Int],
                 profile: Profile,
                 is_admin: Option[Boolean],
                 is_owner: Option[Boolean],
                 is_primary_owner: Option[Boolean],
                 is_restricted: Option[Boolean],
                 is_ultra_restricted: Option[Boolean],
                 updated: Double,
                 has_2fa: Option[Boolean],
                 two_factor_type: Option[String]
)
