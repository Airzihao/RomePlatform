import grapheco.RomePlatform.util.IdGenerator

object IdGeneratorTest {

  def main(args: Array[String]): Unit ={
    println(IdGenerator.id)
    println(IdGenerator.generateId())
    println(IdGenerator.id)
  }



}
