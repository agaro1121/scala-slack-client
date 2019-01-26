import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Materializer, Supervision}
import com.github.agaro1121.rtmlite
import com.github.agaro1121.rtmlite.client.AbilityToRespondToRtm
import com.github.agaro1121.sharedevents.models.SlackMessage
import scala.concurrent.Future

object TestBot {
  def props: Props = Props(new TestBot())

  val handler: PartialFunction[SlackMessage, SlackMessage] = {
    case msg@SlackMessage(text, _, _, _, _) =>
      println(s"Test Bot received Abstract message: $text")
      msg.replyWithMessage(s"Test Bot received Abstract message: $text")
  }

  val handlerFuture: PartialFunction[SlackMessage, Future[SlackMessage]] = {
    case msg@SlackMessage(text, _, _, _, _) =>
      println(s"Test Bot received Abstract message: $text")
      Future.successful(msg.replyWithMessage(s"Test Bot received Abstract message: $text"))
  }
}

class TestBot extends AbilityToRespondToRtm {
  import context.dispatcher
  override def receiveWithWsActorToRespond(slack: ActorRef): Receive =
//    TestBot.handler.andThen(slack ! _).asInstanceOf[Receive]
    TestBot.handlerFuture.andThen(_.foreach(slack ! _)).asInstanceOf[Receive]
}


object TestBotRunner extends App {

  val decider: Supervision.Decider = {
    case _: java.lang.IllegalStateException => Supervision.resume
  }

  implicit val system: ActorSystem = ActorSystem("main")
  implicit val mat: Materializer = ActorMaterializer(
    ActorMaterializerSettings(system).withSupervisionStrategy(decider)
  )
  import system.dispatcher

  val rtmClient = rtmlite.client.RtmClient()
  val testBot = system.actorOf(TestBot.props)

//  val result = rtmClient.connectWithUntypedActor(testBot)
//  val result = rtmClient.connectWithPF(TestBot.handler)
  val result = rtmClient.connectWithPFAsync(TestBot.handlerFuture)

  result.onComplete{
    case scala.util.Success((status, streamDone)) =>
      println(status)
      streamDone.onComplete(println)
    case scala.util.Failure(exception) =>
      println(exception.getMessage)
  }
}
