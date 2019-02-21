package Off_lineRecommendation

/**
  * 加载model,对每个用户进行推荐，将推荐电影放到数据库中
  */

import org.apache.spark.sql.SQLContext
import Logger.LoggerLevels
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.{SparkConf, SparkContext}
import utils.connectDataBaseUtil

object RecommendForAllUsers {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RecommendForAllUsers").setMaster("local[4]")
    //创建sparkContext, 该对象是提交spark App的入门
    val sc = new SparkContext(conf)
    val sqLContext = new SQLContext(sc)
    /*设置提示级别*/
    LoggerLevels.setStreamingLogLevels()

    //从本地载入训练模型
    val modelpath = args(0)
    val model = MatrixFactorizationModel.load(sc, modelpath)

    //从本地读取原始数据集-ratings.csv，获取所有的uerId

    val uIds = sc.textFile(args(1)).map(line => {
      val fields = line.split(",")
      fields(0).toInt
    }).distinct().toLocalIterator

    /*uIds.foreach(println)*/

    while(uIds.hasNext){
      val uid=uIds.next()
      val rec = model.recommendProducts(uid, 10)

      val result=rec.map(x => x.user.toString() + "|"
        + x.product.toString() + "|" + x.rating.toString())

      import sqLContext.implicits._
      val resultDFArray = sc.parallelize(result)
      val resultDF = resultDFArray.
        map(_.split("\\|")).map(x => Result(x(0).trim(), x(1).trim, x(2).trim())).toDF

      connectDataBaseUtil.connect(resultDF,"Result_all_users")

    }
    sc.stop()
  }
}
case class Result(userId:String,filmId:String,rate:String)
