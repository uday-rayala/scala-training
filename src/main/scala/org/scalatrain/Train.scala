package org.scalatrain

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(info != null, "info must not be null!")
  require(schedule != null, "schedule must not be null!")
  require(schedule.size >= 2, "schedule must have at least two stations!")
  // TODO schedule must be monotonically increaing in time!

  val stations: Seq[Station] = schedule map { _._2 }
  val backToBackStations: Seq[(Station, Station)] = stations.init zip stations.tail
  val departureTimes: Map[Station, Time] = schedule map { _.swap } toMap
}

case class Station(name: String) {
  require(name != null, "name must not be null!")
} 

object Station {
  implicit def fromStringToStation(stationName: String): Station = Station(stationName)
}

case class Hop(from: Station, to: Station, train: Train) {
  val departureTime = train.departureTimes(from)
  val arrivalTime = train.departureTimes(to)  
}

sealed abstract class TrainInfo {
  def number: String
}

case class Ice(number: String, hasWifi: Boolean = false) extends TrainInfo

case class Re(number: String) extends TrainInfo

case class Brb(number: String) extends TrainInfo
