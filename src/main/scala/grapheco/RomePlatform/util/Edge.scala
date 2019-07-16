package grapheco.RomePlatform.util

import org.json.JSONArray

class Edge(fromId: Long, toId: Long, props: Map[String, Any]) {

  val from = fromId
  val to = toId
  var propMap = props
  val weight = if(propMap.contains("weight")) propMap.get("weight") else 1

  def getProp(propName: String):Any = {
    propMap.get(propName).get
  }

  def setProp(key:String, value: Any)= {
    propMap += (key -> value)
  }
  def getAllPropsAsString():String = {
    if(propMap.isEmpty)
      return ""
    else{
      var str = s"{"
      for (elem <- propMap) {
        val key = elem._1
        val value = {
          elem._2 match {
            case l: Long => l
            case i: Int => i
            case d: Double => d
            case a: Array[_] => a
            case j: JSONArray => j
            case o => "\""+o.toString+"\""
          }
        }
        if(str.last.toString.equals("{"))
          str+=(s"$key:$value")
        else
          str+=(s",$key:$value")

      }
     return str.+("}")
    }

  }



}
