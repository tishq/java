import org.apache.spark.SparkContext._
import org.apache.spark.{SparkConf,SparkContext}
object Wordcount
{
  def main(args:Array[String]): Unit =
  {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("file:///home/hq/IdeaProjects/mr/spark/data/hello.txt")
    val wordcount = rdd.flatMap(_.split(" ")).map(x=>(x,1)).reduceByKey(_+_)
    val wordsort = wordcount.map(x=>(x._2,x._1)).sortByKey(false).map(x=>(x._2,x._1))
    wordsort.saveAsTextFile("file:///home/hq/Desktop/hello")
    sc.stop()
  }
}