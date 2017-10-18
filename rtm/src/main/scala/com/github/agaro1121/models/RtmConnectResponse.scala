package com.github.agaro1121.models

case class ConnectTeam(
  id: String,
  name: String,
  domain: String,
  enterprise_id: Option[String],
  enterprise_name: Option[String]
)

case class ConnectSelf(
  id: String,
  name: String
)

case class RtmConnectResponse(
  ok: Boolean,
  url: String,
  team: ConnectTeam,
  self: ConnectSelf
)