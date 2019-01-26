package com.github.agaro1121.core.config

import pureconfig.generic.auto._

case class SlackClientConfig(botToken: String, apiUrl: String)

object SlackClientConfig extends ConfigUtils {

  val fromReference: SlackClientConfig =
    unsafeLoadConfig[SlackClientConfig]("slack")

}
