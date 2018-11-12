import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.scaladsl.{Flow, Keep}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
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

  val decider: Supervision.Decider = {
    case _: java.lang.IllegalStateException => Supervision.resume
  }

  implicit val system = ActorSystem("main")
  implicit val mat = ActorMaterializer(
    ActorMaterializerSettings(system).withSupervisionStrategy(decider)
  )
  import system.dispatcher

  val rtmClient = rtmlite.client.RtmClient()
  val testBot = system.actorOf(TestBot.props)

//  val result = rtmClient.connectWithUntypedActor(testBot)
  val result = rtmClient.connectWithPF(TestBotUsingPf.handler)

    result.onComplete {
      case scala.util.Success(value) =>
        value.fold(
          err => println(err), {
          case (connectionStatus, done) =>
            connectionStatus.onComplete(println)
            done.onComplete{ d =>
              println(s"Stream completed as: $d")
              println("Shutting down application...")
              system.terminate().onComplete{
                t =>
                  println(s"Actor System terminated: $t")
                  System.exit(-1)
              }
            }
          })

      case scala.util.Failure(exception) =>
        println(exception.getMessage)
    }
}
