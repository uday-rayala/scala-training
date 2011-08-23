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
class QueueSpec extends Specification { def is =

  "Calling Queue.dequeue" ^
    `on an empty Queue should throw a NoSuchElementException` ^
    `on a non-empty Queue should return the first element and a new Queue without the first element` ^
  p^
  "Calling Queue.equals" ^
    `with a Queue with equal elements should be true` ^
    `with a Queue with non-equal elements should be false` ^
  end

  def `on an empty Queue should throw a NoSuchElementException` =
    Queue().dequeue must throwA[NoSuchElementException]

  def `on a non-empty Queue should return the first element and a new Queue without the first element` =
    Queue(1, 2).dequeue must_== (1, Queue(2))

  def `with a Queue with equal elements should be true` =
    Queue(1, 2, 3) equals Queue(1, 2, 3) must beTrue

  def `with a Queue with non-equal elements should be false` =
    Queue(1, 2, 3) equals Queue(3, 2, 1) must beFalse
}
