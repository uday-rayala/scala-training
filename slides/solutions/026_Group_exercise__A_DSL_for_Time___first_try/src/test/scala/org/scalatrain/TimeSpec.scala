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
class TimeSpec extends Specification { def is =

  "Calling Time.fromString" ^
    `should create a properly initialized Time` ^
    `implicitly should create a properly initialized Time` ^
  p^
  "Calling Time.intToTimeBuilder" ^
    `with hours not in 0 to 23 should throw an IAE` ^
    `implicitly should create a properly initialized TimeBuilder` ^
  p^
  "Creating a Time" ^
    `with invalid hours should throw an IAE` ^
    `with invalid minutes should throw an IAE` ^
    `without arguments should return a Time(0, 0)` ^
  p^
  "Calling Time.minus" ^
    `with null should throw an IAE` ^
    `should return the correct time difference` ^            
  p^
  `The value Time.asMinutes should be initialized correctly` ^
  p^
  `The value Time.toString should be initialized correctly` ^
  p^
  "Comparing a Time" ^
    `with null should throw an IAE (comparing)` ^
    `should return the correct order` ^
  p^
  "Calling Time.fromXml" ^
    `with null should throw an IAE (fromXml)` ^
    `should return None for an invalid XML element` ^
    `should return Some for a valid XML element` ^
  p^
  "Calling Time.toXml and then Time.fromXml" ^
    `should return an equal Time instance wrapped in Some` ^
  end

  def `should create a properly initialized Time` =
    Time fromString null must throwA[IAE]

  def `implicitly should create a properly initialized Time` = {
    val time: Time = "9:45"
    time must_== Time(9, 45)
  }

  def `with hours not in 0 to 23 should throw an IAE` =
    (Time intToTimeBuilder -1 must throwA[IAE]) and (Time intToTimeBuilder 24 must throwA[IAE])

  def `implicitly should create a properly initialized TimeBuilder` = {
    import Time._
    (1: TimeBuilder).hours must_== 1
  }

  def `with invalid hours should throw an IAE` =
    (Time(-1) must throwA[IAE]) and (Time(24) must throwA[IAE])

  def `with invalid minutes should throw an IAE` =
    (Time(minutes = -1) must throwA[IAE]) and (Time(minutes = 60) must throwA[IAE])

  def `without arguments should return a Time(0, 0)` =
    Time() must_== Time(0, 0)

  def `with null should throw an IAE` =
    Time() minus null must throwA[IAE]

  def `should return the correct time difference` =
    Time(2, 2) - Time(1, 1) must_== 61

  def `The value Time.asMinutes should be initialized correctly` =
    Time(2, 2).asMinutes must_== 122

  def `The value Time.toString should be initialized correctly` =
    (Time(1).toString must_== "01:00") and (Time(12, 34).toString must_== "12:34")

  def `with null should throw an IAE (comparing)` =
    Time() < null must throwA[IAE]

  def `should return the correct order` =
    (Time() < Time(1) must_== true)

  def `with null should throw an IAE (fromXml)` =
    Time fromXml null must throwA[IAE]

  def `should return None for an invalid XML element` =
    Time fromXml <time/> must be(None)

  def `should return Some for a valid XML element` =
    (Time fromXml <time hours="12" minutes="01"/> must_== Some(Time(12, 1))) and
        (Time fromXml <time hours="12" minutes="1"/> must_== Some(Time(12, 1)))

  def `should return an equal Time instance wrapped in Some` =
    Time fromXml Time(12, 1).toXml must_== Some(Time(12, 1)) 
}
