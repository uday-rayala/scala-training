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
class JourneyPlannerSpec extends Specification { def is =

  "Creating a JourneyPlanner" ^
    `with null trains should throw an IAE` ^
  p^
  `The value JourneyPlanner.stations should be initialized correctly` ^
  p^
  "Calling JourneyPlanner.trainsAt" ^
    `with null should throw an IAE (trainsAt)` ^
    `should return the correct trains` ^
  p^
  "Calling JourneyPlanner.stopsAt" ^
    `with null should throw an IAE (stopsAt)` ^
    `should return the correct departures` ^
  p^
  "Calling JourneyPlanner.isShortTrip" ^
    `with a null from station should throw an IAE` ^
    `with a null to station should throw an IAE` ^
    `with identical stations should be false` ^
    `should be false for not existing connections` ^
    `should be false for too long connections` ^
    `should be true for short trips` ^
  p^
  "Calling JourneyPlanner.hops" ^
    `should initialise hops grouped by departure station` ^
  end
  
  def `should initialise hops grouped by departure station` = {
    planner.hops must_== Map(
      munich -> Set(
        Hop(munich, nuremberg, ice722),
        Hop(munich, nuremberg, ice724)
      ), 
      nuremberg -> Set(
        Hop(nuremberg, frankfurt, ice722),
        Hop(nuremberg, frankfurt, ice724)
      ),
      frankfurt -> Set(
        Hop(frankfurt, essen, ice722),
        Hop(frankfurt, cologne, ice724)      
      )
    )
  }

  def `with null trains should throw an IAE` =
    new JourneyPlanner(null) must throwA[IAE]

  def `The value JourneyPlanner.stations should be initialized correctly` =
    planner.stations must_== Set(munich, nuremberg, frankfurt, cologne, essen)

  def `with null should throw an IAE (trainsAt)` =
    planner stopsAt null must throwA[IAE]

  def `should return the correct trains` =
    (planner trainsAt munich must_== Set(ice722, ice724)) and (planner trainsAt essen must_== Set(ice722)) and
        (planner trainsAt Station("unknown") must_== Set())

  def `with null should throw an IAE (stopsAt)` =
    planner stopsAt null must throwA[IAE]

  def `should return the correct departures` =
    (planner stopsAt frankfurt must_== Set(frankfurtTimeIce722 -> ice722, frankfurtTimeIce724 -> ice724)) and
        (planner stopsAt cologne must_== Set(cologneTimeIce724 -> ice724))

  def `with a null from station should throw an IAE` =
    planner.isShortTrip(null, frankfurt) must throwA[IAE]

  def `with a null to station should throw an IAE` =
    planner.isShortTrip(munich, null) must throwA[IAE]

  def `with identical stations should be false` =
    planner.isShortTrip(munich, munich) must beFalse

  def `should be false for not existing connections` =
    planner.isShortTrip(munich, Station("unknown")) must beFalse

  def `should be false for too long connections` =
    planner.isShortTrip(munich, essen) must beFalse

  def `should be true for short trips` =
    (planner.isShortTrip(munich, nuremberg) must beTrue) and (planner.isShortTrip(nuremberg, cologne) must beTrue) and
        (planner.isShortTrip(nuremberg, essen) must beTrue)

  val (munich, nuremberg, frankfurt, cologne, essen) =
    (Station("Munich"), Station("Nuremberg"), Station("Frankfurt"), Station("Cologne"), Station("Essen"))

  val (munichTimeIce722, nurembergTimeIce722, frankfurtTimeIce722, essenTimeIce722) =
    (Time(9, 55), Time(11), Time(13, 10), Time(15, 02))

  val ice722 = Train(Ice("722"), Seq(
      munichTimeIce722 -> munich, 
      nurembergTimeIce722 -> nuremberg, 
      frankfurtTimeIce722 -> frankfurt, 
      essenTimeIce722 -> essen))

  val (munichTimeIce724, nurembergTimeIce724, frankfurtTimeIce724, cologneTimeIce724) =
    (Time(8, 55), Time(10), Time(12, 10), Time(13, 39))

  val ice724 = Train(Ice("724"), Seq(
      munichTimeIce724 -> munich, 
      nurembergTimeIce724 -> nuremberg, 
      frankfurtTimeIce724 -> frankfurt, 
      cologneTimeIce724 -> cologne))

  val planner = new JourneyPlanner(Set(ice722, ice724))
}
