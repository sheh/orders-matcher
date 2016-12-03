/**
  * Created by sheh on 02/12/2016.
  */
class Matcher(accounts: List[Account], orders: Iterator[Order]) {

  def trade():List[Account] = {
    orders.foldLeft(OrderBook())((oBook: OrderBook, order: Order) =>
      oBook.applyOrder(order)
    ).trades.foldLeft(accounts)((accs: List[Account], trade: Trade) =>
      accs map { _.applyTrade(trade)}
    )
  }

}
