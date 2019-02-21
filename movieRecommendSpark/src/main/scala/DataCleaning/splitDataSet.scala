package DataCleaning

/**
  * 将数据集切分为训练集，测试解，验证集
  */

import Logger.LoggerLevels
import org.apache.spark.{SparkConf, SparkContext}

object splitDataSet {

  def main(args: Array[String]): Unit = {

    LoggerLevels.setStreamingLogLevels()
    //创建SparkConf()并设置
    val conf = new SparkConf().setAppName("splitDataSet").setMaster("local[4]")
    //创建sparkContext,该对象是提交spark App的入门
    val sc = new SparkContext(conf)

    //使用sc创建RDD并执行相应的transformation和action
    val ratings = sc.textFile(args(0)).map {

       line =>{
         try{
           val fields = line.split(",")
           // format: (timestamp % 10, Rating(userId, movieId, rating))
           (fields(0).toInt,fields(1).toInt,fields(2).toDouble,fields(3).toLong%10)
         }catch {
           case e:Exception => (0, 0 ,0.toDouble ,0.toLong%10 )
         }
       }
    }

    val training=ratings.filter(x=>x._4<6&&x._1!=0 ).saveAsTextFile(args(1))
    val validation= ratings.filter(x=>x._4>=6&&x._4<8&&x._1!=0).saveAsTextFile(args(2))
    val test=ratings.filter(x=>x._4>=8&&x._1!=0).saveAsTextFile(args(3))

  }
}

