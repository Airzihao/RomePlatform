package grapheco.RomePlatform.util

class Point(pointId: Long) {
  val id = pointId
  var x:Double = 0.0
  var y:Double = 0.0
  var z:Double = 0.0

  def setXPosition(xp: Any): Unit ={
    x = xp.asInstanceOf[Double]
  }
  def setYPosition(yp: Any): Unit ={
    y = yp.asInstanceOf[Double]
  }
  def setZPosition(zp: Any): Unit ={
    z = zp.asInstanceOf[Double]
  }

  def setPosition(xp:Any, yp:Any, zp:Any): Unit ={
    x = xp.asInstanceOf[Double]
    y = yp.asInstanceOf[Double]
    z = zp.asInstanceOf[Double]
  }

}
