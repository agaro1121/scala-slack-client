package com.github.agaro1121.rtmlite.config

import pureconfig._
import pureconfig.generic.auto._

case class RtmConfig(rtmConnect: String)

object RtmConfig {

  val default: RtmConfig = loadConfigOrThrow[RtmConfig]("slack")

}
