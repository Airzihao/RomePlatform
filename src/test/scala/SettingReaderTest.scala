import grapheco.RomePlatform.util.SettingReader

object SettingReaderTest {
  def main(args: Array[String]): Unit = {
    val settingReader = new SettingReader("./database.properties");
    println(settingReader.getProp("neo4j.boltUrl"))
    println(settingReader.getProp("communityField"))
    println(settingReader.getProp("commueld"))
  }
}
