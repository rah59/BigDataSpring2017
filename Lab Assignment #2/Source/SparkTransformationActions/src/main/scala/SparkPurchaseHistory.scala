import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Raj on 29-Jan-17.
  * This program is created to add purchases to provide total spend by customer
  */

object SparkPurchaseHistory {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("SparkActions").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val customers = sc.parallelize(Array((1, "raj"),(2, "arun"),(3, "george"),(4, "daniel"),(5, "vidya")))

    val purchases = (sc.parallelize(Array((1, 10.25), (2, 5.15), (3, 2.35), (1, 3.55), (2, 1.25),
      (4, 3.85), (4, 2.50), (5,2.55), (5,1.25))))

    if (purchases.count() > 0) {

      val combined = customers.join(purchases)

      val result = combined.reduceByKey((p1, p2) => (p1._1, p1._2 + p2._2))

      result.sortByKey()

      result.saveAsTextFile("PurchasesByCustomer")
    }
    else{
      println("There are no purchase records for any customer")
    }

  }

}
