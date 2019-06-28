import grapheco.RomePlatform.util.SettingReader

object SettingReaderTest {
  def main(args: Array[String]): Unit = {
    val settingReader = new SettingReader("D:\\GitSpace\\RomePlatform\\resource\\database.properties");
    val map = settingReader.getMap()
    println(settingReader.getProp("neo4j.boltUrl"))
    println(settingReader.getProp("communityField"))
    println(settingReader.getProp("commueld"))

  }
}
