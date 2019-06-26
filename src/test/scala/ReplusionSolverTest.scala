import grapheco.RomePlatform.util._
import grapheco.RomePlatform.Layout._

object ReplusionSolverTest{
  def main(args: Array[String]): Unit ={
    val node1 = new Node(0,Map("name"->"bob"))
    val node2 = new Node(1,Map("name"->"alice"))
    val edge = new Edge(0,1,Map("weight"->5))

    val nodeList : List[Node] = List(node1,node2)
    val edgeList : List[Edge] = List(edge)
    val physicBody = new PhysicBody(nodeList,edgeList)

    val repulsionSolver = new RepulsionSolver(physicBody,Map("nodeDistance"->200))


    for(i <- 0 until physicBody.forces.length){
      println(physicBody.forces(i).x,physicBody.forces(i).y,physicBody.forces(i).z)
    }
    repulsionSolver.solve()
    for(i <- 0 until physicBody.forces.length){
      println(physicBody.forces(i).x,physicBody.forces(i).y,physicBody.forces(i).z)
    }
    repulsionSolver.solve()
    for(i <- 0 until physicBody.forces.length){
      println(physicBody.forces(i).x,physicBody.forces(i).y,physicBody.forces(i).z)
    }


  }
}