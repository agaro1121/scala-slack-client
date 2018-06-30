import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.github.agaro1121.rtmlite
import com.github.agaro1121.rtmlite.client.AbilityToRespondToRtm
import com.github.agaro1121.rtmlite.models.Message

object TestBot {
  def props: Props = Props(new TestBot())
}

class TestBot extends AbilityToRespondToRtm {
  override def receiveWithWsActorToRespond(slack: ActorRef): Receive = {

    case msg@Message(_, _, _, text, _, _, _) =>
      slack ! msg.replyWithMessage(s"Test Bot received message: $text")

  }
}

object TestBotTester extends App {

  implicit val system = ActorSystem("main")
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val rtmClient = rtmlite.client.RtmClient()
  val testBot = system.actorOf(TestBot.props)

  rtmClient.connectWithUntypedActor(testBot).onComplete(println)
}
