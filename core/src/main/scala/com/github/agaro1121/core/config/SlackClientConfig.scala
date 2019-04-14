package com.github.agaro1121.core.config

import pureconfig.generic.auto._
import pureconfig._

case class SlackClientConfig(botToken: String, apiUrl: String)

object SlackClientConfig {

  val fromReference: SlackClientConfig =
    loadConfigOrThrow[SlackClientConfig]("slack")

}
