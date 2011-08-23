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

import scala.annotation.tailrec

package object util {

  @tailrec def isIncreasing[A <% Ordered[A]](times: Seq[A]): Boolean = {
    require(times != null, "times must not be null!")
    times match {
      case t1 :: t2 :: ts => (t1 < t2) && isIncreasing(t2 :: ts)
      case _ => true
    }
  }

  def isIncreasingSliding[A <% Ordered[A]](times: Seq[A]): Boolean = {
    require(times != null, "times must not be null!")
    times sliding 2 forall {
      case t1 :: t2 :: Nil => t1 < t2
      case _ => true
    }
  }

  def isIncreasingFold[A <% Ordered[A]](times: Seq[A]): Boolean = {
    require(times != null, "times must not be null!")
    val isIncreasingAndTime = times.foldLeft(true -> (None: Option[A])) {
      case ((false, _), _) => false -> None
      case ((true, None), t1) => true -> Some(t1)
      case ((true, Some(t0)), t1) => (t0 < t1) -> Some(t1)
    }
    isIncreasingAndTime._1
  }
}
