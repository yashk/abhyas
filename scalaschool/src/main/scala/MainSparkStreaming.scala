import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.dstream.{ConstantInputDStream, DStream}

import scala.util.Random // not necessary since Spark 1.3

object MainSparkStreaming {

  def main(args: Array[String]): Unit = {

    // transform operations
    // actions - same as RDDs
    // foreachRDD

    val conf = new SparkConf().setMaster("local[2]").setAppName("ConstantTest")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(15))

    val randomListFunction : () => List[String] = {
      () => {
        List.fill(10) (Random.alphanumeric.take(10).mkString)
      }
    }

    //val flist: List[() => String] = Seq.fill(1) (randomListFunction)
    val s:Seq[() => List[String]] = Seq.fill(1) (randomListFunction)



    val rdd = sc.parallelize(Seq.fill(1) (randomListFunction))

    val stream: DStream[() =>  List[String]] =
      new ConstantInputDStream(ssc, rdd)

    val m = stream.map( f => f())
    m.print()
    ssc.start()
    ssc.awaitTermination()



  }

}
