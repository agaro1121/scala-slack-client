package com.github.agaro1121.rtmlite.client

import akka.NotUsed
import akka.http.scaladsl.model.ws
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.{Flow, Source}
import com.github.agaro1121.core.utils.JsonUtils
import com.github.agaro1121.sharedevents.models
import io.circe.syntax._
import io.circe.{Json, ParsingFailure}
import io.circe.parser.parse
import com.github.agaro1121.sharedevents.marshalling.GeneralEventEncoders._
import com.github.agaro1121.sharedevents.marshalling.GeneralEventDecoders._
import com.github.agaro1121.sharedevents.models.GeneralEvent
import io.circe
//for calling `map` on [[Either]] in Scala v2.11.x
import cats.syntax.either._
import com.github.agaro1121.sharedevents.models.SlackMessage

private[client] trait AkkaStreamsComponents {

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

  val json2Message: Flow[Either[ParsingFailure, Json], models.Message, NotUsed] =
    Flow[Either[ParsingFailure, Json]].map {
      _.flatMap(_.as[models.Message])
    }.collect{ case t if t.isRight => t.right.get }

  private val json2AllSlackMessages: Flow[Either[ParsingFailure, Json], Either[circe.Error, GeneralEvent], NotUsed] =
    Flow[Either[ParsingFailure, Json]].map { jsonOrError =>
      jsonOrError.flatMap{ json =>
        json.as[models.Message]
          .orElse(json.as[models.EditedMessage])
          .orElse(json.as[models.BotMessage])
      }
    }

  val json2SlackMessage: Flow[Either[ParsingFailure, Json], SlackMessage, NotUsed] =
    json2AllSlackMessages.collect {
      case Right(msg) => SlackMessage.fromSomeMessage(msg)
    }

  val json2SlackMessageAndMessage: Flow[Either[ParsingFailure, Json], models.SlackEvent, NotUsed] =
    json2AllSlackMessages.collect {
      case Right(msg) => msg
    }.flatMapConcat { msg =>
      Source(List(msg, SlackMessage.fromSomeMessage(msg)))
    }

  val wsMessage2SlackMessage: Flow[ws.Message, models.Message, NotUsed] =
    wsMessage2Json.via(json2Message)

  val slackMessage2WsMessage: Flow[models.Message, ws.Message, NotUsed] =
    slackMessage2Json.via(json2WsMessage)
}
