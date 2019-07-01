import org.apache.spark.SparkContext._
import org.apache.spark.{SparkConf,SparkContext}
object wc
{
  def main(args:Array[String]): Unit =
  {
    val conf = new SparkConf().setAppName("wc").setMaster("localhost")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("file:///usr/local/spark/README.md")
    val wordcount = rdd.flatMap(_.split(" ")).map(x=>(x,1)).reduceByKey(_+_)
    val wordsort = wordcount.map(x=>(x._2,x._1)).sortByKey(false).map(x=>(x._2,x._1))
    wordsort.saveAsTextFile("file:///home/hq/wc")
    sc.stop()
  }
}