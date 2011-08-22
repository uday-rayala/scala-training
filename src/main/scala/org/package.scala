package org

package object scalatrain {
  def isIncreasing[A <% Ordered[A]](elements: Seq[A]):Boolean = {
    elements sliding 2 forall { case Seq(first, second) => first < second }
  }
}