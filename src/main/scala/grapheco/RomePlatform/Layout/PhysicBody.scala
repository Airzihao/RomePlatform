package grapheco.RomePlatform.Layout

import grapheco.RomePlatform.util.{Edge, Node}

class PhysicBody(nodes: List[Node], edges: List[Edge]) {
  val nodeArr = nodes.toArray
  val edgeArr = edges.toArray

  var forces:Array[Force] = new Array[Force](nodeArr.length)
  for(i<-0 until forces.length){
    forces(i) = new Force
  }

}

class Force{
  var x: Double = 0
  var y: Double = 0
  var z: Double = 0
}
