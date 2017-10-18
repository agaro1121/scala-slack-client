package com.github.agaro1121.config

import pureconfig.{ConfigReader, loadConfig}

trait ConfigUtils {

  def unsafeLoadConfig[T : ConfigReader](namespace: String): T = {
    loadConfig[T](namespace) match {
      case Left(errors) => throw MissingConfiguration(errors.toList.mkString("\n"))
      case Right(slackClientConfig) => slackClientConfig
    }
  }

}
