package com.github.agaro1121.sharedevents

package object models {

  type ChannelId = String
  type UserId = String

  trait SlackEvent extends Product with Serializable
  trait CustomEvent extends SlackEvent

}
