# README #

# Updates coming soon !!!!

This is my attempt to provide a library that will allow users to create bots.

The first thing you need to do is create a `token.conf` file with one configuration.

`bot-token = "xoxb-..."`

You can create your bot on slack and obtain a token.

Add the following to your build.sbt

```scala
resolvers += "Artifactory Realm" at "http://oss.jfrog.org/artifactory/oss-snapshot-local"
libraryDependencies += "com.github.agaro1121" %% "scala-slack-rtm" % "0.1.1-SNAPSHOT"
```


The next thing you require is a class that extends `AbilityToRespondToRtm`
This will be an actor
It will look like this:

```scala
import akka.actor.{ActorRef, Props}
import com.github.agaro1121.client.AbilityToRespondToRtm
import com.github.agaro1121.models.Message

object MyActor {
  def props: Props = Props(new MyActor())
}

class MyActor extends AbilityToRespondToRtm {

  override def receiveWithWsActorToRespond(slack: ActorRef): Receive = {

    case message @ Message(_,_, _, "hello", _,_,_) =>
      slack ! message.replyWithMessage("Hello this is MyBot")

    
  }
}
```
Your bot will most likely be responding to messages but the library includes all events that flow through 
slack's event stream. You can choose to react to them as you see fit.

More examples to come. I will have a reference application uploaded soon.

This is still far from a 1.0 and still has many kinks to work out but it's stable and can be actively used.
