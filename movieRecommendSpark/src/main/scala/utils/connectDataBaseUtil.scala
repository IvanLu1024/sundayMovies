package utils

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SaveMode}

object connectDataBaseUtil {

  def connect(dataFrame:DataFrame, tableName:String):Unit={
    //创建properties存储数据库相关属性
    val prop = new Properties()
    prop.put("user","root")
    prop.put("password","123456")
    //将数据追加到数据库
    dataFrame.write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/big_data","big_data."+tableName,prop)

  }

}
