package advent.of.tdd.day7

case class CamelCardsGame(
                           handsWithBids: Seq[(HandOfCards, Int)]
                         ) {
  def handsOfCardsSorted: Seq[HandOfCards] = {
    val hands = handsWithBids.map(_._1)
    val sortedHands = hands.sorted
    sortedHands
  }

  def calculateScore: Long = {

    val handAndBidsAndRank: Seq[((HandOfCards, Int), Int)] = handsWithBids.sortBy(
      _._1
    ).zipWithIndex.map(pair => (pair._1, pair._2 + 1))

    println(handAndBidsAndRank)

    handAndBidsAndRank.map(element => element._1._2.toLong * element._2.toLong).sum
  }

}

object CamelCardsGame {
  def createGameFromLines(lines: Seq[String]): CamelCardsGame = {
    val pairs: Seq[(HandOfCards, Int)] = lines.map {
      line: String =>
        val (cards: String, bid: String) = {
          val lineParts = line.split(" +")
          (lineParts(0), lineParts(1))
        }
        (HandOfCards(cards.map(_.toString).map(Card.withName)), bid.toInt)
    }
    CamelCardsGame(pairs)

  }
}