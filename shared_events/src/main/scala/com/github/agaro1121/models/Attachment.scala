package com.github.agaro1121.models

case class Attachment(
  fallback: String,
  text: String,
  pretext: String,
  id: Double,
  color: String,
  mrkdwn_in: List[String]
)
