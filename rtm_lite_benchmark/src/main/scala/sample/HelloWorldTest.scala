package sample

import java.util.concurrent.TimeUnit

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws
import akka.http.scaladsl.model.ws.TextMessage
import akka.http.scaladsl.model.ws.TextMessage.Strict
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, JsonFraming, Keep, RunnableGraph, Sink, Source}
import com.github.agaro1121.core.utils.JsonUtils
import com.github.agaro1121.rtmlite.client.AkkaStreamsComponents
import com.github.agaro1121.sharedevents.marshalling.{GeneralEventDecoders, GeneralEventEncoders}
import com.github.agaro1121.sharedevents.models
import com.github.agaro1121.sharedevents.models.Message
import com.typesafe.config.ConfigFactory
import io.circe.parser.parse
import io.circe.{Json, ParsingFailure}
import org.openjdk.jmh.annotations._
import io.circe.syntax._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object HelloWorldTest {
  val numOfMessages = 1000
}

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.Throughput))
class HelloWorldTest extends AkkaStreamsComponents with GeneralEventEncoders with GeneralEventDecoders {
  import HelloWorldTest._

  val configs = ConfigFactory.parseString(
    """
    akka.actor.default-dispatcher {
      executor = "fork-join-executor"
      fork-join-executor {
        parallelism-factor = 1
      }
    }
    """
  )

  implicit val a = ActorSystem("testingSystem", configs)
  implicit val ma = ActorMaterializer()
  import a.dispatcher


  val m = Message(None, "ChannelId", "UserId", "someText", "ts", None, None)

  val json1 = TextMessage("""{"some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage","some":"garbage"}""")
  val wsMessage: ws.Message = TextMessage(m.asJson.toString())
  def pickMessage(n: Int): ws.Message = n match {
    case n if n % 2 == 0 => wsMessage
    case _ => json1
  }

  val source: Source[ws.Message, NotUsed] =
  //    Source.repeat(wsMessage).take(numOfMessages)
    Source.fromIterator(() => Iterator.tabulate(numOfMessages)(n => pickMessage(n)))

  val wsMessage2Json2: Flow[ws.Message, Either[ParsingFailure, Json], NotUsed] =
    Flow[ws.Message]
      .collect {
        case Strict(json) =>
          parse(json).map(JsonUtils.convertTypeFieldToCapitalCamel)
      }

  val json2SlackMessage2: Flow[Either[ParsingFailure, Json], models.Message, NotUsed] =
    Flow[Either[ParsingFailure, Json]]
      .map {
        _.flatMap(_.as[models.Message])
      }.collect {
      case Right(msg) => msg
    }

  val graph1: RunnableGraph[Future[Done]] =
    source
      .via(wsMessage2SlackMessage)
      .toMat(Sink.ignore)(Keep.right)

  val graph2: RunnableGraph[Future[Done]] =
    source
      .via(wsMessage2Json).via(json2SlackMessage)
      .toMat(Sink.ignore)(Keep.right)

  val graph3: RunnableGraph[Future[Done]] =
    source
      .via(wsMessage2Json2).via(json2SlackMessage)
      .toMat(Sink.ignore)(Keep.right)

  val graph4: RunnableGraph[Future[Done]] =
    source
      .via(wsMessage2Json).via(json2SlackMessage2)
      .toMat(Sink.ignore)(Keep.right)

  val graph5: RunnableGraph[Future[Done]] =
    source
      .via(wsMessage2Json2).via(json2SlackMessage2)
      .toMat(Sink.ignore)(Keep.right)

  val graph6: RunnableGraph[Future[Done]] =
    source
      .via(wsMessage2Json2).async.via(json2SlackMessage2)
      .toMat(Sink.ignore)(Keep.right)

  @TearDown
  def shutdown(): Unit = {
    Await.result(a.terminate(), 5.seconds)
  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def withAsync(): Unit = {
    Await.result(graph1.run(), Duration.Inf)
  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def withoutAsync(): Unit = {
    Await.result(graph2.run(), Duration.Inf)
  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def withWsMessage2Json2(): Unit = {
    Await.result(graph3.run(), Duration.Inf)
  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def withJson2SlackMessage2(): Unit = {
    Await.result(graph4.run(), Duration.Inf)
  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def withBoth2(): Unit = {
    Await.result(graph5.run(), Duration.Inf)
  }

  @Benchmark
  @OperationsPerInvocation(1000)
  def withBothAndAsync(): Unit = {
    Await.result(graph6.run(), Duration.Inf)
  }

}
