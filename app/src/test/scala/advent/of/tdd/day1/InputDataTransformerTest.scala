package advent.of.tdd.day1

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class InputDataTransformerTest extends AnyFlatSpec {

  "Input data transformer" should "not change empty row" in {
    InputDataTransformerImpl.replaceNumbersAsWordsWithDigits(Seq.empty) should be(Seq.empty)
  }

  it should "not change row without number word" in {
    InputDataTransformerImpl.replaceNumbersAsWordsWithDigits(Seq("adbfd34ref")) should be(Seq("adbfd34ref"))
  }

  it should "replace one with 1" in {
    InputDataTransformerImpl.replaceNumbersAsWordsWithDigits(
      Seq("adone34ref")
    ) should be(
      Seq("ad134ref")
    )
  }

  it should "replace all numbers properly" in {
    InputDataTransformerImpl.replaceNumbersAsWordsWithDigits(
      Seq(
        "adone34ref",
        "ad34two",
        "ad34three",
        "wfourq",
        "fiveaaa",
        "sixaaa",
        "aaaseven",
        "aaaeight",
        "nineaaa"
      )
    ) should be(
      Seq(
        "ad134ref",
        "ad342",
        "ad343",
        "w4q",
        "5aaa",
        "6aaa",
        "aaa7",
        "aaa8",
        "9aaa"
      )
    )
  }

  it should "replace same digit multiple times" in {
    InputDataTransformerImpl.replaceNumbersAsWordsWithDigits(
      Seq(
        "adone34onerefone"
      )
    ) should be(
      Seq(
        "ad1341ref1"
      )
    )
  }

  it should "replace same multiple digits in same line" in {
    InputDataTransformerImpl.replaceNumbersAsWordsWithDigits(
      Seq(
        "adone34tworefthree"
      )
    ) should be(
      Seq(
        "ad1342ref3"
      )
    )
  }
}
