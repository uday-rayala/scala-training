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

import util.{ XmlFormat, XmlSerializable }

import scala.util.control.Exception
import scala.xml.{ Elem, Node }

object Time extends XmlSerializable[Time] {

  implicit val timeXmlFormat = new XmlFormat[Time] {

    override def fromXml(xml: Elem): Option[Time] = {
      require(xml != null, "xml must not be null!")
      for {
        hoursAttr <- (xml \\ "@hours").headOption
        hours <- toInt(hoursAttr)
        minutesAttr <- (xml \\ "@minutes").headOption
        minutes <- toInt(minutesAttr)
      } yield Time(hours, minutes)
    }

    override def toXml(time: Time): Elem =
      <time hours={ "%02d" format time.hours } minutes={ "%02d" format time.minutes }/>
  }

  private val timePattern = """(\d{1,2}):(\d{1,2})""".r

  def fromMinutes(minutes: Int): Time = new Time(minutes / 60, minutes % 60)

  implicit def fromString(s: String): Time = {
    require(s != null, "s must not be null!")
    val timePattern(hours, minutes) = s
    Time(hours.toInt, minutes.toInt)
  }

  implicit def intToTimeBuilder(hours: Int): TimeBuilder[PreH] = {
    require(hours >= 0 && hours < 24, "hours must be within [0, 24)!")
    new TimeBuilder(hours)
  }

  private def toInt(attr: Node): Option[Int] = Exception.catching(classOf[NumberFormatException]) opt { attr.text.toInt }
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

  override def compare(that: Time) = {
    require(that != null, "that must not be null!")
    this.asMinutes - that.asMinutes
  }
}

private[scalatrain] class TimeBuilder[A <: TimeBuilderState](val hours: Int, val minutes: Int = 0) {

  def h[B >: PreH <: A] = new TimeBuilder[PostH](hours)

  def apply[B >: PostH <: A](minutes: Int) = new TimeBuilder[PreM](hours, minutes)

  def m[B >: PreM <: A] = Time(hours, minutes)
}

private[scalatrain] sealed trait TimeBuilderState

private[scalatrain] sealed trait PreH extends TimeBuilderState

private[scalatrain] sealed trait PostH extends TimeBuilderState

private[scalatrain] sealed trait PreM extends TimeBuilderState
