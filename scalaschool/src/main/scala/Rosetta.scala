import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Rosetta {

  case class Rating(user: String, item: String, score: Double)
  case class LogEvent(user: String, track: String, timestamp: Long)
  case class UserMeta(user: String, age: Int)


  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("MainSparkBatch").setMaster("local[1]")
    val sc = new SparkContext(conf)



    val data = List(Rating("123","book2",0.23d),
      Rating("124","book2",0.23d),
      Rating("125","book2",0.23d),
      Rating("126","book2",0.23d)
    )

    sc.parallelize(data)

    val input:RDD[Rating] = sc.parallelize(data)
    val result:Long = count(input)

    println(result)

    println(countItemsForUser(input,"123"))

    sc.stop();
  }

  /**
    * count total items
    * @param input
    * @return
    */
  def count(input:RDD[Rating]):Long = {
    input.count
  }

  /**
    *
    * @param input
    * @param user
    * @return
    */
  def countItemsForUser(input:RDD[Rating],user:String):Long = {
    input
      .filter(rating => rating.user == user)
      .count
  }


  def avgAgeForATrack(left: RDD[LogEvent],right: RDD[UserMeta]): RDD[(String,Double)] ={
    val sc = left.context

    val userMap = right.map( um => (um.user,um.age.toDouble)).collectAsMap()

    val userMapLookup = sc.broadcast(userMap)


    val joined = left.map(logevent => (logevent.track,userMapLookup.value.getOrElse(logevent.user,0.0)))

    val joinedOne = joined.mapValues((_,1.0))

    val joinCounted = joinedOne.reduceByKey((a,b) => (a._1+b._1 , a._2+b._2))

    val averaged = joinCounted.mapValues{
      case (sum,count) => sum / count
    }
    averaged
  }





}
