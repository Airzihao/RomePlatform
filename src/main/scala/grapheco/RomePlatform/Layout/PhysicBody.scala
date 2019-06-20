package grapheco.RomePlatform.Layout

import grapheco.RomePlatform.util.{Edge, Node}

class PhysicBody(nodes: List[Node], edges: List[Edge]) {
  val nodeArr = nodes.toArray
  val edgeArr = edges.toArray
  var forces = new Array[Force](nodeArr.length)

}

class Force{
  var x = 0
  var y = 0
  var z = 0
}