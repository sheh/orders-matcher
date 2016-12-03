import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by sheh on 03/12/2016.
  */
class OrderSpec extends FlatSpec with Matchers {

  "A Order" should "not match if tickets are not equal" in {
    val o1 = Order("C1", "A", "s", 1, 1)
    val o2 = Order("C2", "B", "b", 1, 1)
    o1.isMatch(o2) shouldBe false
  }

  it should "not match if types are the same" in {
    val o1 = Order("C1", "A", "b", 1, 1)
    val o2 = Order("C2", "A", "b", 1, 1)
    o1.isMatch(o2) shouldBe false
  }

  it should "not match if clients are the same" in {
    val o1 = Order("C1", "A", "s", 1, 1)
    val o2 = Order("C1", "A", "b", 1, 1)
    o1.isMatch(o2) shouldBe false
  }

  it should "not match if 's' price > 'b' price" in {
    val o1 = Order("C1", "A", "s", 1, 2)
    val o2 = Order("C2", "A", "b", 1, 1)
    o1.isMatch(o2) shouldBe false
  }

  it should "not match if 'b' price < 's' price" in {
    val o1 = Order("C1", "A", "b", 1, 1)
    val o2 = Order("C2", "A", "s", 1, 2)
    o1.isMatch(o2) shouldBe false
  }

  it should "not match if quantities are differ" in {
    val o1 = Order("C1", "A", "b", 1, 1)
    val o2 = Order("C2", "A", "s", 1, 2)
    o1.isMatch(o2) shouldBe false
  }

  it should "create buy trade" in {
    val o1 = Order("C1", "A", "b", 1, 1)
    val o2 = Order("C2", "A", "s", 1, 1)
    o1.matchOrder(o2) shouldBe Trade("C2", "C1", "A", 1, 1)
  }

  it should "create sale trade" in {
    val o1 = Order("C1", "A", "s", 1, 1)
    val o2 = Order("C2", "A", "b", 1, 1)
    o1.matchOrder(o2) shouldBe Trade("C1", "C2", "A", 1, 1)
  }

  it should "throw an exception if orders don't match" in {
    val o1 = Order("C1", "A", "b", 1, 1)
    val o2 = Order("C1", "A", "b", 1, 1)
    intercept[Exception] {
      o1.matchOrder(o2)
    }

  }


}