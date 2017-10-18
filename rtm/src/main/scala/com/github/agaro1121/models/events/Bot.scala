package com.github.agaro1121.models.events


case class Icons(
  image_34: Option[String],
  image_36: Option[String],
  image_44: Option[String],
  image_48: Option[String],
  image_68: Option[String],
  image_72: Option[String],
  image_88: Option[String],
  image_102: Option[String],
  image_132: Option[String],
  image_230: Option[String],
  image_original: Option[String]
)

case class Bot(
  id: String,
  deleted: Option[Boolean],
  name: String,
  updated: Option[Double],
  app_id: String,
  icons: Icons
)
