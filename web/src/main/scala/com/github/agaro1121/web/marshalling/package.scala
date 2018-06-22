package com.github.agaro1121.web

import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport

package object marshalling {

  trait AllDecoders extends WebApiDecoders with ErrorAccumulatingCirceSupport
  trait AllEncoders extends WebApiEncoders with ErrorAccumulatingCirceSupport

}
