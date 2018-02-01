package com.github.agaro1121.rtm.models

import com.github.agaro1121.sharedevents.models.GeneralEvent
import shapeless.{:+:, CNil}


package object events {

  type SlackRtmEvent = GeneralEvent :+: RtmApiEvent :+: CNil

}
