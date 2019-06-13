package grapheco.RomePlatform.util

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
    propMap.get(propName)
  }

  def setProp(key:String, value: Any)= {
    propMap += (key -> value)
  }

}
