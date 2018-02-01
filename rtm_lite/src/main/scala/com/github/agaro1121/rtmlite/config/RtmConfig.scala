package com.github.agaro1121.rtmlite.config

import com.github.agaro1121.core.config.ConfigUtils

case class RtmConfig(rtmConnect: String)

object RtmConfig extends ConfigUtils{

  val default: RtmConfig = unsafeLoadConfig[RtmConfig]("slack")

}
