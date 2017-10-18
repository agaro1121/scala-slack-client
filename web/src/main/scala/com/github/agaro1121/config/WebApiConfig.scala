package com.github.agaro1121.config

case class Endpoints(listUsers: String)

case class WebApiConfig(endpoints: Endpoints)

object WebApiConfig extends ConfigUtils {

  def default: WebApiConfig = unsafeLoadConfig[WebApiConfig]("slack")

}
