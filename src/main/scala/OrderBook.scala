import scala.collection.mutable._

/**
  * Created by sheh on 02/12/2016.
  */

class OrderBook(orders: List[Order], val trades:List[Trade]) {

  def applyOrder(order: Order): OrderBook = {
    orders.indexWhere(_.isMatch(order)) match {  // find index of the first matched order
      case -1 =>  // there is no matched order, add the order to the orderbook with sorting by price asc
        val ordersWithNewOne = orders.indexWhere(order.price < _.price) match {
          case -1 => orders :+ order  // the biggest price
          case idx => orders.slice(0, idx-1) ++ (order :: orders.slice(idx-1, orders.size)) // insert in order
        }
        new OrderBook(ordersWithNewOne, trades)
      case idx => // there is matched order
        val trade = orders(idx).matchOrder(order)  // get trade
        val ordersAfterTrade = orders.slice(0, idx) ++ orders.slice(idx + 1, orders.size)  // remove order
        new OrderBook(ordersAfterTrade, trades :+ trade) // add the trade to trades
    }
  }

}

object OrderBook {
  def apply(): OrderBook = new OrderBook(Nil, Nil)
}