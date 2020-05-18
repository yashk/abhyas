import java.io.File;
import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level


object MainSpark {

  def main(args: Array[String]): Unit = {
    val shakespear=new File("/Users/ykochare/code/github/JustEnoughScalaForSpark/data/shakespeare")

    val success = if(shakespear.exists){
      println("exists")
      true
    }else{
      println("does not exists")
      false
    }




    val pathSep = File.separator
    val targetDir = shakespear.toString
    val plays = Seq("asyoulikeit","comedyoferrors"
      ,"loveslabourslost",
      "merrywivesofwindsor",
      "midsummersnightsdream",
      "muchadoaboutnothing",
      "tamingoftheshrew",
      "twelfthnight","yashdnmbas")


    if(success){

      Logger.getLogger("org").setLevel(Level.ERROR)
      Logger.getLogger("akka").setLevel(Level.ERROR)




      val failure = for {
        play <- plays
        playFileName = targetDir+pathSep+play
        playFile = new File(playFileName)
        if(playFile.exists == false)
      } yield {
        s"$playFileName:\t NOT FOUND"
      }

      if(failure.size == 0){
        info("all plays found!")

      }else{
        println("following plays were not found")
        failure.foreach(play => error(play))
      }

      println(failure)


//      val spark = SparkSession.builder.appName("Simple Application")
//        .config("spark.master","local")
//        .getOrCreate()

    }








  }

  def info(message: String): String = {
    println(message)

    // The last expression in the block, message, is the return value.
    // "return" keyword not required.
    // Do no additional formatting for the return string.
    message
  }

  def error(message: String): String = {

    // Print the string passed to "println" and add a linefeed ("ln"):
    // See the next cell for an explanation of how the string is constructed.
    val fullMessage = s"""
                         |********************************************************************
                         |
        |  ERROR: $message
                         |
        |********************************************************************
                         |""".stripMargin
    println(fullMessage)

    fullMessage
  }

}
