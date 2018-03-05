package com.shixun.spark.day03

import com.ivan.spark.utils.LoggerLevels
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

object ModelLoader {
  def main(args: Array[String]): Unit = {
    /*设置提示级别*/
    LoggerLevels.setStreamingLogLevels()

    //基础配置信息
    val con = new SparkConf().setAppName("ALSRecommendation").setMaster("local[4]")
    val sc = new SparkContext(con)
    val sqlContext = new SQLContext(sc)

    //从本地加载模型
    val model = MatrixFactorizationModel.load(sc,"E:\\实训\\数据集\\模型\\out")
    val array = model.recommendProducts(111,5)

    println(array.mkString(","))





  }

}
