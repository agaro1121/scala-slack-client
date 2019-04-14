package com.github.agaro1121.web.config

import pureconfig._
import pureconfig.generic.auto._

case class Endpoints(listUsers: String)

case class WebApiConfig(endpoints: Endpoints)

object WebApiConfig {

  def default: WebApiConfig = loadConfigOrThrow[WebApiConfig]("slack")

}
