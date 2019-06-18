package grapheco.RomePlatform.util
import java.util.Properties
import java.io.{FileInputStream, FileNotFoundException}

class SettingReader(filePath:String) {
  val properties = new Properties()
  val path = filePath

  try{
    properties.load(new FileInputStream(path))
  }catch {
    case ex: FileNotFoundException =>{
      throw new FileNotFoundException("File not found: "+path)
    }
  }

  def getProp(propName:String):String = {
    try{
      properties.getProperty(propName).toString
    }catch {
      case ex: NullPointerException =>{
        throw new NullPointerException(s"The property: ${propName}, is not found.")
      }
    }
  }


}


