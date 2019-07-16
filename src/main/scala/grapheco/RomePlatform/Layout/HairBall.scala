package grapheco.RomePlatform.Layout

import grapheco.RomePlatform.util.{Node, Point}

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
  def hairBallGenerator(nodeArr: Array[Node]): Unit ={
    // ultra parameter
    // means the count of points in 1 unit volume
    // unitNodesCount / narrowFactor = 1(unit volume)
    val narrowFactor = 0.5

    val volume = nodeCount/narrowFactor
    val radius = math.pow(volume, 1/3.0)

    // k = 1/sqrt(3) which is an approximate parameter to get the coor of a point
    val k = 0.6
    nodeArr.foreach(node => {
      node.px = ((Math.random()-0.5)*2*k*radius + center.x).asInstanceOf[Int]
      node.py = ((Math.random()-0.5)*2*k*radius + center.y).asInstanceOf[Int]
      node.pz = ((Math.random()-0.5)*2*k*radius + center.z).asInstanceOf[Int]
    })
  }


}
