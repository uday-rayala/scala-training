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

package org.scalatrain.util

import scala.xml.Elem

trait XmlSerializable[A] {

  def fromXml(xml: Elem)(implicit format: XmlFormat[A]): Option[A] = {
    require(xml != null, "xml must not be null!")
    require(format != null, "format must not be null!")
    format fromXml xml
  }

  implicit def toXmlOut(a: A): XmlOut[A] = {
    require(a != null, "a must not be null!")
    new XmlOut(a) 
  }
}

class XmlOut[A](a: A) {
  def toXml(implicit format: XmlFormat[A]): Elem = format toXml a
}

trait XmlFormat[A] {

  def fromXml(xml: Elem): Option[A]

  def toXml(a: A): Elem
}
