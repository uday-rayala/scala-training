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

// Animals

abstract class Animal {
  def eat(food: Food): Unit = ()
}

class Bird extends Animal

class Cow extends Animal

// Food

abstract class Food

case object Grains extends Food

case object Grass extends Food

// Feeding

object Feeding {

  def main(args: Array[String]): Unit = {

    val bird = new Bird
    bird.eat(Grass) // Unhealthy: Mustn't compile!

    val cow = new Cow
    cow.eat(Grains) // Unhealthy: Mustn't compile!
  }
}
