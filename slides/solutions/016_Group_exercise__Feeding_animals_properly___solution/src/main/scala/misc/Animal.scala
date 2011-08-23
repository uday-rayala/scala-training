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

  type SuitableFood <: Food

  def eat(food: SuitableFood): Unit = ()

  def id: this.type = this
}

class Bird extends Animal {
  override type SuitableFood = Grains.type
}

class Cow extends Animal {
  override type SuitableFood = Grass.type
}

// Food

trait Food

case object Grains extends Food

case object Grass extends Food

// Feeding

object Feeding {

  def main(args: Array[String]): Unit = {

    val bird = new Bird
//    bird.eat(Grass) // Unhealthy: Mustn't compile!
    bird.eat(Grains)
    bird.id.eat(Grains)

    val cow = new Cow
//    cow.eat(Grains) // Unhealthy: Mustn't compile!
    cow.eat(Grass)
    cow.id.eat(Grass)
  }

  val fish = new Animal with Food {

    override type SuitableFood = Food {
      def swim(): Unit  // Want's to eat all that swims.
    }

    def swim(): Unit = ()
  }
  fish.eat(fish)
  fish.id.eat(fish)
}
