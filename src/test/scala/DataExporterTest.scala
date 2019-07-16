import grapheco.RomePlatform.{JsonImporter, Neo4jExporter}
import grapheco.RomePlatform.Layout.PhysicBody
import grapheco.RomePlatform.util.SettingReader

object DataExporterTest {
  def main(args: Array[String]): Unit ={
    val jsonImporter = new JsonImporter("D:\\GitSpace\\RomePlatform\\resource\\json.properties")
    val physicBody = new PhysicBody(jsonImporter.getNodeList(),jsonImporter.getEdgeList())
    val physicConf = new SettingReader("D:\\GitSpace\\RomePlatform\\resource\\physic.properties").getMap()
    val neo4jSetting = new SettingReader("D:\\GitSpace\\RomePlatform\\resource\\database.properties")
    neo4jEx(physicBody,neo4jSetting)
  }

  def jsonEx(): Unit ={

  }
  def neo4jEx(physicBody: PhysicBody,neo4jSetting: SettingReader): Unit ={
    val neo4jExporter = new Neo4jExporter(physicBody,neo4jSetting)
    neo4jExporter.export()
  }
}
