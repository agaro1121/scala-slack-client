package com.github.agaro1121.common

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.github.agaro1121.client.SlackWebApiClient
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.scalatest.time.{Millis, Seconds, Span}

abstract class SlackWebApiTestSuite
  extends TestKit(ActorSystem("test-system"))
  with WordSpecLike
  with BeforeAndAfterAll
  with Matchers
  with ScalaFutures {


  implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()

  val slackWebClient = SlackWebApiClient()

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(10, Seconds), interval = Span(500, Millis))

  override protected def afterAll(): Unit = {
    system.terminate()
    super.afterAll()
  }

}
