import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object MainSparkBatch {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("MainSparkBatch").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)

    val result:Int = distData.reduce((a,b) => a + b)
    println(result)

  }

}
