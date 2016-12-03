/**
  * Created by sheh on 02/12/2016.
  */
case class Order(client: String, ticket: String, `type`: String, price: Int, quantity: Int) {

  // matches two orders, returns a Trade as the result
  def matchOrder(otherOrder: Order):Trade = if (isMatch(otherOrder)) {  // addition check
    otherOrder match {
      case Order(c, tk, "s", p, q) => Trade(c, client, tk, price, q)  // client sale to otherClient
      case Order(c, tk, "b", p, q) => Trade(client, c, tk, price, q)  // client buy from otherClient
      case _ => throw new Exception(s"The orders are differ, $otherOrder")  // just in case
    }
  } else throw new Exception("The orders are differ")  // orders cannot be matched

  // Do orders match?
  def isMatch(other: Order):Boolean =
    other.client != client &&  // not to trade with itself
      other.ticket == ticket &&  // the tickets are equal
      other.`type` != `type` &&  // s<->b, not s<->s or b<->b
      other.quantity == quantity && // the quantities are equal
      other.price == price // the prices are equal
      // best price feature, not tested
      //(if (other.`type` == "b") other.price >= price else other.price <= price)

}

object OrderParser {
  implicit class OrderParserTabString(str: String) {
    def toOrder:Order = {
      val Array(c, t, tk, p, q) = str.trim.split("\t")
      Order(c, tk, t, p.toInt, q.toInt)
    }
  }
}