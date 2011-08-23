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
class PowerOfFoldingSpec extends Specification { def is =

  "Calling PowerOfFolding.map" ^
    `with a null list should throw an IAE (map)` ^
    `with a null function should throw an IAE (map)` ^
    `with an empty list should return an empty list (map)` ^
    `with an non-empty list should return a list with the elements correctly mapped by the given function` ^
  p^
  "Calling PowerOfFolding.flatMap" ^
    `with a null list should throw an IAE (flatMap)` ^
    `with a null function should throw an IAE (flatMap)` ^
    `with an empty list should return an empty list (flatMap)` ^
    `with an non-empty list should return a list with the elements correctly flatMapped by the given function` ^
  p^
  "Calling PowerOfFolding.filter" ^
    `with a null list should throw an IAE (filter)` ^
    `with a null function should throw an IAE (filter)` ^
    `with an empty list should return an empty list (filter)` ^
    `with an non-empty list should return a list with the elements correctly filtered by the given function` ^
  end

  // map

  def `with a null list should throw an IAE (map)` =
    PowerOfFolding.map(null: List[Int]) { x => x + 1 } must throwA[IAE]

  def `with a null function should throw an IAE (map)` =
    PowerOfFolding.map(List[Int]()) { null } must throwA[IAE]

  def `with an empty list should return an empty list (map)` =
    PowerOfFolding.map(List[Int]()) { x => x + 1 } must_== List[Int]()

  def `with an non-empty list should return a list with the elements correctly mapped by the given function` =
    PowerOfFolding.map(List(1, 2, 3)) { x => x + 1 } must_== List(2, 3, 4)

  // flatMap

  def `with a null list should throw an IAE (flatMap)` =
    PowerOfFolding.flatMap(null: List[Int]) { x => List(x - 1, x, x + 1) } must throwA[IAE]

  def `with a null function should throw an IAE (flatMap)` =
    PowerOfFolding.flatMap(List[Int]()) { null } must throwA[IAE]

  def `with an empty list should return an empty list (flatMap)` =
    PowerOfFolding.flatMap(List[Int]()) { x => List(x - 1, x, x + 1) } must_== List[Int]()

  def `with an non-empty list should return a list with the elements correctly flatMapped by the given function` =
    PowerOfFolding.flatMap(List(1, 2, 3)) { x => List(x - 1, x, x + 1) } must_== List(0, 1, 2, 1, 2, 3, 2, 3, 4)

  // filter

  def `with a null list should throw an IAE (filter)` =
    PowerOfFolding.filter(null: List[Int]) { x => x % 2 != 0 } must throwA[IAE]

  def `with a null function should throw an IAE (filter)` =
    PowerOfFolding.filter(List[Int]()) { null } must throwA[IAE]

  def `with an empty list should return an empty list (filter)` =
    PowerOfFolding.filter(List[Int]()) { x => x % 2 != 0 } must_== List[Int]()

  def `with an non-empty list should return a list with the elements correctly filtered by the given function` =
    PowerOfFolding.filter(List(1, 2, 3)) { x => x % 2 != 0 } must_== List(1, 3)
}
