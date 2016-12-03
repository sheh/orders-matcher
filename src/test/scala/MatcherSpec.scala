import org.scalatest._

/**
  * Created by sheh on 03/12/2016.
  */
class MatcherSpec extends FlatSpec with Matchers {

  "A Matcher" should "trade" in {
    val accounts = List(
      Account("C1", 100, Map("A" -> 100)),
      Account("C2", 100, Map("B" -> 100)),
      Account("C3", 100, Map("C" -> 100)),
      Account("C4", 150, Map("D" -> 100))
    )
    val orders = Iterator(
      Order("C1", "A", "s", 1, 50),  // C1 sale 50 A by 1$
      Order("C2", "B", "s", 2, 50),  // C2 sale 50 B by 2$
      Order("C3", "C", "s", 3, 50),  // C3 sale 50 C by 3$
      Order("C4", "A", "b", 1, 50),  // C4 buy  50 A by 1$, trade with C1 on 1$
      Order("C4", "B", "b", 3, 50),  // C4 buy  50 B by 3$, trade with C2 on 2$
      Order("C4", "C", "b", 1, 50),  // C4 buy  50 C by 1$, no trade because C3 wants $3
      Order("C4", "D", "s", 1, 100)  // C4 sale 100 D by 1$, no trade because no buy order
    )
    new Matcher(accounts, orders).trade() shouldBe List(
      Account("C1", 150, Map("A" -> 50)),
      Account("C2", 200, Map("B" -> 50)),
      Account("C3", 100, Map("C" -> 100)),
      Account("C4", 0, Map("A" -> 50, "B" -> 50, "D" -> 100))
    )
  }

}
