package com.github.agaro1121.rtm.client

import akka.NotUsed
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.Flow
import com.github.agaro1121.sharedevents.models._
import com.typesafe.scalalogging.LazyLogging
import cats.implicits._
import com.github.agaro1121.core.utils.JsonUtils
import com.github.agaro1121.rtm.marshalling.{RtmDecoders, RtmEncoders}
import com.github.agaro1121.rtm.models.events.{Ack, RtmApiEvent, SlackRtmEvent, Undefined}
import com.github.agaro1121.sharedevents.marshalling.{GeneralEventDecoders, GeneralEventEncoders}
import io.circe
import io.circe.generic.extras.auto._
import io.circe.shapes._
import io.circe.{DecodingFailure, Json, ParsingFailure}
import io.circe.parser._
import shapeless._


/**
  *
  * Some Akka Streams components for use
  *
  * Websockets require Flow[Message, Message]
  *
  * The following pieces come together to form a sane flow
  *
  * wsMessage2Json ~> json2SlackEvent ~> [Your logic to respond goes here] ~> slackEvent2Json ~> json2WsMessage
  *
  * slackEvent2WsMessage is a summary of the outgoing flow (slackEvent2Json ~> json2WsMessage)
  * and can be used in place of them
  *
  * it is equipped with asynchronous boundaries for a slight performance gain (hopefully)
  *
  * wsMessage2SlackEvent is a similar summary
  *
  * */
trait AkkaStreamsComponents extends LazyLogging
  with GeneralEventEncoders
  with GeneralEventDecoders
  with RtmEncoders
  with RtmDecoders {

  val wsMessage2Json: Flow[Message, Either[ParsingFailure, Json], NotUsed] =
    Flow[Message]
      .map { msg =>
        val json: String = msg.asTextMessage.getStrictText
        parse(json).map(JsonUtils.convertTypeFieldToCapitalCamel)
      }

  val json2SlackEvent: Flow[Either[ParsingFailure, Json], Either[circe.Error, SlackRtmEvent], NotUsed] =
    Flow[Either[ParsingFailure, Json]]
      .map {
        _.flatMap { json =>
          json.as[SlackRtmEvent]
            .orElse(superRecover(json)) //TODO: delete this when the shapeless work is done
            .recover(recover(json)) //this stays forever in case Slack introduces new Events this client can't handle
        }
      }

  val slackEvent2Json: Flow[SlackRtmEvent, Json, NotUsed] =
    Flow[SlackRtmEvent].map(JsonUtils.convertTypeFieldToSnakeCaseAndEncode(_))

  val json2WsMessage: Flow[Json, TextMessage.Strict, NotUsed] =
    Flow[Json].map { json =>
      TextMessage.Strict(json.toString)
    }

  val wsMessage2SlackEvent: Flow[Message, Either[circe.Error, SlackRtmEvent], NotUsed] =
    wsMessage2Json.async.via(json2SlackEvent).async

  val slackEvent2WsMessage: Flow[SlackRtmEvent, TextMessage.Strict, NotUsed] =
    slackEvent2Json.async.via(json2WsMessage).async


  /*
  * Currently the json library is configured to look for a "type" field
  * This handles some of the current limitations of Circe(json)
  *
  * The scenarios are:
  *
  *   1. There are a couple events that do not have the "type" field
  *
  *   2. Many differently shaped events with the same "type" but have a "subtype"
  *
  * There will be some work done in the future using Shapeless to address the above scenarios.
  * For now, this will do.
  * */
  private val superRecover: Json => Either[DecodingFailure, SlackRtmEvent] = json => {
    val e: Either[io.circe.DecodingFailure, RtmApiEvent] = json.as[Ack]
    e.map(Coproduct[SlackRtmEvent](_))
      .recoverWith {
        case _: DecodingFailure =>
          val e: Either[io.circe.DecodingFailure, GeneralEvent] = json.as[EditedMessage]
          e.map(Coproduct[SlackRtmEvent](_))
            .recoverWith {
              case _: DecodingFailure =>
                val e: Either[io.circe.DecodingFailure, GeneralEvent] = json.as[BotMessage]
                e.map(Coproduct[SlackRtmEvent](_))
            }
      }
  }

  /**
    *
    * This will handle the cases where Slack introduces new Events and this client is not updated
    * to handle them
    *
  * */
  private val recover: Json => PartialFunction[DecodingFailure, SlackRtmEvent] = json => {
    case error =>
      val undefined: RtmApiEvent = Undefined(s"json=$json\n ${error.toString}")
      logger.warn(undefined.toString)
      Coproduct[SlackRtmEvent](undefined)
  }

}
