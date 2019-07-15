package grapheco.RomePlatform.util

object IdGenerator {
  val initId = 0
  var id = initId

  def generateId(): Long = {
    id += 1
    id
  }

}
