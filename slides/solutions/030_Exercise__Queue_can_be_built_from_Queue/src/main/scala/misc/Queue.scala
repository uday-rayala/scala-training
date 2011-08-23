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

import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.{ Builder, ListBuffer }

object Queue {

  def apply[A](as: A*): Queue[A] = new Queue(as.toList) 

  implicit def cbf[A, B]: CanBuildFrom[Queue[A], B, Queue[B]] = new CanBuildFrom[Queue[A], B, Queue[B]] {

    override def apply() = newBuilder

    override def apply(from: Queue[A]) = newBuilder
  }

  private def newBuilder[A]: Builder[A, Queue[A]] = new Builder[A, Queue[A]] {

    private val as = new ListBuffer[A]()

    override def +=(a: A): this.type = {
      as += a
      this
    }

    override def clear(): Unit = as.clear()

    override def result: Queue[A] = Queue(as: _*)
  }
}

class Queue[+A] private (private val as: List[A]) extends SeqLike[A, Queue[A]] {

  def dequeue: (A, Queue[A]) = as match {
    case a :: as => a -> new Queue(as)
    case _ => throw new NoSuchElementException("How should dequeue work on an empty Queue?")
  }

  def enqueue[B >: A](element: B): Queue[B] = new Queue(as :+ element)

  override def equals(that: Any): Boolean = 
    if (that.isInstanceOf[Queue[_]]) this.as == that.asInstanceOf[Queue[_]].as
    else false

  override def apply(i: Int): A = as(i)

  override def length: Int = as.length

  override def iterator: Iterator[A] = as.iterator

  override def seq: Queue[A] = this

  override protected[this] def newBuilder: Builder[A, Queue[A]] = Queue.newBuilder
}
