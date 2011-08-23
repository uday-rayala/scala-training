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
package util

import java.lang.{ IllegalArgumentException => IAE }
import org.junit.runner.RunWith
import org.specs2._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class utilSpec extends Specification { def is =

  "Calling isIncreasing" ^
    `with null should throw an IAE (isIncreasing)` ^
    `should return true for a Seq with 0 or 1 elements (isIncreasing)` ^
    `should return true for a Seq with increasing elements (isIncreasing)` ^
    `should return false for a Seq with not-increasing elements (isIncreasing)` ^
  p^
  "Calling isIncreasingSliding" ^
    `with null should throw an IAE (isIncreasingSliding)` ^
    `should return true for a Seq with 0 or 1 elements (isIncreasingSliding)` ^
    `should return true for a Seq with increasing elements (isIncreasingSliding)` ^
    `should return false for a Seq with not-increasing elements (isIncreasingSliding)` ^
  p^
  "Calling isIncreasingFold" ^
    `with null should throw an IAE (isIncreasingFold)` ^
    `should return true for a Seq with 0 or 1 elements (isIncreasingFold)` ^
    `should return true for a Seq with increasing elements (isIncreasingFold)` ^
    `should return false for a Seq with not-increasing elements (isIncreasingFold)` ^
  end

  def `with null should throw an IAE (isIncreasing)` =
    isIncreasing(null) must throwA[IAE]
  
  def `should return true for a Seq with 0 or 1 elements (isIncreasing)` =
    (isIncreasing(Seq()) must beTrue) and (isIncreasing(Seq(Time())) must beTrue)
  
  def `should return true for a Seq with increasing elements (isIncreasing)` =
    (isIncreasing(Seq(Time(1), Time(2))) must beTrue) and
        (isIncreasing(Seq(1, 2, 3)) must beTrue)
  
  def `should return false for a Seq with not-increasing elements (isIncreasing)` =
    (isIncreasing(Seq(Time(1), Time(1))) must beFalse) and
        (isIncreasing(Seq("a", "b", "a")) must beFalse)

  def `with null should throw an IAE (isIncreasingSliding)` =
    isIncreasingSliding(null) must throwA[IAE]
  
  def `should return true for a Seq with 0 or 1 elements (isIncreasingSliding)` =
    (isIncreasingSliding(Seq()) must beTrue) and (isIncreasingSliding(Seq(Time())) must beTrue)
  
  def `should return true for a Seq with increasing elements (isIncreasingSliding)` =
    (isIncreasingSliding(Seq(Time(1), Time(2))) must beTrue) and
        (isIncreasingSliding(Seq(1, 2, 3)) must beTrue)
  
  def `should return false for a Seq with not-increasing elements (isIncreasingSliding)` =
    (isIncreasingSliding(Seq(Time(1), Time(1))) must beFalse) and
        (isIncreasingSliding(Seq("a", "b", "a")) must beFalse)

  def `with null should throw an IAE (isIncreasingFold)` =
    isIncreasingFold(null) must throwA[IAE]
  
  def `should return true for a Seq with 0 or 1 elements (isIncreasingFold)` =
    (isIncreasingFold(Seq()) must beTrue) and (isIncreasingFold(Seq(Time())) must beTrue)
  
  def `should return true for a Seq with increasing elements (isIncreasingFold)` =
    (isIncreasingFold(Seq(Time(1), Time(2))) must beTrue) and
        (isIncreasingFold(Seq(1, 2, 3)) must beTrue)
  
  def `should return false for a Seq with not-increasing elements (isIncreasingFold)` =
    (isIncreasingFold(Seq(Time(1), Time(1))) must beFalse) and
        (isIncreasingFold(Seq("a", "b", "a")) must beFalse)
}
