/**
  * Created by sheh on 02/12/2016.
  */
case class Account(client: String, cache: Int, stocks: Map[String, Int]) {

  def applyTrade(trade: Trade):Account = if (trade.clientFrom == client) { // sale
    val newStocks = stocks.updated(trade.ticket, stocks.getOrElse(trade.ticket, 0) - trade.quantity)
    copy(cache = cache + trade.price * trade.quantity, stocks = newStocks)
  } else if (trade.clientTo == client) { // buy
    val newStocks = stocks.updated(trade.ticket, stocks.getOrElse(trade.ticket, 0) + trade.quantity)
    val newCache = cache - trade.price * trade.quantity
    copy(cache = newCache, stocks = newStocks)
  } else {
    this
  }

  override def toString = s"$client\t$cache" +
    s"\t${stocks.getOrElse("A", 0)}" +
    s"\t${stocks.getOrElse("B", 0)}" +
    s"\t${stocks.getOrElse("C", 0)}" +
    s"\t${stocks.getOrElse("D", 0)}"

}

object Account {
  def apply(client: String): Account = new Account(client, 0, Map())
}

object AccountParser {
  implicit class AccountParserTabString(str: String) {
    def toAccount:Account = {
      val Array(name, cache, a, b, c, d) = str.trim.split("\t")
      Account(name, cache.toInt, Map("A" -> a.toInt, "B" -> b.toInt, "C" -> c.toInt, "D" -> d.toInt))
    }
  }
}