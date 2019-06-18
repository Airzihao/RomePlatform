package grapheco.RomePlatform.Layout
import grapheco.RomePlatform.util._

class Solver(nodes: List[Node], edges: List[Edge], conf: Map[String,Any]){
  val nodeList = nodes
  val edgeList = edges
}

class SpringSolver(nodes: List[Node], edges: List[Edge], conf: Map[String,Any]) extends Solver(nodes: List[Node], edges: List[Edge], conf: Map[String,Any]){

}

class RepulsionSolver(nodes: List[Node], edges: List[Edge], conf: Map[String,Any]) extends Solver(nodes: List[Node], edges: List[Edge], conf: Map[String,Any]){

}
