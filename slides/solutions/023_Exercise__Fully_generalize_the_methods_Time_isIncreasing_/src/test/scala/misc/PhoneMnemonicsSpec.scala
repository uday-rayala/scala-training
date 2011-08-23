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

package misc

import java.lang.{ IllegalArgumentException => IAE }
import org.junit.runner.RunWith
import org.specs2._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PhoneMnemonicsSpec extends Specification { def is =

  "Creating a PhoneMnemonics" ^
    `with a null dictionary should throw an IAE` ^
  p^
  `The value PhoneMnemonics.charCode should be initialized correctly` ^
  p^
  "Calling PhoneMnemonics.wordCode" ^
    `with null should throw an IAE (wordCode)` ^
    `should return the correct word codes` ^
  p^
  `The value PhoneMnemonics.wordsForNumber should be initialized correctly` ^
  p^
  "Calling PhoneMnemonics.encode" ^
    `with null should throw an IAE (encode)` ^
    `should return the correct set of words` ^
  end

  def `with a null dictionary should throw an IAE` =
    new PhoneMnemonics(null) must throwA[IAE]

  def `The value PhoneMnemonics.charCode should be initialized correctly` =
    phoneMnemonics.charCode must_== Map(
        'A' -> '2', 'B' -> '2', 'C' -> '2', 'D' -> '3', 'E' -> '3', 'F' -> '3',
        'G' -> '4', 'H' -> '4', 'I' -> '4', 'J' -> '5', 'K' -> '5', 'L' -> '5',
        'M' -> '6', 'N' -> '6', 'O' -> '6', 'P' -> '7', 'Q' -> '7', 'R' -> '7', 'S' -> '7',
        'T' -> '8', 'U' -> '8', 'V' -> '8', 'W' -> '9', 'X' -> '9', 'Y' -> '9', 'Z' -> '9')

  def `with null should throw an IAE (wordCode)` =
    phoneMnemonics wordCode null must throwA[IAE]

  def `should return the correct word codes` =
    phoneMnemonics wordCode "Java" must_== "5282"

  def `The value PhoneMnemonics.wordsForNumber should be initialized correctly` =
    phoneMnemonics.wordsForNumber must_== Map("5282" -> Set("Java", "Kata"))

  def `with null should throw an IAE (encode)` =
    phoneMnemonics encode null must throwA[IAE]

  def `should return the correct set of words` =
    (phoneMnemonics encode "5282" must_== Set(Seq("Java"), Seq("Kata"))) and
        (phoneMnemonics encode "52825282" must_== Set(Seq("Java", "Kata"), Seq("Java", "Java"), Seq("Kata", "Java"), Seq("Kata", "Kata"))) and
            (phoneMnemonics encode "123" must_== Set.empty)

  private val (java, kata) = ("Java", "Kata")

  private val phoneMnemonics = new PhoneMnemonics(Set(java, kata))
}
