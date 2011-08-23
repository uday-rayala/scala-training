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

import util.isIncreasing

object Train {

  implicit def toTrainBuilder(train: Train): TrainBuilder[ScheduleNotEmpty] = {
    require(train != null, "train must not be null!")
    new TrainBuilder(train.info, train.schedule)
  }
}

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(info != null, "info must not be null!")
  require(schedule != null, "schedule must not be null!")
  require(schedule.size >= 2, "schedule must have at least two stations!")
  require(isIncreasing(schedule map { _._1 }))

  val stations: Seq[Station] = schedule map { _._2 }

  val backToBackStations: Seq[(Station, Station)] = stations.init zip stations.tail

  val departureTimes: Map[Station, Time] = schedule map { _.swap } toMap
}

object Station {
  implicit def fromString(s: String): Station = Station(s)
}

case class Station(name: String) {
  require(name != null, "name must not be null!")
}

object TrainInfo {

  implicit def toTrainBuilder(info: TrainInfo): TrainBuilder[ScheduleEmpty] = {
    require(info != null, "info must not be null!")
    new TrainBuilder(info)
  }
}

sealed abstract class TrainInfo {
  def number: String
}

case class Ice(number: String, hasWifi: Boolean = false) extends TrainInfo

case class Re(number: String) extends TrainInfo

case class Brb(number: String) extends TrainInfo

private[scalatrain] class TrainBuilder[A <: TrainBuilderStatus](
    info: TrainInfo,
    schedule: Seq[(Time, Station)] = Seq.empty,
    time: Time = null) {

  def startsAt[B >: ScheduleEmpty <: A](time: Time): TrainBuilder[ScheduleEmptyAndTime] = {
    require(time != null, "time must not be null!")
    new TrainBuilder(info, time = time)
  }

  def startsFrom[B >: ScheduleEmptyAndTime <: A](station: Station): TrainBuilder[ScheduleNotEmpty] = {
    require(station != null, "station must not be null!")
    new TrainBuilder(info, Seq(time -> station))
  }

  def at[B >: ScheduleNotEmpty <: A](time: Time): TrainBuilder[ScheduleNotEmptyAndTime] = {
    require(time != null, "time must not be null!")
    new TrainBuilder(info, schedule, time)
  }

  def from[B >: ScheduleNotEmptyAndTime <: A](station: Station): Train = {
    require(station != null, "station must not be null!")
    Train(info, schedule ++ Seq(time -> station))
  }
}

private[scalatrain] sealed trait TrainBuilderStatus

sealed trait ScheduleEmpty extends TrainBuilderStatus

sealed trait ScheduleEmptyAndTime extends TrainBuilderStatus

sealed trait ScheduleNotEmpty extends TrainBuilderStatus

sealed trait ScheduleNotEmptyAndTime extends TrainBuilderStatus
