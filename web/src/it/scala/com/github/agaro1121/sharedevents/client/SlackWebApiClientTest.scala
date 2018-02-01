package com.github.agaro1121.sharedevents.client

import com.github.agaro1121.common.SlackWebApiTestSuite

class SlackWebApiClientTest extends SlackWebApiTestSuite {

  "Slack Web Api Client" should {

    "call users.list and deserialize the response" in {

      whenReady(slackWebClient.listUsers) { result =>
        result shouldBe 'right
      }
    }

  }

}
