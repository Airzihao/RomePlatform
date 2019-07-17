package grapheco.RomePlatform

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import com.google.gson.{Gson, GsonBuilder, JsonArray, JsonObject}
import com.sun.tools.internal.jxc.ConfigReader
import grapheco.RomePlatform.Layout.PhysicBody
import grapheco.RomePlatform.util.{Edge, JsonUtils, Node, SettingReader}
import org.neo4j.driver.v1._

abstract class DataExporter(physicBody: PhysicBody) {
  //wrapNode
  //wrapEdge
  //outputTo
}

class Neo4jExporter(physicBody: PhysicBody, settingReader: SettingReader) extends DataExporter(physicBody: PhysicBody){
  val _boltUrl = settingReader.getProp("neo4j.boltUrl")
  val _user = settingReader.getProp("neo4j.user")
  val _pwd = settingReader.getProp("neo4j.pwd")

  def export(): Unit ={
    val driver = GraphDatabase.driver(_boltUrl,AuthTokens.basic(_user,_pwd))
    val session = driver.session(AccessMode.WRITE)
    var count = 0
    physicBody.nodeArr.foreach(node => addNode(node,session) )
    physicBody.edgeArr.foreach(edge => addEdge(edge,session))
    session.close()
    driver.close()
  }


  def addNode(node: Node, session: Session): Unit ={
    //TO DO : make it configable
    val props = node.getAllPropsAsString()
    val statement: Statement = new Statement(s"Merge(n:Test$props);")
    session.run(statement)
  }
  def addEdge(edge: Edge, session: Session): Unit ={
    val props = edge.getAllPropsAsString()
    val fromId = edge.from
    val toId = edge.to
    println(fromId,toId,props)
    val cypher = s"Match(n1) Where n1.id=$fromId With n1 Match(n2) Where n2.id=$toId Create (n1)-[r:TEdge$props]->(n2);"
    println(cypher)
    val statement: Statement = new Statement(cypher)
    session.run(statement)
  }
}

class JsonExporter(physicBody: PhysicBody, conf: Map[String,Any]) extends DataExporter(physicBody :PhysicBody){

  val title:String = conf.get("title").get.toString
  var nodes:JsonArray = new JsonArray()
  var links:JsonArray = new JsonArray()


  def getJsonStr(): String ={
    for(node <- physicBody.nodeArr){
      nodes.add(wrapNode(node))
    }
    for(edge <- physicBody.edgeArr){
      links.add(wrapEdge(edge))
    }

    val jo = new JsonObject
    jo.add("nodes",nodes)
    jo.add("links",links)

    return new Gson().toJson(jo)
  }

  def outputTo(targetFilePath: String): Unit ={
    val file  = new File(targetFilePath)
    if(file.exists()){
      file.delete()
    }
    file.createNewFile()
    val writer = new FileWriter(file)
    val bfWriter = new BufferedWriter(writer)
    val result = getJsonStr()
    bfWriter.write(result)
    bfWriter.flush()
    bfWriter.close()
  }

  def wrapNode(node:Node): JsonObject = {
    var jo = new JsonObject
    jo.addProperty("id",node.id.toString)
    jo.addProperty("title",node.getProp(title).toString)
    addCoor(node,jo)
    //jo.addProperty("categories",node.getProp("categories").toString)
    //jo.addProperty("size", node.getProp("value").asInstanceOf[Int])
    return jo
  }
  def addCoor(node:Node, jo:JsonObject): Unit ={
    jo.addProperty("fx",node.px)
    jo.addProperty("fy",node.py)
    jo.addProperty("fz", node.pz)
  }

  def wrapEdge(edge: Edge): JsonObject = {
    var jo = new JsonObject
    jo.addProperty("source",edge.from.toString)
    jo.addProperty("target",edge.to.toString)
//    jo.addProperty("label",edge.getProp("name").toString)
    return jo
  }
}