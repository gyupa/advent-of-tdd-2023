package advent.of.tdd.day14

import advent.of.tdd.utils.{FileReaderImpl, Matrix}
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlatformTest extends AnyFlatSpec {

  "Platform" should "calculate score properly for single column" in {
    val matrix = Matrix(
      Seq(
        Seq('O'),
        Seq('O'),
        Seq('#')
      )
    )

    Platform(matrix).calculateScore should be(5)
  }

  it should "calculate score when rounded rocks move" in {
    val matrix = Matrix(
      Seq(
        Seq('.'),
        Seq('O'),
        Seq('#'),
        Seq('.'),
        Seq('O')
      )
    )

    Platform(matrix).rollNorth.calculateScore should be(7)
  }

  it should "calculate score when no rounded between cubes" in {
    val matrix = Matrix(
      Seq(
        Seq('#'),
        Seq('.'),
        Seq('#'),
        Seq('.'),
        Seq('.'),
        Seq('O'),
        Seq('#'),
        Seq('.'),
        Seq('#'),
        Seq('#')
      )
    )

    Platform(matrix).rollNorth.calculateScore should be(7)
  }

  it should "calculate multiple columns correctly" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '#'),
        Seq('O', 'O'),
        Seq('#', '.'),
        Seq('.', '.'),
        Seq('O', 'O')
      )
    )

    Platform(matrix).rollNorth.calculateScore should be(14)
  }

  it should "find rounded positions" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '#'),
        Seq('O', 'O'),
        Seq('#', '.'),
        Seq('O', '.'),
        Seq('O', 'O')
      )
    )

    Platform(matrix).roundedRockPositions should be(
      Set(
        (1, 0), (1, 1), (3, 0), (4, 0), (4, 1)
      )
    )
  }

  it should "calculate example correctly" in {

    FileReaderImpl.readLinesFromFile("src/main/resources/day14_example.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
        fail()
      case Right(fileContents) =>
        Platform.readFromLines(fileContents).rollNorth.calculateScore should be(136)
    }
  }
}
