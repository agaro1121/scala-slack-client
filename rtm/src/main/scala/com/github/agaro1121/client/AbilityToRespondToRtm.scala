package com.github.agaro1121.client

import akka.actor.{Actor, ActorRef}

object AbilityToRespondToRtm {
  case class ConnectedTo(actor: ActorRef)
}

trait AbilityToRespondToRtm extends Actor {

  override def receive: Receive = {
    case AbilityToRespondToRtm.ConnectedTo(wsActor) =>
      context.become(receiveWithWsActorToRespond(wsActor))
  }

  def receiveWithWsActorToRespond(slack: ActorRef): Receive
}
