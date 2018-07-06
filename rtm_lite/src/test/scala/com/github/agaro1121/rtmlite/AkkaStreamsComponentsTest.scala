package com.github.agaro1121.rtmlite

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.TextMessage
import akka.http.scaladsl.model.ws
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{JsonFraming, Sink, Source}
import com.github.agaro1121.rtmlite.client.AkkaStreamsComponents
import com.github.agaro1121.sharedevents.models.Message
import org.scalatest.{Matchers, WordSpecLike}
import io.circe.syntax._
import com.github.agaro1121.sharedevents.marshalling.GeneralEventEncoders._
import org.scalatest.concurrent.{PatienceConfiguration, ScalaFutures}

import scala.concurrent.{Await, Future}
import concurrent.duration._
import scala.io.StdIn
import org.scalatest.time._

class AkkaStreamsComponentsTest extends WordSpecLike with Matchers with AkkaStreamsComponents
  with ScalaFutures {

  implicit override val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(3, Minutes), interval = Span(20, Millis))


  "Stuff" should {
    "just work" in {
      implicit val a = ActorSystem()
      implicit val ma = ActorMaterializer()
      import a.dispatcher

      def timedFuture[T](future: Future[T]) = {
        val start = System.currentTimeMillis()
        future.onComplete({
          case _ => println(s"Future took ${System.currentTimeMillis() - start} ms")
        })
        future
      }

      val m = Message(None, "ChannelId", "UserId", "someText", "ts", None, None)
      val wsMessage: ws.Message = TextMessage(m.asJson.toString())

      val source: Source[ws.Message, NotUsed] =
        Source.fromIterator(() => Stream.continually(wsMessage).take(100000000).iterator)

      /*val result = Await.result(source
        .via(wsMessage2SlackMessage)
        .runWith(Sink.seq),
        5 seconds
      )*/

      val f = source.via(wsMessage2SlackMessage).to(Sink.ignore)

      whenReady(
//        timedFuture(source.via(wsMessage2SlackMessage).runWith(Sink.ignore))
        timedFuture(source.via(wsMessage2Json).via(json2SlackMessage).runWith(Sink.ignore))
      ){
        println
      }


    }
  }

}
