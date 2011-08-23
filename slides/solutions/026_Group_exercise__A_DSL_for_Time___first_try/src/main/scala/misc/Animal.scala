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

abstract class Animal[A <: AwakeOrAsleep] {

  type SuitableFood <: Food

  type This[A <: AwakeOrAsleep] <: Animal[A]

  def eat(food: SuitableFood): Unit = ()

  def id: this.type = this

  def wakeUp[B >: Asleep <: A]: This[Awake] = create[Awake]

  def goToSleep[B >: Awake <: A]: This[Asleep] = create[Asleep]

  protected def create[B <: AwakeOrAsleep]: This[B]
}

private[misc] sealed trait AwakeOrAsleep

sealed trait Awake extends AwakeOrAsleep

sealed trait Asleep extends AwakeOrAsleep

class Bird[A <: AwakeOrAsleep] extends Animal[A] {

  override type SuitableFood = Grains.type

  override type This[A <: AwakeOrAsleep] = Bird[A]

  override protected def create[B <: AwakeOrAsleep]: This[B] = new Bird
}

class Cow[A <: AwakeOrAsleep] extends Animal[A] {

  override type SuitableFood = Grass.type

  override type This[A <: AwakeOrAsleep] = Cow[A]

  override protected def create[B <: AwakeOrAsleep]: This[B] = new Cow
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

//  val fish = new Animal with Food {
//
//    override type SuitableFood = Food {
//      def swim(): Unit  // Want's to eat all that swims.
//    }
//
//    def swim(): Unit = ()
//  }
//  fish.eat(fish)
//  fish.id.eat(fish)
}
