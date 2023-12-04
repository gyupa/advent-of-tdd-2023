package advent.of.tdd.day4

import scala.collection.immutable.Set

case class ScratchCard(
                        cardNumber: Int,
                        winningNumbers: Set[Int],
                        ourNumbers: Set[Int]
                      ) {
  def getScore: Int = {
    val numberOfMatches = getNumberOfMatches

    if (numberOfMatches == 0)
      0
    else
      Math.pow(2, numberOfMatches - 1).toInt
  }

  def getNumberOfMatches: Int = winningNumbers.intersect(ourNumbers).size
}

object ScratchCard {
  def extractFromLine(line: String): ScratchCard = {

    val cardNumber: Int = {
      val cardNumberPattern = "(\\d+):".r
      cardNumberPattern.findFirstMatchIn(line).get.group(1).toInt
    }

    val winningNumbers: Set[Int] = {
      val winningPattern = ":(.*)\\|".r
      val winningString = winningPattern.findFirstMatchIn(line).get.group(1)

      extractNumbers(winningString)
    }

    val ourNumbers: Set[Int] = {
      val ourNumbersPattern = "\\|(.*)".r
      val ourNumbersString = ourNumbersPattern.findFirstMatchIn(line).get.group(1)

      extractNumbers(ourNumbersString)
    }

    ScratchCard(
      cardNumber = cardNumber,
      winningNumbers = winningNumbers,
      ourNumbers = ourNumbers
    )
  }

  private def extractNumbers(numbersSeparatedBySpaces: String): Set[Int] = {
    val numberPattern = "(\\d+)".r
    numberPattern.findAllMatchIn(numbersSeparatedBySpaces).map(_.group(1).toInt).toSet
  }
}
