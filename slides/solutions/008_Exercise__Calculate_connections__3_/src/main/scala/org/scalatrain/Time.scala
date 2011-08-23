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
import scala.util.control.Exception
import scala.xml.{ Elem, Node }

object Time {

  def fromMinutes(minutes: Int): Time = new Time(minutes / 60, minutes % 60)

  def fromXml(xml: Elem): Option[Time] = {
    require(xml != null, "xml must not be null!")
    for {
      hoursAttr <- (xml \\ "@hours").headOption
      hours <- toInt(hoursAttr)
      minutesAttr <- (xml \\ "@minutes").headOption
      minutes <- toInt(minutesAttr)
    } yield Time(hours, minutes)
  }

  @tailrec def isIncreasing(times: Seq[Time]): Boolean = {
    require(times != null, "times must not be null!")
    times match {
      case t1 :: t2 :: ts => (t1 < t2) && isIncreasing(t2 :: ts)
      case _ => true
    }
  }

  def isIncreasingSliding(times: Seq[Time]): Boolean = {
    require(times != null, "times must not be null!")
    times sliding 2 forall {
      case t1 :: t2 :: Nil => t1 < t2
      case _ => true
    }
  }

  def isIncreasingFold(times: Seq[Time]): Boolean = {
    require(times != null, "times must not be null!")
    val isIncreasingAndTime = times.foldLeft(true -> (None: Option[Time])) {
      case ((false, _), _) => false -> None
      case ((true, None), t1) => true -> Some(t1)
      case ((true, Some(t0)), t1) => (t0 < t1) -> Some(t1)
    }
    isIncreasingAndTime._1
  }

  private def toInt(attr: Node) = Exception.catching(classOf[NumberFormatException]) opt { attr.text.toInt }
}

case class Time(hours: Int = 0, minutes: Int = 0) extends Ordered[Time] {
  require(hours >= 0 && hours < 24, "hours must be within 0..23!")
  require(minutes >= 0 && minutes < 60, "minutes must be within 0..59!")

  lazy val asMinutes: Int = 60 * hours + minutes

  override val toString = "%02d:%02d".format(hours, minutes)

  def -(that: Time) = minus(that)

  def minus(that: Time): Int = {
    require(that != null, "that must not be null!")
    asMinutes - that.asMinutes
  }

  def toXml: Elem = <time hours={ "%02d" format hours } minutes={ "%02d" format minutes }/>

  override def compare(that: Time) = {
    require(that != null, "that must not be null!")
    this.asMinutes - that.asMinutes
  }
}
