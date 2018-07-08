package com.github.agaro1121.sharedevents.models

/**
  * Abstraction over all types of Slack message types
  * Currently the running list is:
  * - EditedMessage
  * - Message
  * - BotMessage
  * */
final case class SlackMessage(text: String
                        ,channel: ChannelId
                        ,user: UserId
                        ,ts: String
                        ,isBot: Boolean = false) extends CustomEvent {

  def replyWithMessage(msg: String): SlackMessage = copy(text = msg)

  def toMessage: Message = Message(
    None, channel, user, text, ts, None, None
  )
}

object SlackMessage {
  val fromSomeMessage: PartialFunction[GeneralEvent, SlackMessage] = {
    case msg: Message => SlackMessage(
      msg.text, msg.channel, msg.user, msg.ts
    )
    case msg: EditedMessage => SlackMessage(
      msg.message.text, msg.channel, msg.message.user, msg.message.ts
    )
    case msg: BotMessage => SlackMessage(
      msg.text, msg.channel, msg.bot_id, msg.ts, true
    )
  }
}

