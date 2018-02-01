package com.github.agaro1121.web.config

import com.github.agaro1121.core.config.ConfigUtils

case class Endpoints(listUsers: String)

case class WebApiConfig(endpoints: Endpoints)

object WebApiConfig extends ConfigUtils {

  def default: WebApiConfig = unsafeLoadConfig[WebApiConfig]("slack")

}
