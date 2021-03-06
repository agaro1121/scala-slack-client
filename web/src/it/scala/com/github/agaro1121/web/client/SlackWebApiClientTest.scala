package com.github.agaro1121.web.client

import com.github.agaro1121.web.common.SlackWebApiTestSuite

class SlackWebApiClientTest extends SlackWebApiTestSuite {

  "Slack Web Api Client" should {

    "call users.list and deserialize the response" in {

      whenReady(slackWebClient.listUsers) { result =>
        result shouldBe 'right
      }
    }

  }

}
