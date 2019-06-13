package grapheco.RomePlatform

import org.neo4j.driver.v1.{AuthTokens, GraphDatabase}
import util.SettingReader

class DataImporter {

}

class Neo4jImporter extends DataImporter{

  val setting = new SettingReader("./database.properties")
  val _url = setting.getProp("neo4j.boltUrl")
  val _user = setting.getProp("neo4j.User")
  val _pwd = setting.getProp("neo4j.Password")


  val driver = GraphDatabase.driver(_url,AuthTokens.basic(_user, _pwd))


  //execute Cypher
  //queryNodes
  //queryRels
}

class JenaImporter extends DataImporter{

} 