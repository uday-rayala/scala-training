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

object Element {

  def main(args: Array[String]) {
    val e1 = Element("xx")
    val e2 = Element(Array("a", "bb", "ccc", "dddd"))
    println("e1 above e2:")
    println(e1 above e2)
    println()
    println("e2 beside e1:")
    println(e2 beside e1)
  }

  def apply(contents: Array[String]): Element = new ArrayElement(contents)

  def apply(line: String): Element = new LineElement(line)

  def apply(ch: Char, width: Int, height: Int): Element = new UniformElement(ch, width, height)

  private val LineSeparator = System.getProperty("line.separator", "\n")
}

abstract class Element {

  val contents: Array[String]

  def width: Int = if (contents.size == 0) 0 else contents.max.length

  def height: Int = contents.size

  def above(that: Element): Element =
    Element(this.widen(that.width).contents ++ that.widen(this.width).contents)

  def beside(that: Element): Element = {
    val thisContents = this.heighten(that.height).contents map { line => line + " " * (this.width - line.length) }
    val thatContents = that.heighten(this.height).contents
    Element(
        for {
          (thisLine, thatLine) <- thisContents zip thatContents
        } yield thisLine + thatLine)
  }

  override def toString = contents mkString Element.LineSeparator

  private def widen(newWidth: Int) =
    if (newWidth <= width) this
    else {
      val left = Element(' ', (newWidth - width) / 2, height)
      val right = Element(' ', newWidth - width - left.width, height)
      left beside this beside right
    }

  private def heighten(newHeight: Int) =
    if (newHeight <= height) this
    else {
      val top = Element(' ', width, (newHeight - height) / 2)
      val bottom = Element(' ', width, newHeight - height - top.height)
      top above this above bottom
    }
}

private class ArrayElement(override val contents: Array[String]) extends Element

private class LineElement(line: String) extends Element {

  override val contents = Array(line)

  override val width = line.length

  override val height = 1
}

private class UniformElement(ch: Char, override val width: Int, override val height: Int) extends Element {

  override val contents = Array.fill(height)(ch.toString * width)
}
