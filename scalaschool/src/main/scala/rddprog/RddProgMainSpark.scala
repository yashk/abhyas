package rddprog

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object RddProgMainSpark {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    val conf = new SparkConf().setAppName("MainSparkBatch").setMaster("local[3]")
    val sc = new SparkContext(conf)
    rddExample(sc)

  }
  /*
  ## Resilient Distributed Datasets (RDDs)
  1. Spark revolves around the concept of a resilient distributed dataset (RDD),
  2. which is a fault-tolerant collection of elements that can be operated on in parallel.
  3. There are two ways to create RDDs: parallelizing an existing collection in your driver program,
  or referencing a dataset in an external storage system, such as a shared filesystem, HDFS, HBase,
  or any data source offering a Hadoop InputFormat.
*/

  def rddExample(sc: SparkContext): Unit ={
    //val data = Array(1, 2, 3, 4)
    val data = 1 to 100000 toArray
    val rdd = sc.parallelize(data)
    println(rdd.getNumPartitions)

    // 10 partitions
    val rddWithPart = sc.parallelize(data,10)

    // rdd is partitioned to cut dataset , spark runs a task to handle each partition.
    // typically 2-4 partitions per cpu core

  }





}
