package advent.of.tdd.day9

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SequenceTest extends AnyFlatSpec {

  "Sequence" should "return the last values of subsequences of differences" in {
    Sequence(Seq(0, 3, 6, 9, 12, 15)).findLastElementsOfDiffSequences should be (Seq(15, 3, 0))
    Sequence(Seq(1, 3, 6, 10, 15, 21)).findLastElementsOfDiffSequences should be (Seq(21, 6, 1, 0))
    Sequence(Seq(10, 13, 16, 21, 30, 45)).findLastElementsOfDiffSequences should be (Seq(45, 15, 6, 2, 0))
  }

  it should "calculate next element proerly" in {
    Sequence(Seq(0, 3, 6, 9, 12, 15)).calculateNextElement should be(18)
    Sequence(Seq(1, 3, 6, 10, 15, 21)).calculateNextElement should be(28)
    Sequence(Seq(10, 13, 16, 21, 30, 45)).calculateNextElement should be(68)
  }

  it should "read sequence from file properly" in {
    Sequence.createSequenceFromLine("0 3 6 9 12 15") should be (Sequence(Seq(0, 3, 6, 9, 12, 15)))
    Sequence.createSequenceFromLine("1 3 6 10 15 21") should be (Sequence(Seq(1, 3, 6, 10, 15, 21)))
    Sequence.createSequenceFromLine("10 13 16 21 30 45") should be (Sequence(Seq(10, 13, 16, 21, 30, 45)))
  }

  it should "return the first values of subsequences of differences" in {
    Sequence(Seq(0, 3, 6, 9, 12, 15)).findFirstElementsOfDiffSequences should be(Seq(0, 3, 0))
    Sequence(Seq(1, 3, 6, 10, 15, 21)).findFirstElementsOfDiffSequences should be(Seq(1, 2, 1, 0))
    Sequence(Seq(10, 13, 16, 21, 30, 45)).findFirstElementsOfDiffSequences should be(Seq(10, 3, 0, 2, 0))
  }

  it should "calculate previous element proerly" in {
    Sequence(Seq(0, 3, 6, 9, 12, 15)).calculatePreviousElement should be(-3)
    Sequence(Seq(1, 3, 6, 10, 15, 21)).calculatePreviousElement should be(0)
    Sequence(Seq(10, 13, 16, 21, 30, 45)).calculatePreviousElement should be(5)
  }

}
