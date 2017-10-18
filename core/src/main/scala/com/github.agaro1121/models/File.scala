package com.github.agaro1121.models

case class InitialComment()

case class Reactions(
  name: String,
  count: Double,
  users: List[String]
)

case class File(
  id: String,
  created: Double,
  timestamp: Double,
  name: String,
  title: String,
  mimetype: String,
  filetype: String,
  pretty_type: String,
  user: String,
  mode: String,
  editable: Boolean,
  is_external: Boolean,
  external_type: String,
  username: String,
  size: Double,
  url_private: String,
  url_private_download: String,
  thumb_64: String,
  thumb_80: String,
  thumb_360: String,
  thumb_360_gif: String,
  thumb_360_w: Double,
  thumb_360_h: Double,
  thumb_480: String,
  thumb_480_w: Double,
  thumb_480_h: Double,
  thumb_160: String,
  permalink: String,
  permalink_public: String,
  edit_link: String,
  preview: String,
  preview_highlight: String,
  lines: Double,
  lines_more: Double,
  is_public: Boolean,
  public_url_shared: Boolean,
  display_as_bot: Boolean,
  channels: List[String],
  groups: List[String],
  ims: List[String],
  initial_comment: InitialComment,
  num_stars: Double,
  is_starred: Boolean,
  pinned_to: List[String],
  reactions: List[Reactions],
  comments_count: Double
)
