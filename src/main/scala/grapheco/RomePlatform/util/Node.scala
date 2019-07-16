package grapheco.RomePlatform.util

import org.json.JSONArray

import scala.Predef

class Node(nodeId: Long, props: Map[String, Any]) {
  val id = nodeId;
  var propMap = props;

  //position
  var px = 0
  var py = 0
  var pz = 0

  // force direct
  var dx = 0;
  var dy = 0;
  var dz = 0;


  def getProp(propName: String):Any = {
    propMap.get(propName).get
  }

  def setProp(key:String, value: Any)= {
    propMap += (key -> value)
  }
  def getAllPropsAsString():String = {
    var str = s"{id:$id"
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

      str+=(s",$key:$value")
    }
    str.+("}")
  }

  def randomPosition(): Unit ={
    px = (new util.Random).nextInt(200)
    py = (new util.Random).nextInt(200)
    pz = (new util.Random).nextInt(200)
  }

}
