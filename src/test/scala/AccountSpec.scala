import org.scalatest._

/**
  * Created by sheh on 03/12/2016.
  */
class AccountSpec extends FlatSpec with Matchers {

  "An Account" should "not apply a trade if client name is not equal" in {
    Account("C1")
      .applyTrade(Trade("C2", "C3", "A", 1, 1)) shouldBe Account("C1")
  }

  it should "apply a sale trade" in {
    Account("C1", 0, Map("A" -> 1))
      .applyTrade(Trade("C1", "C2", "A", 1, 1)) shouldBe Account("C1", 1, Map("A" -> 0))
  }

  it should "apply a buy trade" in {
    Account("C1", 10, Map())
      .applyTrade(Trade("C2", "C1", "A", 1, 10)) shouldBe Account("C1", 0, Map("A" -> 10))
  }

  it should "ignore negative cache value" in {
    Account("C1", 0, Map())
      .applyTrade(Trade("C2", "C1", "A", 1, 10)) shouldBe Account("C1", -10, Map("A" -> 10))
  }

  it should "ignore negative stock quantity value" in {
    Account("C1", 0, Map())
      .applyTrade(Trade("C1", "C2", "A", 1, 10)) shouldBe Account("C1", 10, Map("A" -> -10))
  }

  it should "be able to process partial trade" in {
    Account("C1", 0, Map("A" -> 10))
      .applyTrade(Trade("C1", "C2", "A", 10, 3)) shouldBe Account("C1", 30, Map("A" -> 7))
  }

  it should "apply sequence trades" in {
    Account("C1", 30, Map())
      .applyTrade(Trade("C2", "C1", "A", 10, 3))
      .applyTrade(Trade("C1", "C2", "A", 30, 1)) shouldBe Account("C1", 30, Map("A" -> 2))
  }

}
