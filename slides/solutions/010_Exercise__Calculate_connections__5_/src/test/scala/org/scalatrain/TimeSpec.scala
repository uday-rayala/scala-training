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
  p^
  "Calling Time.isIncreasing" ^
    `with null should throw an IAE (isIncreasing)` ^
    `should return true for a Seq with 0 or 1 elements (isIncreasing)` ^
    `should return true for a Seq with increasing elements (isIncreasing)` ^
    `should return false for a Seq with not-increasing elements (isIncreasing)` ^
  p^
  "Calling Time.isIncreasingSliding" ^
    `with null should throw an IAE (isIncreasingSliding)` ^
    `should return true for a Seq with 0 or 1 elements (isIncreasingSliding)` ^
    `should return true for a Seq with increasing elements (isIncreasingSliding)` ^
    `should return false for a Seq with not-increasing elements (isIncreasingSliding)` ^
  p^
  "Calling Time.isIncreasingFold" ^
    `with null should throw an IAE (isIncreasingFold)` ^
    `should return true for a Seq with 0 or 1 elements (isIncreasingFold)` ^
    `should return true for a Seq with increasing elements (isIncreasingFold)` ^
    `should return false for a Seq with not-increasing elements (isIncreasingFold)` ^
  end

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

  def `with null should throw an IAE (isIncreasing)` =
    Time isIncreasing null must throwA[IAE]
  
  def `should return true for a Seq with 0 or 1 elements (isIncreasing)` =
    (Time isIncreasing Seq() must beTrue) and (Time isIncreasing Seq(Time()) must beTrue)
  
  def `should return true for a Seq with increasing elements (isIncreasing)` =
    (Time isIncreasing Seq(Time(1), Time(2)) must beTrue) and
        (Time isIncreasing Seq(Time(1), Time(2), Time(3)) must beTrue)
  
  def `should return false for a Seq with not-increasing elements (isIncreasing)` =
    (Time isIncreasing Seq(Time(1), Time(1)) must beFalse) and
        (Time isIncreasing Seq(Time(1), Time(2), Time(1)) must beFalse)

  def `with null should throw an IAE (isIncreasingSliding)` =
    Time isIncreasingSliding null must throwA[IAE]
  
  def `should return true for a Seq with 0 or 1 elements (isIncreasingSliding)` =
    (Time isIncreasingSliding Seq() must beTrue) and (Time isIncreasingSliding Seq(Time()) must beTrue)
  
  def `should return true for a Seq with increasing elements (isIncreasingSliding)` =
    (Time isIncreasingSliding Seq(Time(1), Time(2)) must beTrue) and
        (Time isIncreasingSliding Seq(Time(1), Time(2), Time(3)) must beTrue)
  
  def `should return false for a Seq with not-increasing elements (isIncreasingSliding)` =
    (Time isIncreasingSliding Seq(Time(1), Time(1)) must beFalse) and
        (Time isIncreasingSliding Seq(Time(1), Time(2), Time(1)) must beFalse)

  def `with null should throw an IAE (isIncreasingFold)` =
    Time isIncreasingFold null must throwA[IAE]
  
  def `should return true for a Seq with 0 or 1 elements (isIncreasingFold)` =
    (Time isIncreasingFold Seq() must beTrue) and (Time isIncreasingFold Seq(Time()) must beTrue)
  
  def `should return true for a Seq with increasing elements (isIncreasingFold)` =
    (Time isIncreasingFold Seq(Time(1), Time(2)) must beTrue) and
        (Time isIncreasingFold Seq(Time(1), Time(2), Time(3)) must beTrue)
  
  def `should return false for a Seq with not-increasing elements (isIncreasingFold)` =
    (Time isIncreasingFold Seq(Time(1), Time(1)) must beFalse) and
        (Time isIncreasingFold Seq(Time(1), Time(2), Time(1)) must beFalse)
}
