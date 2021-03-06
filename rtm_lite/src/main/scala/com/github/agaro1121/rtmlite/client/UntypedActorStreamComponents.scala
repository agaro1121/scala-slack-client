package com.github.agaro1121.rtmlite.client

import akka.{Done, NotUsed}
import akka.actor.{ActorRef, PoisonPill}
import akka.http.scaladsl.model.ws
import akka.http.scaladsl.model.ws.Message
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import com.github.agaro1121.sharedevents.models
import com.github.agaro1121.sharedevents.models.SlackMessage
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

private[client] trait UntypedActorStreamComponents extends LazyLogging {
  this: AkkaStreamsComponents =>

  def untypedActorFlow(usersActor: ActorRef): Flow[Message, Message, NotUsed] = {
    /*
    * Actors reply with [[Object]] type
    * this will handle the transition to a proper type
    * */
    val actorReply2SlackMessage: Flow[Object, SlackMessage, NotUsed] =
      Flow[Object].map(_.asInstanceOf[SlackMessage])

    val sink: Sink[Message, NotUsed] =
      wsMessage2Json.via(json2SlackMessage).to(Sink.actorRef(usersActor, PoisonPill))

    val source: Source[ws.Message, ActorRef] = {
      Source.actorRef[models.Message](0, OverflowStrategy.fail)
        .mapMaterializedValue { actorThatTalksToSlack =>
          logger.info("Switching your actor to receive messages...")
          usersActor ! AbilityToRespondToRtm.ConnectedTo(actorThatTalksToSlack)
          actorThatTalksToSlack
        }.via(actorReply2SlackMessage).via(slackMessage2WsMessage)
    }

    Flow.fromSinkAndSource(sink, source)
  }

}
