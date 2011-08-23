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

object PowerOfFolding {

  def map[A, B](as: List[A])(f: A => B): List[B] = {
    require(as != null, "as must not be null!")
    require(f != null, "f must not be null!")
    as.foldLeft(List[B]()) { _ :+ f(_) }
  }

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = {
    require(as != null, "as must not be null!")
    require(f != null, "f must not be null!")
    as.foldLeft(List[B]()) { _ ++ f(_) }
  }

  def filter[A](as: List[A])(f: A => Boolean): List[A] = {
    require(as != null, "as must not be null!")
    require(f != null, "f must not be null!")
    as.foldLeft(List[A]()) { (as, a) => if (f(a)) as :+ a else as }
  }
}
