package com.github.agaro1121.models

case class Edited(
  user: UserId,
  ts: String
)

case class MessageEdited(
  user: UserId,
  text: String,
  edited: Edited,
  ts: String
)

case class PreviousMessage(
  user: UserId,
  text: String,
  ts: String
)
