import java.io.PrintWriter

import scala.io.Source

/**
  * Created by sheh on 02/12/2016.
  */
object Main {

  def main(args: Array[String]): Unit = {
    import AccountParser._
    import OrderParser._
    val accounts = Source.fromResource("clients.txt").getLines().map(_.toAccount).toList
    val orders = Source.fromResource("orders.txt").getLines().map(_.toOrder)
    val writer = new PrintWriter("result.txt")
    new Matcher(accounts, orders).trade() foreach { a =>
      println(a)
      writer.write(a.toString + "\n")
    }
    writer.close()
  }

}
