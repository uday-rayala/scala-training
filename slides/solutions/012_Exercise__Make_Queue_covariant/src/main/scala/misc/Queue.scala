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

object Queue {
  def apply[A](as: A*): Queue[A] = new Queue(as.toList) 
}

class Queue[+A] private (private val as: List[A]) {

  def dequeue: (A, Queue[A]) = as match {
    case a :: as => a -> new Queue(as)
    case _ => throw new NoSuchElementException("How should dequeue work on an empty Queue?")
  }

  override def equals(that: Any): Boolean = 
    if (that.isInstanceOf[Queue[_]]) this.as == that.asInstanceOf[Queue[_]].as
    else false
}
