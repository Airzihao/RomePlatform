import grapheco.RomePlatform.{JsonExporter, JsonImporter}
import grapheco.RomePlatform.Layout.{LayoutHandler, PhysicBody, RepulsionSolver, SpringSolver}
import grapheco.RomePlatform.util.SettingReader

object MainTest {
  def main(args: Array[String]): Unit ={
    val jsonImporter = new JsonImporter("D:\\GitSpace\\RomePlatform\\resource\\json.properties")
    val physicBody = new PhysicBody(jsonImporter.getNodeList(),jsonImporter.getEdgeList())
    val physicConf = new SettingReader("D:\\GitSpace\\RomePlatform\\resource\\physic.properties").getMap()

    val layoutHandler = new LayoutHandler(physicBody)
    layoutHandler.initPosition()
    layoutHandler.startSimulation()
    val exportConf = new SettingReader("D:\\GitSpace\\RomePlatform\\resource\\exporter.properties").getMap()
    val jsonExporter = new JsonExporter(physicBody,exportConf)
    jsonExporter.outputTo(exportConf.get("exportFilePath").get.toString)


  }
}
