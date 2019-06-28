package grapheco.RomePlatform.Layout

import grapheco.RomePlatform.util.SettingReader

class LayoutHandler(physicBody: PhysicBody) {
  val settingReader = new SettingReader("D:\\GitSpace\\RomePlatform\\resource\\physic.properties")
  val conf = settingReader.getMap()
  val repulsionSolver:RepulsionSolver = new RepulsionSolver(physicBody,conf)
  val springSolver:SpringSolver = new SpringSolver(physicBody,conf)

  var simulationFlag:Boolean = true
  var epochCount:Int = 0
  val maxEpochs: Int = conf.get("maxEpooch").get.toString.toInt

  //TO DO: Implement the startSimulation
  def startSimulation(): Unit ={
    simulationFlag = true
    while (simulationFlag & epochCount<maxEpochs){
      updateShift()
      epochCount+=1
    }
  }
  def initPosition(): Unit ={
    for(node<-physicBody.nodeArr){
      node.randomPosition()
    }
  }

  //TO DO: Implement the pause
  def pauseSimulation(): Unit ={

  }

  //TO DO: Implement the
  def stopSimulation(): Unit ={

  }

  //根据力计算位移
  //移动节点，更新节点坐标
  def updateShift(): Unit ={
    val nodeCount = physicBody.nodeArr.length
    springSolver.solve()
    repulsionSolver.solve()
    for(i <-0 until nodeCount){
      physicBody.nodeArr(i).px += physicBody.forces(i).x.toInt/100
      physicBody.nodeArr(i).py += physicBody.forces(i).y.toInt/100
      physicBody.nodeArr(i).pz += physicBody.forces(i).z.toInt/100
    }
  }


}
