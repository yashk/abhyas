name := "scalaschool"

version := "0.1"

scalaVersion := "2.11.8"


// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" % "spark-sql" % "2.3.1"
//org.apache.hadoop:hadoop-aws:2.7.3,com.amazonaws:aws-java-sdk:1.7.4
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.3"
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.7.4"


// spark steaming
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.3.1"

