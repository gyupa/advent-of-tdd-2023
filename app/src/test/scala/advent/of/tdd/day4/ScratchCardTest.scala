package advent.of.tdd.day4

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScratchCardTest extends AnyFlatSpec {

  "Scratch card" should "return 0 score for no match" in {
    val sut = new ScratchCard(
      cardNumber = 1,
      winningNumbers = Set(1, 2, 3),
      ourNumbers = Set(4, 5, 6)
    )

    sut.getScore should be (0)
  }

  it should "return 1 score for single match" in {
    val sut = new ScratchCard(
      cardNumber = 1,
      winningNumbers = Set(1, 2, 3),
      ourNumbers = Set(3, 4, 5, 6)
    )

    sut.getScore should be(1)
  }

  it should "return 8 score for 4 matches" in {
    val sut = new ScratchCard(
      cardNumber = 1,
      winningNumbers = Set(1, 2, 3, 4, 5),
      ourNumbers = Set(2, 3, 4, 5, 6)
    )

    sut.getScore should be(8)
  }

  it should "extract card from line without extra whitespaces" in {

    ScratchCard.extractFromLine(
      "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
    ) should be (
      ScratchCard(
        cardNumber = 2,
        winningNumbers = Set(13, 32, 20, 16, 61),
        ourNumbers = Set(61, 30, 68, 82, 17, 32, 24, 19)
      )
    )
  }

  it should "extract card from line with extra whitespaces" in {

    ScratchCard.extractFromLine(
      "Card 3:  1 21 53 59  4 |  9 82 63 72 16 21 14  1"
    ) should be(
      ScratchCard(
        cardNumber = 3,
        winningNumbers = Set(1, 21, 53, 59, 4),
        ourNumbers = Set(9, 82, 63, 72, 16, 21, 14, 1)
      )
    )
  }
}
