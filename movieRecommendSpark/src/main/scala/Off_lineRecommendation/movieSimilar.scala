package Off_lineRecommendation

/**
  * 用离线推荐model给每部电影推荐10部最相似的电影，并放到mysql数据库中
  */

import java.util.Properties
import Logger.LoggerLevels
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.jblas.DoubleMatrix

object Analysis {
  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()

    //创建SparkConf()并设置
    val conf = new SparkConf().setAppName("Analysis").setMaster("local[4]")
    //创建sparkContext, 该对象是提交spark App的入门
    val sc = new SparkContext(conf)
    //使用sc创建RDD并执行相应的transformation和action

    //从指定的地址创建RDD
    //创建SQLContext
    val sqlContext = new SQLContext(sc)

    val model = MatrixFactorizationModel.load(sc, "d:\\outmodel")



    //通过StructType直接指定每个字段的schema
    val schema = StructType(
      List(
        StructField("movie1", IntegerType, true),
        StructField("movie2", IntegerType, true),
        StructField("Similarity", DoubleType, true)
      )
    )

    //导入电影数据集
    val movies = sc.textFile(args(0))

    //定义相似度函数
    def consineSimilarity(vec1: DoubleMatrix, vec2: DoubleMatrix) : Double = {
      vec1.dot(vec2) / (vec1.norm2() * vec2.norm2())
    }
    val movieNum = movies.map(line =>{
      val fields = line.split("\\|")
      fields(0).toInt
    }).distinct().toLocalIterator

    //对每一部电影进行相似度分析
    while (movieNum.hasNext) {

      val i = movieNum.next()
      //获取该物品的隐因子向量
      val itemFactor = model.productFeatures.lookup(i).head
      //将该向量转换为jblas矩阵类型
      val itemVector = new DoubleMatrix(itemFactor)

      //计算一个电影与其他物品的相似度
      val sims = model.productFeatures.map { case (id, factor) =>
        val factorVector = new DoubleMatrix(factor)
        val sim = consineSimilarity(factorVector, itemVector)
        (id, sim)
      }
      //获取与电影567最相似的10部电影
      val sortedSims = sims.top(11)(Ordering.by[(Int, Double), Double] { case ( id, similarity) => similarity })

      val simRDD = sortedSims.map(x=>Sim(i, x._1, x._2))
        //将RDD映射到rowRDD
      import sqlContext.implicits._
      val simDF = simRDD.toList.toDF

        //创建Properties存储数据库相关属性
        val prop = new Properties()
        prop.put("user", "root")
        prop.put("password", "123456")
        //将数据追加到数据库中
      simDF.write.mode("append").jdbc("jdbc:mysql://localhost:3306/spark","spark.t_Similar", prop)
        //停止SparkContext

      }
    sc.stop()
  }
}
case class Sim(movie1:Int, movie2: Int, rate:Double)

