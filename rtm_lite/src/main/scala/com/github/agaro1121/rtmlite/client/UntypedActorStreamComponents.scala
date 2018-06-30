package com.github.agaro1121.rtmlite.client

import akka.NotUsed
import akka.actor.{ActorRef, PoisonPill}
import akka.http.scaladsl.model.ws
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.github.agaro1121.rtmlite.models
import com.typesafe.scalalogging.LazyLogging

private[client] trait UntypedActorStreamComponents extends LazyLogging {
  this: AkkaStreamsComponents =>

  def untypedActorFlow(usersActor: ActorRef): Flow[ws.Message, ws.Message, NotUsed] = {
    /*
    * Actors reply with [[Object]] type
    * this will handle the transition to a proper type
    * */
    val actorReply2SlackMessage: Flow[Object, models.Message, NotUsed] =
      Flow[Object].map { o =>
        o.asInstanceOf[models.Message]
      }

    val sink: Sink[ws.Message, NotUsed] = wsMessage2SlackMessage.to(Sink.actorRef(usersActor, PoisonPill))

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
