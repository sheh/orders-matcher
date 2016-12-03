import org.scalatest._

/**
  * Created by sheh on 02/12/2016.
  */
class OrderBookSpec extends FlatSpec with Matchers {

  "A OrderBook" should "create trades" in {
    OrderBook()
      .applyOrder(Order("C1", "A", "s", 1, 1))
      .applyOrder(Order("C1", "B", "s", 1, 1))
      .applyOrder(Order("C1", "C", "b", 1, 1))
      .applyOrder(Order("C2", "A", "b", 1, 1))
      .applyOrder(Order("C3", "B", "b", 1, 1))
      .applyOrder(Order("C4", "C", "s", 1, 1))
      .trades shouldBe List(
        Trade("C1", "C2", "A", 1, 1),
        Trade("C1", "C3", "B", 1, 1),
        Trade("C4", "C1", "C", 1, 1)
    )
  }

  it should "remove an order if trade was created" in {
    OrderBook()
      .applyOrder(Order("C1", "A", "s", 1, 1))
      .applyOrder(Order("C2", "A", "b", 1, 1))
      .applyOrder(Order("C2", "A", "b", 1, 1))
      .trades shouldBe List(
        Trade("C1", "C2", "A", 1, 1)
    )
  }

  it should "choose the best order if the order came first" ignore {
    OrderBook()
      .applyOrder(Order("C1", "A", "s", 1, 1))
      .applyOrder(Order("C2", "A", "s", 2, 1))
      .applyOrder(Order("C3", "A", "b", 3, 1))
      .trades shouldBe List(
      Trade("C1", "C3", "A", 1, 1)
    )
  }

  it should "choose the best order if the order came last" ignore {
    OrderBook()
      .applyOrder(Order("C1", "A", "s", 1, 1))
      .applyOrder(Order("C2", "A", "s", 2, 1))
      .applyOrder(Order("C3", "A", "b", 3, 1))
      .trades shouldBe List(
      Trade("C1", "C3", "A", 1, 1)
    )
  }

  it should "choose the oldest order if prices are equal" in {
    OrderBook()
      .applyOrder(Order("C1", "A", "s", 1, 1))
      .applyOrder(Order("C2", "A", "s", 1, 1))
      .applyOrder(Order("C3", "A", "b", 1, 1))
      .trades shouldBe List(
      Trade("C1", "C3", "A", 1, 1)
    )
  }

  it should "add an order if it was not matched" in {
    OrderBook()
      .applyOrder(Order("C1", "A", "s", 1, 1))
      .applyOrder(Order("C2", "A", "s", 1, 1))
      .applyOrder(Order("C3", "A", "b", 1, 2))
      .trades shouldBe Nil
  }

}
