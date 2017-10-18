package com.github.agaro1121.marshalling

import com.github.agaro1121.models.{ConnectSelf, ConnectTeam, RtmConnectResponse}
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

trait RtmConnectResponseDecoders extends CirceSupport {

  implicit val ConnectTeamDecoder: Decoder[ConnectTeam] = deriveDecoder[ConnectTeam]
  implicit val ConnectSelfDecoder: Decoder[ConnectSelf] = deriveDecoder[ConnectSelf]
  implicit val RtmConnectResponseDecoder: Decoder[RtmConnectResponse] = deriveDecoder[RtmConnectResponse]

}

trait RtmConnectResponseEncoders extends CirceSupport {

  implicit val ConnectTeamEncoder: Encoder[ConnectTeam] = deriveEncoder[ConnectTeam]
  implicit val ConnectSelfEncoder: Encoder[ConnectSelf] = deriveEncoder[ConnectSelf]
  implicit val RtmConnectResponseEncoder: Encoder[RtmConnectResponse] = deriveEncoder[RtmConnectResponse]

}
