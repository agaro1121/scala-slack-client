package com.github.agaro1121.rtmlite.client

import akka.actor.{Actor, ActorRef}

object AbilityToRespondToRtm {
  final case class ConnectedTo(actorThatRepresentsSlack: ActorRef)
}

trait AbilityToRespondToRtm extends Actor {

  override def receive: Receive = {
    case AbilityToRespondToRtm.ConnectedTo(actorThatRepresentsSlack) =>
      context.become(receiveWithWsActorToRespond(actorThatRepresentsSlack))
  }

  def receiveWithWsActorToRespond(slack: ActorRef): Receive
}
