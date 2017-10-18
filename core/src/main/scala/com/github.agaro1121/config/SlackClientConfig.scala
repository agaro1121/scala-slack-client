package com.github.agaro1121.config

case class SlackClientConfig(botToken: String, apiUrl: String)

object SlackClientConfig extends ConfigUtils {

  val fromReference: SlackClientConfig =
    unsafeLoadConfig[SlackClientConfig]("slack")

}
