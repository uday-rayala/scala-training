package org.scalatrain
import org.scalatrain.util.Logging

class JourneyPlanner(trains: Set[Train]) extends Logging {  
  require(trains != null, "trains must not be null!")
  
  logger.debug("Initialised with %d trains." format trains.size)

  val stations: Set[Station] = trains flatMap { _.stations }                                                        

  val hops: Map[Station, Set[Hop]] = {
    val hops = for {
      train <- trains
      (from, to) <- train.backToBackStations
    } yield Hop(from, to, train)
    hops groupBy { _.from }
  }

  def trainsAt(station: Station): Set[Train] = {
    require(station != null, "station must not be null!")
    trains filter { _.stations contains station }
  }

  def stopsAt(station: Station): Set[(Time, Train)] = {
    require(station != null, "station must not be null!")
    for {
      train <- trains
      (time, station2) <- train.schedule if station == station2
    } yield time -> train
  }

  def isShortTrip(from: Station, to: Station): Boolean = {
    require(from != null, "from must not be null!")
    require(to != null, "to must not be null!")
    trains exists {
      _.stations dropWhile { _ != from } match {
        case Seq(`from`, `to`, _*) => true 
        case Seq(`from`, _, `to`, _*) => true 
        case _ => false
      }
    }
  }
}

object JourneyPlanner {
  implicit def fromStringToStation(stationName: String): Station = Station(stationName)
}
