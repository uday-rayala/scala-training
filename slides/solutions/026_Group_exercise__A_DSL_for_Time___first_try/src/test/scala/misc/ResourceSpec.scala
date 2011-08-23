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

import java.io.{ InputStream, FileInputStream }
import java.lang.{ IllegalArgumentException => IAE }
import org.junit.runner.RunWith
import org.specs2._
import org.specs2.mock.Mockito
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ResourceSpec extends Specification with Mockito { def is =

  "Calling Resource.withResource" ^
    `with a null resource should throw an IAE` ^
    `with a null f should throw an IAE` ^
    `with proper arguments should return the correct result and close the resource` ^
  end

  import Resource._

  def `with a null resource should throw an IAE` =
    withResource(null)(Map[InputStream, String]()) must throwA[IAE]

  def `with a null f should throw an IAE`=
    withResource(mock[InputStream])(null: Map[InputStream, String]) must throwA[IAE]

  def `with proper arguments should return the correct result and close the resource` = {
    val in = mock[InputStream]
    in.read() returns 1
    (withResource(in) { resource => resource.read() } must_== 1) and
        (there was one(in).close())
  }
}
