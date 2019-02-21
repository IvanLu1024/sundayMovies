package DataCleaning

/**
  * 用于将电影的数据集进行清洗，由于数据有不规整的部分，需要将数据变成规整的数据集
  */

import Logger.LoggerLevels
import org.apache.spark.{SparkConf, SparkContext}

object splitMovie {
  def splitM(path:String):  Array[(Int,String, Int,Array[String])] ={
    LoggerLevels.setStreamingLogLevels()
    //创建SparkConf()并设置
    val conf = new SparkConf().setAppName("splitMovie").setMaster("local[4]")
    //创建sparkContext, 该对象是提交spark App的入门
    val sc = new SparkContext(conf)
    //使用sc创建RDD并执行相应的transformation和action
    val rawData = sc.textFile(path)


    val rawMovie = rawData.map(_.split(","))
    println(rawData.count())

    //形成一个tag数组
    //val tags = rawMovie.map{case Array(movieId, movie, movieType) => Tag(movieId.toInt, movie.toString, ))}
      val movie = rawMovie.map(line => {
        if(line.length == 3){
          //获取电影类型
          var fields = line(2).split("\\|")

          try{
            var middle = line(1).split("\\(")
            var date = middle(middle.length-1).substring(0,4)
            var movie = line(1).substring(0, line(1).length-7)
            (line(0).toInt, movie, date.toInt, fields)
          }catch {
            case e:Exception =>{
              (line(0).toInt, line(1), 0, fields)
            }
          }

        }else{
          println(line(0).toInt)
          var fields = line(line.length-1).split("\\|")
          try{
            var middle = line(line.length-2).split("\\(")
            var date = middle(middle.length-1).substring(0,4).toInt
            var movie = ""
            for(j<-1 to line.length-2 ){
              movie += line(j)
            }
            (line(0).toInt, movie, date, fields)
          }catch {
            case e:Exception=>{
              var middle = line(line.length-2).split("\\(")
              //var date = middle(middle.length-1).substring(0,4).toInt
              var movie = ""
              for(j<-1 to line.length-2 ){
                movie += line(j)
              }
              (line(0).toInt, movie, 0, fields)
            }
          }
        }
      }).collect()
    movie
  }


}
