/**
  * Created by sheh on 02/12/2016.
  */
class Matcher(accounts: List[Account], orders: Iterator[Order]) {

  // Process orders and then process accounts
  def trade():List[Account] = {
    orders.foldLeft(OrderBook())((oBook: OrderBook, order: Order) => // process orders
      oBook.applyOrder(order)
    ).trades.foldLeft(accounts)((accs: List[Account], trade: Trade) => // process accounts
      accs map { _.applyTrade(trade)}
    )
  }

}
