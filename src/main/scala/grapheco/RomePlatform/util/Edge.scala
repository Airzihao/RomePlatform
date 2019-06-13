package grapheco.RomePlatform.util

class Edge(fromId: Long, toId: Long, props: Map[String, Any]) {

  val from = fromId
  val to = toId
  var propMap = props
  val weight = if(propMap.contains("weight")) propMap.get("weight") else 1

  def getProp(propName: String):Any = {
    propMap.get(propName)
  }

  def setProp(key:String, value: Any)= {
    propMap += (key -> value)
  }

}
