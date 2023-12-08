package advent.of.tdd.day7

import javax.print.attribute.standard.MediaSize.Other


object HandsOfCardsType extends Enumeration {
  val HighCard, OnePair, TwoPair, ThreeOfAKind, FullHouse, FourOfAKind, FiveOfAKind = Value
}

case class HandOfCards(
                        cards: Seq[Card.Value]
                      ) extends Ordered[HandOfCards] {


  val category: HandsOfCardsType.Value = {
    assert(cards.size == 5)
    val countByCard: Map[Card.Value, Int] =
      cards.toSet.map {
        card: Card.Value => card -> cards.count(_ == card)
      }.toMap

    if (cards.toSet.size == 1 || (cards.toSet.size == 2 && countByCard.contains(Card.`J`)))
      HandsOfCardsType.FiveOfAKind
    else if (countByCard.exists(_._2 == 4) || hasFourOfAKindWithJokers(countByCard))
      HandsOfCardsType.FourOfAKind
    else if ((countByCard.exists(_._2 == 3) && countByCard.size == 2) || (countByCard.size == 3 && countByCard.contains(Card.`J`)))
      HandsOfCardsType.FullHouse
    else if ((countByCard.exists(_._2 == 3) && countByCard.size == 3) || (countByCard.size == 4 && (countByCard.getOrElse(Card.`J`, 0) == 2 || countByCard.getOrElse(Card.`J`, 0) == 1)))
      HandsOfCardsType.ThreeOfAKind
    else if (countByCard.exists(_._2 == 2) && countByCard.size == 3)
      HandsOfCardsType.TwoPair
    else if (countByCard.size == 4 || (countByCard.size == 5 && countByCard.getOrElse(Card.`J`, 0) == 1))
      HandsOfCardsType.OnePair
    else
      HandsOfCardsType.HighCard
  }

  private def hasFourOfAKindWithJokers(countByCard: Map[Card.Value, Int]) = {
    countByCard.contains(Card.`J`) && countByCard.exists(card => card._1 != Card.`J` && card._2 + countByCard(Card.`J`) == 4)
  }

  override def compare(that: HandOfCards): Int = {
    val compareCategoryResult = category.compare(that.category)
    if (compareCategoryResult == 0) {
      val firstDifferentCardOption = cards.zip(that.cards).find(pair => pair._1 != pair._2)
      firstDifferentCardOption.map(pair => pair._1.compare(pair._2)).getOrElse(0)
    } else
      compareCategoryResult
  }
}
