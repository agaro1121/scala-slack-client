package com.github.agaro1121.rtmlite.common

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import org.scalatest.{Matchers, WordSpecLike}

abstract class RtmTestSuite extends TestKit(ActorSystem("testSystem"))
  with WordSpecLike
  with Matchers {

  implicit val mat = ActorMaterializer()

}
