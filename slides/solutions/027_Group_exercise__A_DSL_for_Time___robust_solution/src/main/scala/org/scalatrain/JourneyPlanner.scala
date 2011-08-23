/*
 * Copyright 2011 Typesafe Inc.
 * 
 * This work is based on the original contribution of WeigleWilczek.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalatrain

import util.Logging
import scala.annotation.tailrec

class JourneyPlanner(trains: Set[Train]) extends Logging {
  require(trains != null, "trains must not be null!")

  logger.debug("Initialized with these trains:" + System.getProperty("line.separator", "\n") + trains)

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

  def connections(from: Station, to: Station, departureTime: Time): Set[Seq[Hop]] = {
    require(from != null, "from must not be null!")
    require(to != null, "to must not be null!")
    require(departureTime != null, "departureTime must not be null!")
    require(from != to, "from and to must not be equal!")

    def connections(soFar: Seq[Hop]): Set[Seq[Hop]] = {
      if (soFar.last.to == to)
        Set(soFar)
      else {
        val soFarStations = soFar.head.from +: (soFar map { _.to })
        val nextHops = hops.getOrElse(soFar.last.to, Set()) filter { hop =>
          hop.departureTime >= soFar.last.arrivalTime && !(soFarStations contains hop.to)
        }
        nextHops flatMap { hop => connections(soFar :+ hop) }
      }
    }

    val nextHops = hops.getOrElse(from, Set()) filter { _.departureTime >= departureTime }
    nextHops flatMap { hop => connections(Vector(hop)) }
  }
}

case class Hop(from: Station, to: Station, train: Train) {
  require(from != null, "from must not be null!")
  require(to != null, "to must not be null!")
  require(train != null, "train must not be null!")
  require(from != to, "from and to must not be equal!")
  require(train.backToBackStations contains from -> to, "from and to must be back-to-back stations of train!")

  val departureTime: Time = train.departureTimes(from)

  val arrivalTime: Time = train.departureTimes(to)
}
