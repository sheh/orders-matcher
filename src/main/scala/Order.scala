/**
  * Created by sheh on 02/12/2016.
  */
case class Order(client: String, ticket: String, `type`: String, price: Int, quantity: Int) {

  def matchOrder(otherOrder: Order):Trade = if (isMatch(otherOrder)) {
    otherOrder match {
      case Order(c, tk, "s", p, q) => Trade(c, client, tk, price, q)
      case Order(c, tk, "b", p, q) => Trade(client, c, tk, price, q)
      case _ => throw new Exception(s"The orders are differ, $otherOrder")
    }
  } else throw new Exception("The orders are differ")

  def isMatch(other: Order):Boolean =
    other.client != client &&
      other.ticket == ticket &&
      other.`type` != `type` &&
      other.quantity == quantity &&
      other.price == price
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