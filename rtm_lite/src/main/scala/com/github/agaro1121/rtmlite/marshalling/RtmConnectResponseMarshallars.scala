package com.github.agaro1121.rtmlite.marshalling

import com.github.agaro1121.rtmlite.models.{ConnectSelf, ConnectTeam, RtmConnectResponse}
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

trait RtmConnectResponseDecoders extends ErrorAccumulatingCirceSupport {

  implicit val ConnectTeamDecoder: Decoder[ConnectTeam] = deriveDecoder[ConnectTeam]
  implicit val ConnectSelfDecoder: Decoder[ConnectSelf] = deriveDecoder[ConnectSelf]
  implicit val RtmConnectResponseDecoder: Decoder[RtmConnectResponse] = deriveDecoder[RtmConnectResponse]

}

trait RtmConnectResponseEncoders extends ErrorAccumulatingCirceSupport {

  implicit val ConnectTeamEncoder: Encoder[ConnectTeam] = deriveEncoder[ConnectTeam]
  implicit val ConnectSelfEncoder: Encoder[ConnectSelf] = deriveEncoder[ConnectSelf]
  implicit val RtmConnectResponseEncoder: Encoder[RtmConnectResponse] = deriveEncoder[RtmConnectResponse]

}
