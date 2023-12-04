package advent.of.tdd.day4

import scala.collection.mutable


class ScratchCardCollection {
  def addNewCardsByEvaluation: Unit = {
    for (i <- 1 to cardsById.keys.size) {
      val numberOfNewCardsToAdd = cardsById(i)._1.getNumberOfMatches
      val numberOfTimesToAddEachCard = cardsById(i)._2
      println(s"Evaluate id $i, number of new cards: $numberOfNewCardsToAdd, how many times: $numberOfTimesToAddEachCard")
      for (j <- 1 to numberOfNewCardsToAdd) {
        for (k <- 1 to numberOfTimesToAddEachCard)
          addScratchCard(cardsById(i + j)._1)
      }
    }
  }

  private val cardsById: mutable.Map[Int, (ScratchCard, Int)] = mutable.Map.empty

  def addScratchCard(card: ScratchCard): Unit = {
    if (cardsById.contains(card.cardNumber))
      cardsById(card.cardNumber) = (cardsById(card.cardNumber)._1, cardsById(card.cardNumber)._2 + 1)
    else
      cardsById(card.cardNumber) = (card, 1)
  }

  def calculateTotalNumberOfMatches: Int = {
    cardsById.values.map(pair => pair._1.getNumberOfMatches * pair._2).sum
  }

  def calculateTotalScore: Int =
    cardsById.values.map(pair => pair._1.getScore * pair._2).sum

  def calculateTotalNumberOfCards: Int =
    cardsById.values.map(_._2).sum
}

object ScratchCardCollection {

  def extractCollectionFromFile(lines: Seq[String]): ScratchCardCollection = {
    val collection = new ScratchCardCollection
    for (line <- lines) yield {
      collection.addScratchCard(ScratchCard.extractFromLine(line))
    }
    collection
  }
}
