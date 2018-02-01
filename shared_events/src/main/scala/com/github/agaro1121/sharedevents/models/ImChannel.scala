package com.github.agaro1121.sharedevents.models

import com.github.agaro1121.core.models.Latest

//TODO: do i need this class?
case class ImChannel(
id: String,
created: Long,
is_im: Boolean,
is_org_shared: Boolean,
user: String,
last_read: String,
latest: Option[Latest],
unread_count: Int,
unread_count_display: Int,
is_open: Boolean)