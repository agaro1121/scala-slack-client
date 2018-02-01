package com.github.agaro1121.sharedevents.models

case class FileComment(
id: String,
created: Long,
timestamp: Long,
user: UserId,
is_intro: Boolean,
comment: String)