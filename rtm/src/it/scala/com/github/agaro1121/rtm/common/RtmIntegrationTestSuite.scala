package com.github.agaro1121.rtm.common

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.github.agaro1121.rtmlite.client.AbilityToConnectToRtm
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

abstract class RtmIntegrationTestSuite
  extends TestKit(ActorSystem("test-system"))
    with WordSpecLike
    with Matchers
    with ScalaFutures
    with BeforeAndAfterAll
    with AbilityToConnectToRtm {


  override implicit protected val actorSystem: ActorSystem = system
  override implicit val ec = system.dispatcher
  implicit val mat: ActorMaterializer = ActorMaterializer()

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(10, Seconds), interval = Span(500, Millis))

  override protected def afterAll(): Unit = {
    httpClient.shutdownAllConnectionPools().onComplete { _ =>
      mat.shutdown()
      system.terminate()
    }
    super.afterAll()
  }


}
