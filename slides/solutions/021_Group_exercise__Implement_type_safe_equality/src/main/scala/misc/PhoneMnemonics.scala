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

class PhoneMnemonics(words: Set[String]) {
  require(words != null, "words must not be null!")

  val mnemonics = Map(
      '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
      '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

  /**
   * Inverse of the mnemonics map, e.g. 'A' -> 2, 'B' -> '2'.
   */
  val charCode: Map[Char, Char] = for {
    (digit, chars) <- mnemonics
    char <- chars
  } yield char -> digit

  /**
   * Maps a word to its digit representation, e.g. "Java" -> "5282".
   */
  def wordCode(word: String): String = {
    require(word != null, "word must not be null!")
    word.toUpperCase map charCode
  }

  /**
   * Maps from numbers to the words that represent these, e.g. "5282" -> Set("Java", "Kata", ...).
   */
  val wordsForNumber: Map[String, Set[String]] = words groupBy wordCode

  /**
   * All ways to encode a number as a sequence of words.
   */
  def encode(number: String): Set[Seq[String]] = {
    require(number != null)
    if (number.isEmpty) Set(Seq.empty)
    else {
      val splitPoints = (1 to number.length).toSet
      for {
        splitPoint <- splitPoints
        head <- wordsForNumber.getOrElse(number take splitPoint, Set())
        tail <- encode(number drop splitPoint)
      } yield head +: tail
    }
  }

  /**
   * Maps a number to the set of all phrases that can represent it.  
   */
  def translate(number: String): Set[String] = encode(number) map { _ mkString " " } 
}
