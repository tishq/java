import org.apache.spark.{SparkConf, SparkContext}

// 导入rating类
import org.apache.spark.mllib.recommendation.Rating

// 导入ALS推荐系统算法包
import org.apache.spark.mllib.recommendation.ALS

// 导入jblas库中的矩阵类
import org.jblas.DoubleMatrix

/**
  * @Author: 孟红全
  * @Date: 2019/7/10 下午2:19
  * @Version 1.0
  */


/*
  用户信息
  用户id | 用户年龄 | 用户性别 | 用户职业 | 用户邮政编码

  影片信息
  影片id | 影片名 | 影片发行日期 | 影片链接 | (后面几列先不去管)

  评分数据：
  用户id | 影片id | 评分值 | 时间戳(timestamp格式)

 */



object Movies {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Movies").setMaster("local")
    val sc = new SparkContext(conf)

    // 载入评级数据
    val rawData = sc.textFile("file:///home/hq/IdeaProjects/mr/spark/data/ml-100k/u.data")
    // 展示一条记录
    print(rawData.first())

    // 格式化数据集
    val rawRatings = rawData.map(_.split("\t").take(3))
    // 展示一条记录
    print(rawData.first())

    // 将评分矩阵RDD中每行记录转换为Rating类型
    val ratings = rawRatings.map { case Array(user, movie, rating) =>
      Rating(user.toInt, movie.toInt, rating.toDouble) }


    // 启动ALS矩阵分解
    val model = ALS.train(ratings, 50, 10, 0.01)


    val userId = 789
    val K = 10

    // 获取推荐列表
    val topKRecs = model.recommendProducts(userId, K)
    // 打印推荐列表
    println(topKRecs.mkString("\n"))

    // 导入电影数据集
    val movies = sc.textFile("file:///home/hq/IdeaProjects/mr/spark/data/ml-100k/u.item")
    // 建立电影id - 电影名字典
    val titles = movies.map(line => line.split("\\|").
      take(2)).map(array => (array(0).toInt, array(1))).collectAsMap()
    // 查看id为123的电影名
    titles(123)

    // 建立用户名-其他RDD，并仅获取用户789的记录
    val moviesForUser = ratings.keyBy(_.user).lookup(789)
    // 获取用户评分最高的10部电影，并打印电影名和评分值
    moviesForUser.sortBy(-_.rating).take(10).map(rating =>
      (titles(rating.product), rating.rating)).foreach(println)


    // 定义相似度函数
    def cosineSimilarity(vec1: DoubleMatrix, vec2: DoubleMatrix): Double = {
      vec1.dot(vec2) / (vec1.norm2() * vec2.norm2())
    }


    // 选定id为567的电影
    val itemId = 567
    // 获取该物品的隐因子向量
    val itemFactor = model.productFeatures.lookup(itemId).head
    // 将该向量转换为jblas矩阵类型
    val itemVector = new DoubleMatrix(itemFactor)


    // 计算电影567与其他电影的相似度
    val sims = model.productFeatures.map { case (id, factor) =>
    val factorVector = new DoubleMatrix(factor)
    val sim = cosineSimilarity(factorVector, itemVector)
      (id, sim)
    }
    // 获取与电影567最相似的10部电影
    val sortedSims = sims.top(K)(Ordering.by[(Int, Double), Double] { case (id, similarity) => similarity })
    // 打印结果
    println(sortedSims.mkString("\n"))


    // 打印电影567的影片名
    println(titles(567))
    // 获取和电影567最相似的11部电影(含567自己)
    val sortedSims2 = sims.top(K + 1)(Ordering.by[(Int, Double),
      Double] { case (id, similarity) => similarity })
    // 再打印和电影567最相似的10部电影
    sortedSims2.slice(1, 11).map { case (id, sim) => (titles(id), sim) }.mkString("\n")


    // 创建用户id-影片id RDD
    val usersProducts = ratings.map { case Rating(user, product, rating) => (user, product) }
    // 创建(用户id,影片id) - 预测评分RDD
    val predictions = model.predict(usersProducts).map {
      case Rating(user, product, rating) => ((user, product), rating)
    }
    // 创建用户-影片实际评分RDD，并将其与上面创建的预测评分RDD join起来
    val ratingsAndPredictions = ratings.map {
      case Rating(user, product, rating) => ((user, product), rating)
    }.join(predictions)

    // 导入RegressionMetrics类
    import org.apache.spark.mllib.evaluation.RegressionMetrics
    // 创建预测评分-实际评分RDD
    val predictedAndTrue = ratingsAndPredictions.map { case ((user, product), (actual, predicted)) =>
      (actual, predicted) }
    // 创建RegressionMetrics对象
    val regressionMetrics = new RegressionMetrics(predictedAndTrue)

    // 打印MSE和RMSE
    println("Mean Squared Error = " + regressionMetrics.meanSquaredError)
    println("Root Mean Squared Error = " + regressionMetrics.rootMeanSquaredError)

    // 创建电影隐因子RDD，并将它广播出去
    val itemFactors = model.productFeatures.map { case (id, factor) => factor }.collect()
    val itemMatrix = new DoubleMatrix(itemFactors)
    val imBroadcast = sc.broadcast(itemMatrix)

    // 创建用户id - 推荐列表RDD
    val allRecs = model.userFeatures.map { case (userId, array) =>
    val userVector = new DoubleMatrix(array)
    val scores = imBroadcast.value.mmul(userVector)
    val sortedWithId = scores.data.zipWithIndex.sortBy(-_._1)
    val recommendedIds = sortedWithId.map(_._2 + 1).toSeq
      (userId, recommendedIds)
    }

    // 创建用户 - 电影评分ID集RDD
    val userMovies = ratings.map { case Rating(user, product, rating) => (user, product) }.groupBy(_._1)

    // 导入RankingMetrics类
    import org.apache.spark.mllib.evaluation.RankingMetrics
    // 创建实际评分ID集-预测评分ID集 RDD
    val predictedAndTrueForRanking = allRecs.join(userMovies).
      map { case (userId, (predicted, actualWithIds)) =>
      val actual = actualWithIds.map(_._2)
      (predicted.toArray, actual.toArray)
    }
    // 创建RankingMetrics对象
    val rankingMetrics = new RankingMetrics(predictedAndTrueForRanking)
    // 打印MAPK
    println("Mean Average Precision = " + rankingMetrics.meanAveragePrecision)
  }

}
