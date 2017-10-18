package com.github.agaro1121.config

case class RtmConfig(rtmConnect: String)

object RtmConfig extends ConfigUtils{

  val default: RtmConfig = unsafeLoadConfig[RtmConfig]("slack")

}
