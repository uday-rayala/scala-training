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

import java.lang.{ IllegalArgumentException => IAE }
import org.junit.runner.RunWith
import org.specs2._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TrainSpec extends Specification { def is =

  "Calling Train.toTrainBuilder" ^
    `with a null train should throw an IAE` ^
    `implicitly should create a properly initialized TrainBuilder (Train.toTrainBuilder)` ^
  p^
  "Creating a Train" ^
    `with a null info should throw an IAE` ^
    `with a null schedule should throw an IAE` ^
    `with an schedule of only 0 or 1 elements should throw an IAE` ^
    `with an schedule that isn't monotonically increasing in time` ^
  p^
  `The value Train.stations should be initialized correctly` ^
  p^
  `The value Train.backToBackStations should be initialized correctly` ^
  p^
  `The value Train.departureTimes should be initialized correctly` ^
  p^
  "Calling TrainInfo.toTrainBuilder" ^
    `with a null info should throw an IAE (TrainInfo.toTrainBuilder)` ^
    `implicitly should create a properly initialized TrainBuilder (TrainInfo.toTrainBuilder)` ^
  p^
  "Calling TrainBuilder." ^
    `startsAt with a null time should throw an IAE` ^
    `startsFrom with a null station should throw an IAE` ^
    `at with a null time should throw an IAE` ^
    `from with a null station should throw an IAE` ^
  end

  def `with a null train should throw an IAE` =
    Train toTrainBuilder null must throwA[IAE]

  def `implicitly should create a properly initialized TrainBuilder (Train.toTrainBuilder)` =
    (ice722: TrainBuilder[ScheduleNotEmpty]) must not be null

  def `with a null info should throw an IAE` =
    Train(null, Seq.empty) must throwA[IAE]

  def `with a null schedule should throw an IAE` =
    Train(Ice("722"), null) must throwA[IAE]

  def `with an schedule of only 0 or 1 elements should throw an IAE` =
    (Train(Ice("722"), Seq.empty) must throwA[IAE]) and
        (Train(Ice("722"), Seq(munichTime -> munich)) must throwA[IAE])

  def `with an schedule that isn't monotonically increasing in time` =
    Train(Ice("722"), Seq(frankfurtTime -> frankfurt, munichTime -> munich)) must throwA[IAE]

  def `The value Train.stations should be initialized correctly` =
    ice722.stations must_== Seq(munich, frankfurt, essen)

  def `The value Train.backToBackStations should be initialized correctly` =
    ice722.backToBackStations must_== Seq(munich -> frankfurt, frankfurt -> essen)

  def `The value Train.departureTimes should be initialized correctly` =
    ice722.departureTimes must_== Map(munich -> munichTime, frankfurt -> frankfurtTime, essen -> essenTime)

  def `with a null info should throw an IAE (TrainInfo.toTrainBuilder)` =
    TrainInfo toTrainBuilder null must throwA[IAE]

  def `implicitly should create a properly initialized TrainBuilder (TrainInfo.toTrainBuilder)` =
    (Ice("722"): TrainBuilder[ScheduleEmpty]) must not be null

  def `startsAt with a null time should throw an IAE` =
    new TrainBuilder[ScheduleEmpty](Ice("722")) startsAt null must throwA[IAE]

  def `startsFrom with a null station should throw an IAE` =
    new TrainBuilder[ScheduleEmpty](Ice("722")) startsAt "1:00" startsFrom null must throwA[IAE]

  def `at with a null time should throw an IAE` =
    new TrainBuilder[ScheduleEmpty](Ice("722")) startsAt "1:00" startsFrom "1" at null must throwA[IAE]

  def `from with a null station should throw an IAE` =
    new TrainBuilder[ScheduleEmpty](Ice("722")) startsAt "1:00" startsFrom "1" at "2:00" from null must throwA[IAE]

  private val (munich, frankfurt, essen) = (Station("Munich"), Station("Frankfurt"), Station("Essen"))

  private val (munichTime, frankfurtTime, essenTime) = (Time(9, 55), Time(13, 25), Time(15, 02))

  private val ice722 = Train(Ice("722"), Seq(munichTime -> munich, frankfurtTime -> frankfurt, essenTime -> essen))
}

@RunWith(classOf[JUnitRunner])
class StationSpec extends Specification { def is =

  "Creating a Station" ^
    `with a null name should throw an IAE` ^
  p^
  "Implicitly converting a String into a Station" ^
    `should create a properly initialized Station` ^
  end

  def `with a null name should throw an IAE` =
    Station(null) must throwA[IAE]

  def `should create a properly initialized Station` = {
    val name = "Munich"
    val station: Station = name
    station.name must_== name
  }
}
