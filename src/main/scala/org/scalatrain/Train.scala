package org.scalatrain

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(info != null, "info must not be null!")
  require(schedule != null, "schedule must not be null!")
  require(schedule.size >= 2, "schedule must have at least two stations!")
  // TODO schedule must be monotonically increaing in time!

  val stations: Seq[Station] = schedule map { _._2 }
}

case class Station(name: String) {
  require(name != null, "name must not be null!")
}

sealed abstract class TrainInfo {
  def number: String
}

case class Ice(number: String, hasWifi: Boolean = false) extends TrainInfo

case class Re(number: String) extends TrainInfo

case class Brb(number: String) extends TrainInfo
