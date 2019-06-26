package grapheco.RomePlatform.Layout
import grapheco.RomePlatform.util._

class Solver(physicBody: PhysicBody, conf: Map[String,Any]){
  val nodeArray = physicBody.nodeArr
  val edgeArray = physicBody.edgeArr
  var forces = physicBody.forces

  var dx : Double = 0
  var dy : Double = 0
  var dz : Double = 0
  var fx: Double = 0
  var fy: Double = 0
  var fz: Double = 0
  var distance : Double = 0
}

//make the origin at the center of the canvas



class SpringSolver(physicBody: PhysicBody, conf: Map[String,Any]) extends Solver(physicBody, conf: Map[String,Any]){

  var springForce: Double = 0

  def _calculateSpringForce(node1: Node, node2: Node, distance: Double): Unit = {

  }

}

class RepulsionSolver(physicBody: PhysicBody, conf: Map[String,Any]) extends Solver(physicBody, conf: Map[String,Any]){

  //useful var
  var repulsingForce: Double = 0

  val nodeDistance = conf.get("nodeDistance").get.asInstanceOf[Int]

  // approximation constants
  val a = (-2 / 3) / nodeDistance;
  val b = 4 / 3;

  def _calculateRepulsionForce(node1: Node,node2: Node, distance: Double): Unit = {


    if (distance < 2 * nodeDistance) {
      if (distance < 0.5 * nodeDistance) {
        repulsingForce = 1.0;
      }
      else {
        repulsingForce = a * distance + b; // linear approx of  1 / (1 + Math.exp((distance / nodeDistance - 1) * steepness))
      }
      repulsingForce = repulsingForce / distance;

      fx = dx * repulsingForce;
      fy = dy * repulsingForce;
      fz = dz * repulsingForce;
    }
  }
  def solve(): Unit ={
    for (i <- 0 until nodeArray.length-1){
      val node1 = nodeArray(i)
      for (j <- 1 until nodeArray.length){
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
