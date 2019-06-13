import grapheco.RomePlatform.util._

object NodeTest {
  def main(args: Array[String]): Unit ={
    var nodeMap: Map[String,Any] = Map()
    nodeMap += ("name" -> "贾宝玉")
    nodeMap += ("age" -> 25)
    var node = new Node(0,nodeMap)
    println(node.px)
    println(node.getProp("name"))
    println(node.getProp("age"))
    node.px = 10
    println(node.px)
  }

}
