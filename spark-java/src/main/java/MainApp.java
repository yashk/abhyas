import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import scala.Tuple2;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainApp {

    public static void main(String[] args) {
        String logFile = "/Users/ykochare/software/spark-notebook-0.6.3-scala-2.11.7-spark-1.6.2-hadoop-2.7.2-with-hive-with-parquet/README.md"; // Should be some file on your system

        // Two abstractions
        // RDD and shared variables (broadcast and accumulator)
        // The main abstraction Spark provides is a resilient distributed dataset (RDD),
        // which is a collection of elements partitioned across the nodes of the cluster that can be operated on in parallel.
        // RDDs are created by starting with a file in the Hadoop file system (or any other Hadoop-supported file system),
        // or an existing Scala collection in the driver program, and transforming it. Users may also ask Spark to persist an RDD in memory,
        // allowing it to be reused efficiently across parallel operations. Finally, RDDs automatically recover from node failures.

        SparkConf sparkConf = new SparkConf().setAppName("MainApp").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        // paralle collections
//        List<Integer> list = Arrays.asList(1,2,3,4,5);
//        JavaRDD<Integer> parallelize = sc.parallelize(list);
//        parallelize.map( i -> i*i)
//                .collect().forEach(System.out::println);

        JavaRDD<String> lines = sc.textFile("/Users/ykochare/software/spark-notebook-0.6.3-scala-2.11.7-spark-1.6.2-hadoop-2.7.2-with-hive-with-parquet/README.md");
        int total = lines.map(s -> s.length())
                .reduce((a , b) -> a + b);

        System.out.println(total);


        JavaRDD<String> words =
                lines.flatMap(line -> Arrays.asList(line.split(" ")).stream().map( s -> s.trim()).filter(s -> s.length() > 4).collect(Collectors.toList()).iterator());

        JavaPairRDD<String, Integer> wordCountPair = words.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> wordCount = wordCountPair.reduceByKey((a, b) -> a + b);

        List<Tuple2<String, Integer>> top = wordCount.top(20, new Tuple2Comparator());
        top.forEach(t -> System.out.println(t._1 + " : " + t._2));


        sc.close();

    }

    public static class Tuple2Comparator implements Comparator<Tuple2<String,Integer>>,Serializable{
        //private Comparator<Tuple2<String,Integer>> proxy = Comparator.comparingInt( (Tuple2<String,Integer> t) -> t._2()).reversed();

        @Override
        public int compare(Tuple2<String, Integer> o1, Tuple2<String, Integer> o2) {
            return Integer.compare(o1._2,o2._2);
        }
    }
}
