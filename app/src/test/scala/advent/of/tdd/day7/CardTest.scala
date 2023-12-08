package advent.of.tdd.day7

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class CardTest extends AnyFlatSpec {

  "Card" should "have correct ordering" in {
    assert(Card.`2` < Card.`3`)
    assert(Card.`A` > Card.`K`)
    assert(Card.`4` == Card.`4`)
    assert(Card.`5` < Card.`Q`)
    assert(Card.`T` > Card.`6`)
    assert(Card.`J` < Card.`Q`)
    assert(Card.`J` < Card.`2`)
  }
}
