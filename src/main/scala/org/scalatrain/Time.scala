package org.scalatrain

import scala.util.control.Exception
import scala.xml.Elem
import scala.xml.Node

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
