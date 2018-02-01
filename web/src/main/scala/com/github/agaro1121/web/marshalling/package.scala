package com.github.agaro1121.web

import de.heikoseeberger.akkahttpcirce.CirceSupport

package object marshalling {

  trait AllDecoders extends WebApiDecoders with CirceSupport
  trait AllEncoders extends WebApiEncoders with CirceSupport

}
