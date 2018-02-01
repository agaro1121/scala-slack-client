package com.github.agaro1121.rtm.client

import com.github.agaro1121.rtmlite.marshalling.RtmConnectResponseEncoders
import com.github.agaro1121.rtm.common.RtmIntegrationTestSuite

class RtmTest extends RtmIntegrationTestSuite with RtmConnectResponseEncoders {

  "Slack Client" should {

    "connect to rtm.connect and parse response successfully" in {
      whenReady(rtmConnect()) { result =>
        result shouldBe 'right
      }
    }

  }

}
