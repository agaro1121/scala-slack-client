import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.github.agaro1121.rtmlite
import com.github.agaro1121.rtmlite.client.AbilityToRespondToRtm
import com.github.agaro1121.sharedevents.models.SlackMessage

object TestBot {
  def props: Props = Props(new TestBot())
}

class TestBot extends AbilityToRespondToRtm {
  override def receiveWithWsActorToRespond(slack: ActorRef): Receive = {

    case msg@SlackMessage(text, _, _, _, _) =>
      println(s"Test Bot received Abstract message: $text")
      slack ! msg.replyWithMessage(s"Test Bot received Abstract message: $text")


  }
}

object TestBotUsingPf {
  val handler: PartialFunction[SlackMessage, SlackMessage] = {
    case msg@SlackMessage(text, _, _, _, _) =>
      println(s"Test Bot received Abstract message: $text")
      msg.replyWithMessage(s"Test Bot received Abstract message: $text")
  }
}

object TestBotTester extends App {

  implicit val system = ActorSystem("main")
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val rtmClient = rtmlite.client.RtmClient()
  val testBot = system.actorOf(TestBot.props)

//  rtmClient.connectWithUntypedActor(testBot).onComplete(println)
  rtmClient.connectWithPF(TestBotUsingPf.handler).onComplete(println)
}
