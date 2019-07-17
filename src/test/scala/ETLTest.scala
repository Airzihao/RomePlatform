import java.io.{BufferedWriter, File, FileWriter}

import com.google.gson.{Gson, JsonArray, JsonElement, JsonObject}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source


class Person(pid: Long, pName: String){
  var value = 0
  val name = pName
  val id = pid
}
object ETLTest {

  var personMap:Map[String,Person] = Map[String,Person]()
  //var mElementArrBuffer: ArrayBuffer[JsonObject] = ArrayBuffer[JsonObject]()
  //var personArrBuffer: ArrayBuffer[JsonObject] = ArrayBuffer[JsonObject]()
  var links = new JsonArray()
  val nodes = new JsonArray()
  var pid = 0

  def main(args: Array[String]): Unit ={
    val jsonStr = getJsonStr("E:\\WorkSpace\\Dataset\\dbmovies.json")
    val jsonArrBuffer = ArrayBuffer[JsonObject]()

    //deal with jsonArr
    val jsonIter = extractor(jsonStr).iterator()
    while (jsonIter.hasNext){
      val jsonObj = jsonIter.next().getAsJsonObject
      dealJsonObj(jsonObj)
    }

    personMap.foreach((x:(String,Person)) => {
      var jsonObj = new JsonObject
      jsonObj.addProperty("id",x._2.id)
      jsonObj.addProperty("name",x._2.name)
      jsonObj.addProperty("value",x._2.value.intValue())
      nodes.add(jsonObj)
    })

    outputTo("E:\\WorkSpace\\Dataset\\movies2.json")

  }

  def dealJsonObj(x:JsonObject): Unit ={



    val node = new JsonObject
    val id = x.get("id").getAsString
    val title = x.get("title").getAsString
    val cover = x.get("cover").getAsString
    val actorArr = if(x.get("actor").isJsonArray) x.get("actor").getAsJsonArray else new JsonArray()
    val iter = actorArr.iterator()
    node.addProperty("id",id)
    node.addProperty("title",title)
    node.addProperty("cover",cover)
    nodes.add(node)
    while(iter.hasNext){
      val name = iter.next().getAsString
      var link = new JsonObject
      if(personMap.contains(name)){
        personMap.get(name).get.value += 1
        link.addProperty("source",personMap.get(name).get.id)
        link.addProperty("target",id)
      }else{
        personMap += (name -> new Person(pid,name))
        link.addProperty("source",personMap.get(name).get.id)
        link.addProperty("target",id)
        pid +=1
      }
      links.add(link)
    }
  }

  def getJsonStr(path: String): String ={
    val jsonFile = Source.fromFile(path)
    return jsonFile.mkString
  }

  def extractor(jsonStr: String): JsonArray ={
    val jsonArray = new Gson().fromJson(jsonStr, classOf[JsonArray])
    return jsonArray
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
  def getJsonStr(): String ={
    val jo = new JsonObject
    jo.add("nodes",nodes)
    jo.add("links",links)
    return new Gson().toJson(jo)
  }
}
