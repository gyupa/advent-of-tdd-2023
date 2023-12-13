package advent.of.tdd.day13

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MirrorMapTest extends AnyFlatSpec {

  "Mirror map" should "see if rows or columns are symmetric" in {
    MirrorMap.areColumnsOrRowsSymmetric(
      Seq(".", "#", "#", ".")
    ) should be (true)
  }

  it should "see if rows or columns are not symmetric" in {
    MirrorMap.areColumnsOrRowsSymmetric(
      Seq(".", "#", ".")
    ) should be(false)
  }

  it should "count mirrors properly" in {
    MirrorMap.findPositionsOfMirrors(
      Seq(".", ".", "." , "#", "#", ".")
    ) should be(Seq(1, 4))
  }

  it should "count mirrors properly2" in {
    MirrorMap.findPositionsOfMirrors(
      Seq(".", ".", "#", ".", ".")
    ) should be(Seq(1, 4))
  }

  it should "count mirrors properly3" in {
    MirrorMap.findPositionsOfMirrors(
      Seq("##", "##", "..", "..", "..", "..", "..", "..", "##")
    ) should be(Seq(1, 5))
  }

  it should "find mirrors properly" in {
    val seq = Seq(".", ".", "#", ".", ".")
    MirrorMap.isMirrorAfterRow(1, seq) should be (true)
    MirrorMap.isMirrorAfterRow(2, seq) should be (false)
    MirrorMap.isMirrorAfterRow(3, seq) should be (false)
    MirrorMap.isMirrorAfterRow(4, seq) should be (true)
    MirrorMap.isMirrorAfterRow(5, seq) should be (false)

    MirrorMap.isMirrorAfterRow(1, Seq("##", "##", "..", "..", "..", "..", "..", "..", "##")) should be (true)
    MirrorMap.isMirrorAfterRow(5, Seq("##", "##", "..", "..", "..", "..", "..", "..", "##")) should be (true)

  }

  it should "it should count mirror score properly" in {
    val rows = Seq("##......#", "##......#")
    val columns = Seq("##", "##", "..", "..", "..", "..", "..", "..", "##")
    MirrorMap(rows, columns).calculateTotalScoreOfMirrors should be (101) // note that this has 2 mirrors in columns!!!
  }

  it should "it should count mirror score properly2" in {
    val rows = Seq(
      "#....#.",
      "#....##",
      ".####..",
      ".####..",
      "#....##",
      "#.##.#.",
      ".####.#"
    )
    val columns = Seq(
      "##..##.",
      "..##..#",
      "..##.##",
      "..##.##",
      "..##..#",
      "##..##.",
      ".#..#.#"
    )
    MirrorMap.findPositionsOfMirrors(rows) should be (Seq.empty)
    MirrorMap.findPositionsOfMirrors(columns) should be (Seq(3))
    MirrorMap(rows, columns).calculateTotalScoreOfMirrors should be(3)
  }

  it should "it should count mirror score properly3" in {
    val columns = Seq(
      ".#..#.#",
      "##..##.",
      "..##..#",
      "..##.##",
      "..##.##",
      "..##..#",
      "##..##."
    )
    MirrorMap.findPositionsOfMirrors(columns) should be(Seq(4))
  }

  it should "construct object properly from lines" in {
    val lines =
      Seq(
        "##......#",
        "##......#"
      )

    MirrorMap.readFromLines(lines) should be (
      MirrorMap(
        rows = Seq("##......#", "##......#"),
        columns = Seq("##", "##", "..", "..", "..", "..", "..", "..", "##")
      )
    )
  }

  it should "calculate example properly" in {
    FileReaderImpl.readLinesFromFile("src/main/resources/day13_example1.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
        fail()
      case Right(fileContents) =>
        MirrorMap.readFromLines(fileContents).calculateTotalScoreOfMirrors should be (5)
    }
  }

  it should "calculate example2 properly" in {
    FileReaderImpl.readLinesFromFile("src/main/resources/day13_example2.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
        fail()
      case Right(fileContents) =>
        MirrorMap.readFromLines(fileContents).calculateTotalScoreOfMirrors should be(400)
    }
  }

  it should "calculate example properly with broken mirrors" in {
    FileReaderImpl.readLinesFromFile("src/main/resources/day13_example1.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
        fail()
      case Right(fileContents) =>
        MirrorMap.readFromLines(fileContents).calculateRepairedTotalScoreOfMirrors should be(300)
    }
  }
}
