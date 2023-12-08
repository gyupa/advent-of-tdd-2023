package advent.of.tdd.day7

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CamelCardsGameTest extends AnyFlatSpec {

  "Camel cards game" should "sort hands of cards properly" in {
    val linesEither: Either[String, Seq[String]] = FileReaderImpl.readLinesFromFile("src/main/resources/day7_example.txt")
    linesEither.map {
      lines =>
        val game = CamelCardsGame.createGameFromLines(lines)
        game.handsOfCardsSorted should be(
          Seq(
            HandOfCards(Seq(Card.`3`, Card.`2`, Card.`T`, Card.`3`, Card.`K`)),
            HandOfCards(Seq(Card.`K`, Card.`K`, Card.`6`, Card.`7`, Card.`7`)),
            HandOfCards(Seq(Card.`T`, Card.`5`, Card.`5`, Card.`J`, Card.`5`)),
            HandOfCards(Seq(Card.`Q`, Card.`Q`, Card.`Q`, Card.`J`, Card.`A`)),
            HandOfCards(Seq(Card.`K`, Card.`T`, Card.`J`, Card.`J`, Card.`T`))
          )
        )

    }
  }

  "Camel cards game" should "evaluate sample file properly with jokers" in {
    val linesEither: Either[String, Seq[String]] = FileReaderImpl.readLinesFromFile("src/main/resources/day7_example.txt")
    linesEither.map {
      lines =>
        val game = CamelCardsGame.createGameFromLines(lines)
        game.calculateScore should be(5905)
    }
  }

  "Camel cards game" should "evaluate input file properly" in {
    val linesEither: Either[String, Seq[String]] = FileReaderImpl.readLinesFromFile("src/main/resources/day7_input.txt")
    linesEither.map {
      lines =>
        val game = CamelCardsGame.createGameFromLines(lines)
        println(game.calculateScore)
    }
  }
}
