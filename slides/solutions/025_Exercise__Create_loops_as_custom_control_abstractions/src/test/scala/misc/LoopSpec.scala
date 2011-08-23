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
class LoopSpec extends Specification { def is =

  "Calling Loop.repeatWhile" ^
    `with proper arguments should loop correctly` ^
  p^
  "Calling Loop.repeat" ^
    `with proper arguments should loop correctly (repeat)` ^
  end

  import Loop._

  def `with proper arguments should loop correctly` = {
    var counter = 0
    repeatWhile(counter < 10) {
      counter += 1
    }
    counter must_== 10
  }

  def `with proper arguments should loop correctly (repeat)` = {
    var counter = 0
    repeat {
      counter += 1
    } until(counter >= 10)
    counter must_== 10
  }

}
