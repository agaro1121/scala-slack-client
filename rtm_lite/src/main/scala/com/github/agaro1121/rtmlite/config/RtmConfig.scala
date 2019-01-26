package com.github.agaro1121.rtmlite.config

import com.github.agaro1121.core.config.ConfigUtils
import pureconfig.generic.auto._

case class RtmConfig(rtmConnect: String)

object RtmConfig extends ConfigUtils {

  val default: RtmConfig = unsafeLoadConfig[RtmConfig]("slack")

}
