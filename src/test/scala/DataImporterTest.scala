import grapheco.RomePlatform.JsonImporter

object DataImporterTest {
  def main(args: Array[String]): Unit ={
    val jsonImporter = new JsonImporter("D:\\GitSpace\\RomePlatform\\resource\\json.properties")
    jsonImporter.getNodeList()
    jsonImporter.getEdgeList()
  }


}
