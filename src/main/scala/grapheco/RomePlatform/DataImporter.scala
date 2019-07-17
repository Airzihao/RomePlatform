package grapheco.RomePlatform

import java.io._

import org.neo4j.driver.v1.{AuthTokens, GraphDatabase, Session}
import util.{Edge, Node, SettingReader}

import scala.io.Source
import org.json.JSONArray
import org.json.JSONObject

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


abstract class DataImporter {
  def wrapNode(): Node
}

class Neo4jImporter extends DataImporter{


  //lazy val nodeList: List[Node]
  //TO DO: implement this function
  override def wrapNode(): Node = {
    var props:Map[String,Any] = Map[String,Any]()
    val id = 0
    val node = new Node(id,props)
    return node
  }

  val setting = new SettingReader("./database.properties")
  val _url = setting.getProp("neo4j.boltUrl")
  val _user = setting.getProp("neo4j.User")
  val _pwd = setting.getProp("neo4j.Password")

  val driver = GraphDatabase.driver(_url,AuthTokens.basic(_user, _pwd))

  private def queryNodes(session: Session): Unit ={
    session.run("MATCH(n) RETURN n")
  }


  //execute Cypher
  //queryNodes
  //queryRels
}

class JenaImporter extends DataImporter{
  //TO DO: implement this function
  override def wrapNode(): Node = {
    var props:Map[String,Any] = Map[String,Any]()
    val id = 0
    val node = new Node(id,props)
    return node
  }

}

class JsonImporter(propFilePath: String) extends DataImporter{


  override def wrapNode(): Node = {
    var props:Map[String,Any] = Map[String,Any]()
    val id = 0
    val node = new Node(id,props)
    return node
  }

  private val setting = new SettingReader(propFilePath)
  private val jsonPath = setting.getProp("jsonPath")
  private val jsonStr = getJsonStr(jsonPath)
  private val jsonObject: JSONObject = new JSONObject(jsonStr)
  private val nodesArray: JSONArray = jsonObject.get("nodes").asInstanceOf[JSONArray]
  private val edgesArray: JSONArray = {
    if(jsonObject.has("edges"))  jsonObject.get("edges").asInstanceOf[JSONArray]
    else jsonObject.get("links").asInstanceOf[JSONArray]
  }

  private def getJsonStr(filePath: String): String ={
    val jsonFile = Source.fromFile(filePath)
    return jsonFile.mkString
  }

  def getNodeList(): List[Node] ={
    var nArrayBuffer: ArrayBuffer[Node] = ArrayBuffer[Node]()

    val ni = nodesArray.iterator()
    while (ni.hasNext){
      //val node = new Node()
      val tNode = ni.next().asInstanceOf[JSONObject]
      val id = tNode.getLong("id")
      var props = Map[String,Any]()

      val pi = tNode.keys()
      while (pi.hasNext){
        val key = pi.next()
        if(key!="id"){
          props+=(key -> tNode.get(key))
        }
      }

      val node = new Node(id,props)
      nArrayBuffer.append(node)
    }
    val nodeList: List[Node] = nArrayBuffer.toList
    return nodeList

  }
  def getEdgeList(): List[Edge] = {
    var eArrayBuffer = ArrayBuffer[Edge]()
    val ei = edgesArray.iterator()
    while(ei.hasNext){
      val tEdge = ei.next().asInstanceOf[JSONObject]
      val from = tEdge.getLong("source")
      val to = tEdge.getLong("target")
      val pi = tEdge.keys()
      var props = Map[String,Any]()
      while(pi.hasNext){
        val key = pi.next()
        if(key!="source" & key!="target"){
          props+=(key->tEdge.get(key))
        }
      }
      val edge = new Edge(from,to,props)
      eArrayBuffer.append(edge)
    }
    val edgeList: List[Edge] = eArrayBuffer.toList
    return edgeList
  }


}