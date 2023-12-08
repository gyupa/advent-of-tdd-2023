package advent.of.tdd.day7

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.collection.immutable.Seq


@RunWith(classOf[JUnitRunner])
class HandOfCardsTest extends AnyFlatSpec {

  "Hand of cards" should "evaluate five of a kind properly" in {
    HandOfCards(
      Seq(
        Card.`6`,
        Card.`6`,
        Card.`6`,
        Card.`6`,
        Card.`6`
      )
    ).category should be(HandsOfCardsType.FiveOfAKind)

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`J`,
        Card.`6`,
        Card.`6`,
        Card.`J`
      )
    ).category should be(HandsOfCardsType.FiveOfAKind)
  }

  it should "evaluate four of a kind properly" in {

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`6`,
        Card.`6`,
        Card.`6`
      )
    ).category should be(HandsOfCardsType.FourOfAKind)

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`J`,
        Card.`6`,
        Card.`J`
      )
    ).category should be(HandsOfCardsType.FourOfAKind)
  }

  it should "evaluate full house properly" in {
    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`6`,
        Card.`6`,
        Card.`7`
      )
    ).category should be(HandsOfCardsType.FullHouse)

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`J`,
        Card.`6`,
        Card.`7`
      )
    ).category should be(HandsOfCardsType.FullHouse)
  }

  it should "evaluate three of a kind properly" in {
    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`6`,
        Card.`6`,
        Card.`8`
      )
    ).category should be(HandsOfCardsType.ThreeOfAKind)

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`J`,
        Card.`J`,
        Card.`8`
      )
    ).category should be(HandsOfCardsType.ThreeOfAKind)

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`J`,
        Card.`6`,
        Card.`8`
      )
    ).category should be(HandsOfCardsType.ThreeOfAKind)
  }

  it should "evaluate two pair properly" in {
    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`7`,
        Card.`6`,
        Card.`8`
      )
    ).category should be(HandsOfCardsType.TwoPair)
  }

  it should "evaluate one pair properly" in {
     HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`7`,
        Card.`9`,
        Card.`8`
      )
    ).category should be(HandsOfCardsType.OnePair)

    HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`J`,
        Card.`9`,
        Card.`8`
      )
    ).category should be(HandsOfCardsType.OnePair)
  }

  it should "evaluate high card properly" in {
    val sut = HandOfCards(
      Seq(
        Card.`6`,
        Card.`7`,
        Card.`T`,
        Card.`9`,
        Card.`8`
      )
    )

    sut.category should be(HandsOfCardsType.HighCard)
  }

  it should "compare hands properly when categories are different" in {
    val highCard = HandOfCards(Seq(Card.`6`, Card.`7`, Card.`T`, Card.`9`, Card.`8`))
    val onePair = HandOfCards(Seq(Card.`7`, Card.`7`, Card.`T`, Card.`9`, Card.`8`))
    val twoPair = HandOfCards(Seq(Card.`7`, Card.`7`, Card.`T`, Card.`T`, Card.`8`))
    val threeOfAKind = HandOfCards(Seq(Card.`7`, Card.`7`, Card.`7`, Card.`T`, Card.`8`))
    val fullHouse = HandOfCards(Seq(Card.`7`, Card.`7`, Card.`7`, Card.`T`, Card.`T`))
    val fourOfAKind = HandOfCards(Seq(Card.`7`, Card.`7`, Card.`7`, Card.`7`, Card.`T`))
    val fiveOfAKind = HandOfCards(Seq(Card.`7`, Card.`7`, Card.`7`, Card.`7`, Card.`7`))

    assert(highCard.compare(onePair) < 0)
    assert(onePair.compare(highCard) > 0)

    Seq(fourOfAKind, twoPair, onePair, fiveOfAKind, fullHouse, threeOfAKind, highCard).sorted should be(
      Seq(highCard, onePair, twoPair, threeOfAKind, fullHouse, fourOfAKind, fiveOfAKind)
    )
  }

  it should "compare hands properly when categories are the same" in {
    val highCard1 = HandOfCards(Seq(Card.`6`, Card.`7`, Card.`T`, Card.`9`, Card.`8`))
    val highCard2 = HandOfCards(Seq(Card.`6`, Card.`7`, Card.`T`, Card.`Q`, Card.`8`))

    assert(highCard1.compare(highCard2) < 0)
  }

  it should "compare two pairs properly when categories are the same" in {
    val tp1 = HandOfCards(Seq(Card.`K`, Card.`K`, Card.`6`, Card.`7`, Card.`7`))
    val tp2 = HandOfCards(Seq(Card.`K`, Card.`T`, Card.`Q`, Card.`Q`, Card.`T`))

    assert(tp1.compare(tp2) > 0)
  }

  it should "compare three of a kind properly when categories are the same with joker" in {
    val tp1 = HandOfCards(Seq(Card.`J`, Card.`K`, Card.`K`, Card.`7`, Card.`6`))
    val tp2 = HandOfCards(Seq(Card.`K`, Card.`K`, Card.`Q`, Card.`J`, Card.`T`))

    assert(tp1.compare(tp2) < 0)
  }
}
