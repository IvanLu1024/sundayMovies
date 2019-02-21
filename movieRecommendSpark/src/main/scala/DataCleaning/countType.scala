package DataCleaning

/**
  * 统计电影的种类
  */

import scala.collection.mutable

object countType {
  val s = scala.collection.mutable.Set("")
  def count(arr:Array[String]): mutable.Set[String] ={
    for(i <- arr){
      s += i
    }
    s
  }
  def main(args: Array[String]): Unit = {
    val arr = splitMovie.splitM(args(0))
    for(i <- arr){
      count(i._4)
    }
    for(i <- s){
      println(i)
    }
  }
}
