package com.shixun.spark

import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SQLContext, SaveMode}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object MySQLDemo01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("JdbcRDD").setMaster("local")
    val sc = new SparkContext(conf)
    val sqLContext = new SQLContext(sc)
    //从本地读取数据集，如u.item
    val itemRDD = sc.textFile("E:\\实训\\数据集\\ml-100k\\u.item").map(_.split("\\|"))



    //*通过StructType直接指定每个字段的schema
    val itemSchema=StructType(
      List(
        StructField("id",IntegerType,true),
        StructField("name",StringType,true),
        StructField("time",StringType,true)


      )

    )
    //*将RDD映射到rowRDD上面
    val itemRow = itemRDD.map(it=>Row(it(0).toInt,it(1).trim,it(2)))
    //*将schema信息应用到rowRDD上面,
    val itemDF =sqLContext.createDataFrame(itemRow,itemSchema)

    //*注册临时表
    itemDF.registerTempTable("t_item")
    val df = sqLContext.sql("select * from t_item")
    df.write.mode(SaveMode.Append).json("D:\\out02")


    //创建properties存储数据库相关属性
    val prop = new Properties()
    prop.put("user","root")
    prop.put("password","123456")
    //化悲愤数据追加到数据库
    itemDF.write.mode(SaveMode.Overwrite).jdbc("jdbc:mysql://localhost:3306/big_data","big_data.item_film",prop)


    sc.stop()

  }

}
