package com.github.agaro1121.client.rtm

import com.github.agaro1121.marshalling.RtmConnectResponseEncoders
import com.github.agaro1121.common.RtmIntegrationTestSuite

class RtmTest extends RtmIntegrationTestSuite with RtmConnectResponseEncoders {

  "Slack Client" should {

    "connect to rtm.connect and parse response successfully" in {
      whenReady(rtmConnect()) { result =>
        result shouldBe 'right
      }
    }

  }

}
