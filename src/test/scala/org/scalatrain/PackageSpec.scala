package org.scalatrain

import org.junit.runner.RunWith
import org.specs2._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PackageSpec extends Specification { def is =

  "isIncreasing" ^
    `should return true if objects are strictly increasing` ^
    `should return false if objects are not strictly increasing` ^
  end                 
  
  def `should return true if objects are strictly increasing` =
    (isIncreasing(Seq(Time(1), Time(1,1))) must beTrue) and
    (isIncreasing(Seq(1, 2, 3)) must beTrue)
    
  def `should return false if objects are not strictly increasing` = 
    (isIncreasing(Seq(Time(1, 1), Time(1))) must beFalse) and        
    (isIncreasing(Seq(Time(1, 3), Time(1, 2))) must beFalse) and
    (isIncreasing(Seq(Time(1, 3), Time(1, 2), Time(1,5))) must beFalse) and    
    (isIncreasing(Seq(2, 3, 1)) must beFalse)  
}
