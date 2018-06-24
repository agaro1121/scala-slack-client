package com.github.agaro1121.rtmlite.client

import akka.NotUsed
import akka.http.scaladsl.model.ws
import akka.http.scaladsl.model.ws.TextMessage
import akka.stream.scaladsl.Flow
import com.github.agaro1121.core.utils.JsonUtils
import com.github.agaro1121.sharedevents.models
import io.circe.syntax._
import io.circe.{Json, ParsingFailure}
import io.circe.parser.parse
import com.github.agaro1121.sharedevents.marshalling.GeneralEventEncoders.MessageEncoder
import com.github.agaro1121.sharedevents.marshalling.GeneralEventDecoders.MessageDecoder

trait AkkaStreamsComponents {

  val wsMessage2Json: Flow[ws.Message, Either[ParsingFailure, Json], NotUsed] =
    Flow[ws.Message]
      .map { msg =>
        val json: String = msg.asTextMessage.getStrictText
        parse(json).map(JsonUtils.convertTypeFieldToCapitalCamel)
      }

  val json2WsMessage: Flow[Json, ws.Message, NotUsed] =
    Flow[Json].map { json =>
      TextMessage.Strict(json.toString)
    }

  val slackMessage2Json: Flow[models.Message, Json, NotUsed] =
    Flow[models.Message].map{ msg =>
      msg.asJson.deepMerge(Json.obj(("type", Json.fromString("message"))))
    }

  val json2SlackMessage: Flow[Either[ParsingFailure, Json], models.Message, NotUsed] =
    Flow[Either[ParsingFailure, Json]].map {
      _.flatMap(_.as[models.Message])
    }.collect{ case t if t.isRight => t.right.get }

  val wsMessage2SlackMessage: Flow[ws.Message, models.Message, NotUsed] =
    wsMessage2Json.async.via(json2SlackMessage)

  val slackMessage2WsMessage: Flow[models.Message, ws.Message, NotUsed] =
    slackMessage2Json.async.via(json2WsMessage)
}
