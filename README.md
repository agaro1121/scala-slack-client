# README #

# Updates coming soon !!!!

This is my attempt to provide a library that will allow users to create bots.

The first thing you need to do is create a `token.conf` file with one configuration.

`bot-token = "xoxb-..."`

You can create your bot on slack and obtain a token.

Add the following to your build.sbt

```scala
resolvers += Resolver.bintrayRepo("agaro1121", "com.github.agaro1121")

libraryDependencies += "com.github.agaro1121" %% "scala-slack-rtm-lite" % "0.2.26"
```


The next thing you require is a class that extends `AbilityToRespondToRtm`
This will be an actor
It will look like this:

```scala
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

object TestBotTester extends App {

  implicit val system = ActorSystem("main")
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val rtmClient = rtmlite.client.RtmClient()
  val testBot = system.actorOf(TestBot.props)

  rtmClient.connectWithUntypedActor(testBot).onComplete(println)
}
```

You can also interact with Slack with the following:
  - `PartialFunction[SlackMessage, SlackMessage]`
  - `PartialFunction[SlackMessage, Future[SlackMessage]]`

``` scala
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.github.agaro1121.rtmlite
import com.github.agaro1121.sharedevents.models.SlackMessage

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

  rtmClient.connectWithPF(TestBotUsingPf.handler).onComplete(println)
}
