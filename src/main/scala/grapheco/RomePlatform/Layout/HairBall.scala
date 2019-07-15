package grapheco.RomePlatform.Layout

import grapheco.RomePlatform.util.Point

class HairBall() {

  var nodeCount:Long = 0
  var edgeCount:Long = 0
  var id:Long = 0
  var center: Point = new Point(id)
  def this(hbId: Long){
    this()
    this.id = hbId
  }
  /**
    * id, nodeCount, edgeCount, center
    * func: coorGenerator
    *
    */

  def setNodeCount(count: Long): Unit ={
    nodeCount = count
  }
  def setEdgeCount(count: Long): Unit ={
    edgeCount = count
  }
  def setCenterPosition(px: Double, py: Double, pz: Double): Unit ={
    center.setPosition(px,py,pz)
  }
  def hairBallGenerator(): Unit ={
    //ultra parameter
    //means the count of points in 1 unit volume
    val narrowFactor = 4
  }




}
