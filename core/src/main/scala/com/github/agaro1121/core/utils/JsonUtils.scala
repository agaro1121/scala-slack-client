package com.github.agaro1121.core.utils

import io.circe.{Encoder, Json}
import io.circe.syntax._
import io.circe.generic.extras.Configuration

trait JsonUtils {

  implicit lazy val config: Configuration = Configuration.default.withDiscriminator("type")

  private def snake2CapitalCamel(name: String): String =
    "_([a-z\\d])".r.replaceAllIn(name, { m =>
      m.group(1).toUpperCase()
    }).capitalize

  private def camel2Underscores(name: String): String =
    "[A-Z\\d]".r.replaceAllIn(name, { m =>
      if (m.end(0) == 1) {
        m.group(0).toLowerCase()
      } else {
        "_" + m.group(0).toLowerCase()
      }
    })

  def convertTypeFieldToCapitalCamel(json: Json): Json =
    json.hcursor
      .downField("type")
      .withFocus(_.mapString(snake2CapitalCamel))
      .top
      .getOrElse(json)

  def convertTypeFieldToSnakeCaseAndEncode[T: Encoder](event: T): Json = {
    val rawJson = event.asJson

    val updatedJson = rawJson.hcursor
      .downField("type")
      .withFocus(_.mapString(camel2Underscores))
      .top

    if (updatedJson.isEmpty)
      println("*********" + event) //TODO: log here

    updatedJson.getOrElse(rawJson)
  }

}

object JsonUtils extends JsonUtils