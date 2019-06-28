package grapheco.RomePlatform.Layout
import grapheco.RomePlatform.util._

class Solver(physicBody: PhysicBody, conf: Map[String,Any]){
  val nodeArray = physicBody.nodeArr
  val edgeArray = physicBody.edgeArr
  var forces = physicBody.forces

  def getNode(id: Long): Node={
      for(node <- nodeArray){
        if(node.id == id){
          return node
        }
      }
    throw new IllegalArgumentException("Can not find the node by "+id);
  }

  def getNodeIndice(id: Long): Int = {
    for(i<-0 until nodeArray.length){
      if(nodeArray(i).id == id){
        return i
      }
    }
    throw throw new IllegalArgumentException("Can not find the node by "+id);
  }


}

/*
Only deal with the force, doesn't know coor
springForce: F=springFactor*d
 */
class SpringSolver(physicBody: PhysicBody, conf: Map[String,Any]) extends Solver(physicBody, conf: Map[String,Any]){

  //reus
  var dx : Double = 0
  var dy : Double = 0
  var dz : Double = 0
  var fx: Double = 0
  var fy: Double = 0
  var fz: Double = 0
  var distance : Double = 0
  var springForce: Double = 0
  val springFactor: Int = if(conf.contains("springFactor")) conf.get("springFactor").get.asInstanceOf[Int] else 1

  def _calculateSpringForce(node1: Node, node2: Node, distance: Double): Unit = {
    springForce = springFactor*distance
    springForce = springForce/distance
    fx = springForce*dx
    fy = springForce*dy
    fz = springForce*dz
  }

  def solve(){
    for(edge <- edgeArray){
      var node1 = getNode(edge.from)
      var node2 = getNode(edge.to)
      val indice1 = getNodeIndice(edge.from)
      val indice2 = getNodeIndice(edge.to)

      //TO DO: wrap the function as getDistance
      dx = node2.px-node1.px
      dy = node2.py-node1.py
      dz = node2.pz-node1.pz
      distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
      //make sure two nodes never 100% overlapping
      if (distance == 0) {
        distance = 0.1 * Math.random();
        dx = distance;
      }

      _calculateSpringForce(node1,node2,distance)
      forces(indice1).x += fx
      forces(indice1).y += fy
      forces(indice1).z += fz
      forces(indice2).x -= fx
      forces(indice2).y -= fy
      forces(indice2).z -= fz
    }
  }

}

/*
Only deal with the force, doesn't know coor
repulsionForce: F=repulsionFactor/d
 */
class RepulsionSolver(physicBody: PhysicBody, conf: Map[String,Any]) extends Solver(physicBody, conf: Map[String,Any]){


  //useful var
  var dx : Double = 0
  var dy : Double = 0
  var dz : Double = 0
  var fx: Double = 0
  var fy: Double = 0
  var fz: Double = 0
  var distance : Double = 0
  var repulsingForce: Double = 0
  val nodeDistance = if(conf.contains("nodeDistance")) 100 else conf.get("nodeDistance").get.asInstanceOf[Int]
  val repulsionFactor: Int = if(conf.contains("repulsionFactor")) conf.get("repulsionFactor").get.asInstanceOf[Int] else 225

  // approximation constants
  val a = (-2 / 3) / nodeDistance;
  val b = 4 / 3;

  def _calculateRepulsionForce(node1: Node,node2: Node, distance: Double): Unit = {
    if (distance < 2 * nodeDistance) {
      repulsingForce = repulsionFactor/distance;
      //get unit force value
      repulsingForce = repulsingForce / distance;
    } else{ //The two nodes are far from each other.
      repulsingForce = 0
    }
    fx = dx * repulsingForce;
    fy = dy * repulsingForce;
    fz = dz * repulsingForce;
  }

  def solve(): Unit ={
    for (i <- 0 until nodeArray.length-1){
      val node1 = nodeArray(i)
      for (j <- i until nodeArray.length){
        val node2 = nodeArray(j)

        dx = node2.px-node1.px
        dy = node2.py-node1.py
        dz = node2.pz-node1.pz
        distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        //make sure two nodes never 100% overlapping
        if (distance == 0) {
          distance = 0.1 * Math.random();
          dx = distance;
        }
        _calculateRepulsionForce(node1,node2,distance)
        forces(i).x -= fx;
        forces(i).y -= fy;
        forces(i).z -= fz;
        forces(j).x += fx;
        forces(j).y += fy;
        forces(j).z += fz;
      }
    }
  }


}
