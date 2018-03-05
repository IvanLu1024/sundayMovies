package com.shixun.spark

import com.ivan.spark.utils.LoggerLevels
import org.apache.spark.mllib.evaluation.{RankingMetrics, RegressionMetrics}
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkConf, SparkContext}
import org.jblas.DoubleMatrix

object ALSRecommendation {


  def main(args: Array[String]): Unit = {

    /*设置提示级别*/
    LoggerLevels.setStreamingLogLevels()


    val con = new SparkConf().setAppName("ALSRecommendation").setMaster("local[2]")
    val sc = new SparkContext(con)
    //从本地读取评级数据--初始数据
    val rawData = sc.textFile("E:\\实训\\数据集\\ml-100k\\u.data")
    //格式化数据集--将数据切分
    val rawRatings = rawData.map(_.split("\t").take(3))
    //将评分矩阵RDD转化为Rating格式的RDD
    val ratings = rawRatings.map{case Array(user,movie,rating)=>Rating(user.toInt,movie.toInt,rating.toDouble)}
    /*训练推荐模型--ALS*/
    val model = ALS.train(ratings,50,15,0.01)
    model.save(sc,"/model")

    //使用ALS推荐模型
    val predictedRating = model.predict(789,123)
    println("预测评分："+predictedRating)
    val userId=789
    val K=10
    val topKRecs = model.recommendProducts(userId,K)
    println(topKRecs.mkString("\n"))


    /*初步检验推荐效果
    *
    * */
    //导入电影数据集
    val movies = sc.textFile("E:\\实训\\数据集\\ml-100k\\u.item")
    val titles = movies.map(line=>line.split("\\|").take(2)).map(array=>(array(0).toInt,array(1))).collectAsMap()
    println("电影信息："+titles(123))
    //建立用户名-其他RDD，并仅获取用户789的记录
    val moviesForUser = ratings.keyBy(_.user).lookup(789)
    //获取用户评分最高的10部电影，并打印电影名和评分值
    moviesForUser.sortBy(_.rating).take(10).map(rating=>(titles(rating.product),rating.rating)).foreach(println)
    /*
    * 物品推荐--利用jblas自定义创建一个余弦相似度计量函数
    * */
    def cosineSimilarity(vec1:DoubleMatrix,vec2:DoubleMatrix):Double={
      vec1.dot(vec2)/(vec1.norm2()*vec2.norm2())

    }
    //获取物品的因子特征向量，并将它转换为jblas的矩阵格式
    val itemId=567
    val itemFactor = model.productFeatures.lookup(itemId).head
    val itemVector = new DoubleMatrix(itemFactor)
    //计算电影567与其他电影的相似度
    val sims=model.productFeatures.map{case (id,factor)=>
        val factorVector=new DoubleMatrix(factor)
        val sim=cosineSimilarity(factorVector,itemVector)
        (id,sim)
    }
    //获取与电影567最相似的十部电影
    val sortedSim= sims.top(K)(Ordering.by[(Int,Double),Double]{
      case (id,similarity)=>similarity
    })
    println("与电影567最相似的十部电影"+sortedSim.mkString("\n"))
    //打印电影567的电影名
    println(titles(567))
    val sortedSim2 =sims.top(K+1)(Ordering.by[(Int,Double),Double]{
      case (id,similarity)=>similarity
    })
    //打印出和电影567最相似的十部电影
    sortedSim2.slice(1,11).map{case (id,sim)=>(titles(id),sim)}.mkString("\n")
    /*
    * 推荐效果评估
    * */
    //首先计算MSE和RMSE：其中MSE就是均方误差，是基于评分矩阵的推荐系统的必用指标。
    val usersProducts = ratings.map{case Rating(user,product,rating)=>(user,product)}
    val predictions=model.predict(usersProducts).map{
      case Rating(user,product,rating)=>((user,product),rating)
    }
    val ratingAndPredictions= ratings.map{
      case Rating(user,product,rating)=>((user,product),rating)
    }.join(predictions)
    val predictAndTrue= ratingAndPredictions.map{
      case ((user,product),(actual,predicted))=>(actual,predicted)

    }
    val regressionMetrics= new RegressionMetrics(predictAndTrue)
    println("平均差错方差是："+regressionMetrics.meanSquaredError)
    println("均方根误差："+regressionMetrics.rootMeanSquaredError)

    //计算MARK
    val itemFactors = model.productFeatures.map{
      case (id,factor)=>factor
    }.collect()
    val itemMatrix = new DoubleMatrix(itemFactors)
    val imBroadcast = sc.broadcast(itemMatrix)
    //创建用户id-推荐列表RDD
    val allRecs=model.userFeatures.map{
      case (userId,array)=>
        val userVector = new DoubleMatrix(array)
        val scores = imBroadcast.value.mmul(userVector)
        val sortedWithId = scores.data.zipWithIndex.sortBy(-_._1)
        val recommendedIds = sortedWithId.map(_._2 + 1).toSeq
        (userId, recommendedIds)
    }
    //创建用户-电影评分ID集RDD
    val userMovies = ratings.map{case Rating(user,product,rating)=>(user,product)}.groupBy(_._1)
    val predictedAndTrueForRanking = allRecs.join(userMovies).map{ case (userId, (predicted, actualWithIds)) =>
      val actual = actualWithIds.map(_._2)
      (predicted.toArray, actual.toArray)
    }
    //创建RankingMetrics对象
    val rankingMetrics= new RankingMetrics(predictedAndTrueForRanking)
    println("Mean Average Precision = " + rankingMetrics.meanAveragePrecision)









  }

}
