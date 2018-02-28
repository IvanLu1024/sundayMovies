package com.shixun.spark.day02

import com.ivan.spark.utils.LoggerLevels
import com.shixun.spark.utils.connectDataBaseUtil
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.evaluation.{RankingMetrics, RegressionMetrics}
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.types._
import org.jblas.DoubleMatrix

object RecommendationJDBCDemo {

  def main(args: Array[String]): Unit = {

    /*设置提示级别*/
    LoggerLevels.setStreamingLogLevels()

    //基础配置信息
    val con = new SparkConf().setAppName("ALSRecommendation").setMaster("local[4]")
    val sc = new SparkContext(con)
    val sqlContext = new SQLContext(sc)
    //从本地读取评级数据--初始数据
    /*u.data:评分数据
	  其中各列数据分别为：
    用户id | 影片id | 评分值 | 时间戳(timestamp格式)
    * */
    val rawData = sc.textFile("E:\\实训\\数据集\\ml-100k\\u.data")
    //格式化数据集--将数据切分
    val rawRatings = rawData.map(_.split("\t").take(3))
    //将评分矩阵RDD转化为Rating格式的RDD
    val ratings = rawRatings.map { case Array(user, movie, rating)
          => Rating(user.toInt, movie.toInt, rating.toDouble) } // (用户id,电影id,评分值)

    /*训练推荐模型--ALS*/
    val model = ALS.train(ratings, 50, 10, 0.01)
    //使用ALS推荐模型
    val predictedRating = model.predict(789, 123)
    println("用户789对于电影123的预测评分为：" + predictedRating)
    val userId = 789
    val K = 10
    val topKRecs = model.recommendProducts(userId, K).toList

    val recsTup = topKRecs.map(x=>(x.user.toInt,x.product.toInt,x.rating.toDouble))

    //将RDD直接转换为DataFrame
    import sqlContext.implicits._
    val resDF = recsTup.toDF

    println("向用户789推荐的电影的前十名的为："+recsTup.mkString)

    connectDataBaseUtil.connect(resDF,"recs_Table")




    /*初步检验推荐效果
    *
    * */
    //导入电影数据集
    /*u.item:影片信息
	  其中前几列数据分别为：
    影片id | 影片名 | 影片发行日期 | 影片链接 | (后面几列先不去管)
    * */
    val movies = sc.textFile("E:\\实训\\数据集\\ml-100k\\u.item")
    val titles = movies.map(line =>
      line.split("\\|").take(2)).map(array =>
      (array(0).toInt, array(1))).collectAsMap()  //(影片id->影片名) 以键值对的形式存储
    println("电影id为123的电影名为：" + titles(123))
    //建立用户名-其他RDD，并仅获取用户789的记录
    val moviesForUser = ratings.keyBy(_.user).lookup(789)
    //获取用户评分最高的10部电影，并打印电影名和评分值；该次查找的是用户id为789的结果
    moviesForUser.sortBy(_.rating).take(10).map(rating =>
      (titles(rating.product), rating.rating)).foreach(println) //(电影名，预测评分)

    /*
    * 物品推荐--利用jblas自定义创建一个余弦相似度计量函数
    * */
    def cosineSimilarity(vec1: DoubleMatrix, vec2: DoubleMatrix): Double = {
      vec1.dot(vec2) / (vec1.norm2() * vec2.norm2())

    }

    //获取物品的因子特征向量，并将它转换为jblas的矩阵格式
    val itemId = 567
    val itemFactor = model.productFeatures.lookup(itemId).head
    val itemVector = new DoubleMatrix(itemFactor)
    //计算电影567与其他电影的相似度
    val sims = model.productFeatures.map { case (id, factor) =>
      val factorVector = new DoubleMatrix(factor)
      val sim = cosineSimilarity(factorVector, itemVector)
      (id, sim)
    }
    //获取与电影567最相似的十部电影
    val sortedSim = sims.top(K)(Ordering.by[(Int, Double), Double] {
      case (id, similarity) => similarity
    })
    println("与电影567最相似的十部电影" + sortedSim.mkString("\n"))
    //将RDD直接转换为DataFrame

    val simRow = sims.map(a=>Row(a._1,a._2))
    //*通过StructType直接指定每个字段的schema
    val simSchema=StructType(
      List(

        StructField("userId",IntegerType,true),
        StructField("similarity",DoubleType,true)
      )

    )
    val simDF = sqlContext.createDataFrame(simRow,simSchema)


    //关联到数据库
    connectDataBaseUtil.connect(simDF,"sim_Table")




    //打印电影567的电影名
    println(titles(567))
    val sortedSim2 = sims.top(K + 1)(Ordering.by[(Int, Double), Double] {
      case (id, similarity) => similarity
    })
    //打印出和电影567最相似的十部电影
    sortedSim2.slice(1, 11).map { case (id, sim) => (titles(id), sim) }.mkString("\n")




  }
}
