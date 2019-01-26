package com.github.agaro1121.core.config

import pureconfig._

trait ConfigUtils {

  def unsafeLoadConfig[T : ConfigReader](namespace: String): T = {
    loadConfig[T](namespace) match {
      case Left(errors) => throw MissingConfiguration(errors.toList.mkString("\n"))
      case Right(slackClientConfig) => slackClientConfig
    }
  }

}
