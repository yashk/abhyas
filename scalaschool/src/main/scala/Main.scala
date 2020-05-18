object Main {
  def main(args: Array[String]): Unit = {
    //println(forComprehension())
    //println(passingFunctionAsArgs())
  }

  /**
    *  example of for comprehension.
    */
  def forComprehension():Unit = {
    val aSeq = Seq("test1","test3","Test3")

    val aUpperCaseSeq = for {
      s <- aSeq
      sUpper = s.toUpperCase
    } yield {
      sUpper
    }

    print(aUpperCaseSeq)
  }

  def passingFunctionAsArgs():Unit ={
    val plays = List(
      "tamingoftheshrew", "comedyoferrors", "loveslabourslost", "midsummersnightsdream",
      "merrywivesofwindsor", "muchadoaboutnothing", "asyoulikeit", "twelfthnight")


    plays.foreach(s => println(s))
    plays.foreach(println)
    plays.foreach((str:String) => println(str))
    // place holder
    plays.foreach(println(_))

    // for longer functions
    plays.foreach{
      (str:String) => {
        println(str)
      }
    }


    val ints = 1 to 1000;


//    val sum= ints.reduceLeft((i,j) => i+j)
//    val sums = ints.reduceLeft(_)


  }

  def printSting(s:String):Unit ={
    println(s)
  }

}
