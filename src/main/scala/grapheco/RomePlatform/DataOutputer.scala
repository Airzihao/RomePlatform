package grapheco.RomePlatform

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import com.google.gson.{Gson, GsonBuilder, JsonArray, JsonObject}
import grapheco.RomePlatform.Layout.PhysicBody
import grapheco.RomePlatform.util.{Edge, JsonUtils, Node}

abstract class DataExporter(physicBody: PhysicBody) {
  //wrapNode
  //wrapEdge
  //outputTo
}

class JsonExporter(physicBody: PhysicBody, conf: Map[String,Any]) extends DataExporter(physicBody :PhysicBody){

  val title:String = conf.get("title").get.toString
  //val targetFilePath = conf.get("targetFilePath").get.toString
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
    jo.addProperty("categories",node.getProp("categories").toString)
    jo.addProperty("size", node.getProp("value").asInstanceOf[Int])
    return jo
  }

  def wrapEdge(edge: Edge): JsonObject = {
    var jo = new JsonObject
    jo.addProperty("source",edge.from.toString)
    jo.addProperty("target",edge.to.toString)
    jo.addProperty("label",edge.getProp("name").toString)
    return jo
  }
}