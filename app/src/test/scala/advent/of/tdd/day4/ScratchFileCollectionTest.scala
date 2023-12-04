package advent.of.tdd.day4

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScratchFileCollectionTest extends AnyFlatSpec {

  "Scratch file collection" should "return score of card in case of single card" in {
    val sut = new ScratchCardCollection

    sut.addScratchCard(
      ScratchCard(
        cardNumber = 1,
        winningNumbers = Set(1, 2, 3),
        ourNumbers = Set(2, 3, 4)
      )
    )

    sut.calculateTotalNumberOfMatches should be(2)
  }

  it should "return sum of scores different cards" in {
    val sut = new ScratchCardCollection

    sut.addScratchCard(
      ScratchCard(
        cardNumber = 1,
        winningNumbers = Set(1, 2, 3),
        ourNumbers = Set(2, 3, 4)
      )
    )
    sut.addScratchCard(
      ScratchCard(
        cardNumber = 2,
        winningNumbers = Set(1, 2, 3, 4),
        ourNumbers = Set(2, 3, 4, 5)
      )
    )

    sut.calculateTotalNumberOfMatches should be(5)
  }

  it should "return sum of scores all cards (even if some of them are identical)" in {
    val sut = new ScratchCardCollection

    sut.addScratchCard(
      ScratchCard(
        cardNumber = 1,
        winningNumbers = Set(1, 2, 3),
        ourNumbers = Set(2, 3, 4)
      )
    )
    sut.addScratchCard(
      ScratchCard(
        cardNumber = 2,
        winningNumbers = Set(1, 2, 3, 4),
        ourNumbers = Set(2, 3, 4, 5)
      )
    )
    sut.addScratchCard(
      ScratchCard(
        cardNumber = 2,
        winningNumbers = Set(1, 2, 3, 4),
        ourNumbers = Set(2, 3, 4, 5)
      )
    )

    sut.calculateTotalNumberOfMatches should be(8)
  }

  private val lines = Seq(
    "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
    "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
    "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
    "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
    "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
    "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
  )

  private val cardsForLines = Seq(
    ScratchCard(
      cardNumber = 1,
      winningNumbers = Set(41, 48, 83, 86, 17),
      ourNumbers = Set(83, 86, 6, 31, 17, 9, 48, 53)
    ),
    ScratchCard(
      cardNumber = 2,
      winningNumbers = Set(13, 32, 20, 16, 61),
      ourNumbers = Set(61, 30, 68, 82, 17, 32, 24, 19)
    ),
    ScratchCard(
      cardNumber = 3,
      winningNumbers = Set(1, 21, 53, 59, 44),
      ourNumbers = Set(69, 82, 63, 72, 16, 21, 14, 1)
    ),
    ScratchCard(
      cardNumber = 4,
      winningNumbers = Set(41, 92, 73, 84, 69),
      ourNumbers = Set(59, 84, 76, 51, 58, 5, 54, 83)
    ),
    ScratchCard(
      cardNumber = 5,
      winningNumbers = Set(87, 83, 26, 28, 32),
      ourNumbers = Set(88, 30, 70, 12, 93, 22, 82, 36)
    ),
    ScratchCard(
      cardNumber = 6,
      winningNumbers = Set(31, 18, 13, 56, 72),
      ourNumbers = Set(74, 77, 10, 23, 35, 67, 36, 11)
    )
  )


  it should "read the collection in example 1 properly" in {

    ScratchCardCollection.extractCollectionFromFile(
      lines
    ).calculateTotalNumberOfCards should be(6)
  }

  it should "calculate the total score properly" in {

    val sut = new ScratchCardCollection
    cardsForLines.foreach(sut.addScratchCard)

    sut.calculateTotalScore should be (13)
  }

  it should "evaluate new cards properly" in {

    val sut = ScratchCardCollection.extractCollectionFromFile(
      lines
    )

    sut.calculateTotalNumberOfCards should be (6)

    sut.addNewCardsByEvaluation

    sut.calculateTotalNumberOfCards should be(30)
  }
}
