package advent.of.tdd.day3

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.language.postfixOps

@RunWith(classOf[JUnitRunner])
class AdjacentCharacterExtractorTest extends AnyFlatSpec {

  "Adjacent character extractor" should "return all adjacent dots for non-engine part having single digit" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "....3...",
      previousLine = Some("........"),
      nextLine = Some("........")
    ) should be(Seq(3 -> "." * 8))
  }

  it should "return all adjacent dots for non-engine part having multiple digits" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "....345.",
      previousLine = Some("........"),
      nextLine = Some("........")
    ) should be(Seq(345 -> "." * 12))
  }

  it should "return all adjacent dots from previous and next line" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "....345.",
      previousLine = Some("...abcde"),
      nextLine = Some("...fghij")
    ) should be(Seq(345 -> "abcde..fghij"))
  }

  it should "handle case when current line is first" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "....345.",
      previousLine = None,
      nextLine = Some("...fghij")
    ) should be(Seq(345 -> "..fghij"))
  }

  it should "handle case when current line is last" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "....345.",
      previousLine = Some("...abcde"),
      nextLine = None
    ) should be(Seq(345 -> "abcde.."))
  }

  it should "handle case when number is at beginning of line" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "345abc",
      previousLine = Some("defghi"),
      nextLine = Some("jklmno")
    ) should be(Seq(345 -> "defgajklm"))
  }

  it should "handle case when number is at end of line" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "abc345",
      previousLine = Some("defghi"),
      nextLine = Some("jklmno")
    ) should be(Seq(345 -> "fghiclmno"))
  }

  it should "return empty seq if line contains no number" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "abcdef",
      previousLine = Some("2e34f56i"),
      nextLine = Some("jkl66o")
    ) should be(Seq.empty)
  }

  it should "handle multiple numbers in line properly" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentCellsFromNumbersInLine(
      line = "1ab23c456",
      previousLine = Some("defghijkl"),
      nextLine = Some("mnopqrstu")
    ) should be(
      Seq(
        1 -> "deamn",
        23 -> "fghibcopqr",
        456 ->"ijklcrstu"
      )
    )
  }

  it should "extract adjacent numbers from same line" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentNumbersForGearCandidatesInLine(
      line = "1ab23*456",
      previousLine = None,
      nextLine = None
    ) should be(
      Seq(
        Seq(23, 456)
      )
    )
  }

  it should "extract adjacent numbers from neighbouring lines as well" in {
    val sut = new AdjacentCharacterExtractor()
    sut.extractAdjacentNumbersForGearCandidatesInLine(
      line = "*ab23*cd*456",
      previousLine = Some("e7f8912ghij"),
      nextLine = Some("123klmn4444")
    ) should be(
      Seq(
        Seq(7, 123),
        Seq(8912, 23),
        Seq(456, 4444)
      )
    )
  }
}
