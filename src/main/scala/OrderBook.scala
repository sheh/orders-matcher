import scala.collection.mutable._

/**
  * Created by sheh on 02/12/2016.
  */

class OrderBook(orders: List[Order], val trades:List[Trade]) {

  def applyOrder(order: Order): OrderBook = {
    orders.indexWhere(_.isMatch(order)) match {
      case -1 =>
        val ordersWithNewOne = orders.indexWhere(order.price < _.price) match {
          case -1 => orders :+ order
          case idx => orders.slice(0, idx-1) ++ (order :: orders.slice(idx-1, orders.size))
        }
        new OrderBook(ordersWithNewOne, trades)
      case idx =>
        val trade = orders(idx).matchOrder(order)
        val ordersAfterTrade = orders.slice(0, idx) ++ orders.slice(idx + 1, orders.size)
        new OrderBook(ordersAfterTrade, trades :+ trade)
    }
  }

}

object OrderBook {
  def apply(): OrderBook = new OrderBook(Nil, Nil)
}