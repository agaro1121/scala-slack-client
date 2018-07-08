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
    case Message(_, channelId, userId, text, ts, _, _) =>
      SlackMessage(text, channelId, userId, ts)

    case EditedMessage(MessageEdited(user, text, _, _), _,_, channel, _, _, ts) =>
      SlackMessage(text, channel, user, ts)

    case BotMessage(text, botId, _, _, _, channel, _, ts) =>
      SlackMessage(text, channel, botId, ts, true)
  }
}

