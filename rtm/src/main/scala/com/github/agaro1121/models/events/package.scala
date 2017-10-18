package com.github.agaro1121.models

import shapeless.{:+:, CNil}


package object events {

  type SlackRtmEvent = GeneralEvent :+: RtmApiEvent :+: CNil

}
