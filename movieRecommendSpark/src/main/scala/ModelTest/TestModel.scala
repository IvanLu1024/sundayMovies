package ModelTest

/**
  * 在秩Rank,迭代次数IterNum,正则系数Lambda不同情况下，测试出RMSE最小的情况。
  */

import Logger.LoggerLevels
import org.apache.spark.mllib.evaluation.RegressionMetrics
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TestModel {
  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    val sparkConf = new SparkConf().setAppName("TestModel").setMaster("local[4]").set("spark.local.dir","D:\\spark")
    val sc = new SparkContext(sparkConf)
    sc.setCheckpointDir(args(3))

    val numPartitions = 4
    //读取训练集
    val train = sc.textFile(args(0)).map(line => {
      val fields = line.split(",")
      (fields(3).substring(0, 1).toInt, Rating(fields(0).substring(1).toInt, fields(1).toInt, fields(2).toDouble))
    }).values.cache()
    //读取校验集
    val validation = sc.textFile(args(1)).map(line => {
      val fields = line.split(",")
      (fields(3).substring(0, 1).toInt, Rating(fields(0).substring(1).toInt, fields(1).toInt, fields(2).toDouble))
    }).values.cache()

    //读取测试集
    val test = sc.textFile(args(2)).map(line => {
      val fields = line.split(",")
      (fields(3).substring(0, 1).toInt, Rating(fields(0).substring(1).toInt, fields(1).toInt, fields(2).toDouble))
    }).values.persist()
    train.checkpoint()
    validation.checkpoint()
    test.checkpoint()

    //val numTest = test.count()
//
    val ranks = List(60,80,100)
    val numlters = List(30,50,70)
    val lambdas = List(0.2,0.15,0.1,0.05,0.01)

    var bestModel: Option[MatrixFactorizationModel] = None
    var bestValidationRmse = Double.MaxValue
    var bestRank = 0
    var bestLambda = -1.0
    var bestNumIter = -1

    //  val collection = scala.collection.mutable.ArrayBuffer[(Double, Int, Int, Double)]()
//    val rank = args(3).toInt
//    val numIter = args(4).toInt
//    val lambda = args(5).toDouble
  for(rank<-ranks;lambda<-lambdas;numIter<-numlters){
    val model = ALS.train(train, rank, numIter, lambda)
    //训练模型
    //model.save("")

    val validationRmse = computeRmse(model, validation)

    println(validationRmse + " " + rank + " " + numIter + " " + lambda)

          if(validationRmse<bestValidationRmse)
          {
            bestModel=Some(model)
            bestValidationRmse=validationRmse
            bestRank=rank
            bestLambda=lambda
            bestNumIter=numIter
          }
        }

    val testRmse = computeRmse(bestModel.get, test)
    println("The best model was trained with rank = " + bestRank + " and lambda = " + bestLambda
     + ", and numIter = " + bestNumIter + ", and its RMSE on the test set is " + testRmse + ".")
    ALS.train(train, bestRank, bestNumIter, bestLambda).save(sc, args(3))
  }


  //RMSE计算
  def computeRmse(model: MatrixFactorizationModel, value: RDD[Rating]): Double ={
    //创建用户id-影片id RDD
    val usersProducts = value.map{case Rating(user, product,rating) => (user, product)}

    //创建（用户id,影片id） - 预测评分RDD
    val predictions = model.predict(usersProducts).map{
      case Rating(user, product, rating) => ((user, product), rating)
    }

    //创建用户-影片实际评分RDD,并将其与上面创建的预测评分RDD join起来
    val ratingsAndPredictions = value.map{
      case Rating(user, product, rating) => ((user, product),rating)
    }.join(predictions)

    //创建预测评分-实际评分RDD
    val predictedAndTrue = ratingsAndPredictions.map{case((user, product),(actual,
    predicted)) => (actual, predicted)}

    val regressionMetrics = new RegressionMetrics(predictedAndTrue)
    regressionMetrics.rootMeanSquaredError
  }

}
