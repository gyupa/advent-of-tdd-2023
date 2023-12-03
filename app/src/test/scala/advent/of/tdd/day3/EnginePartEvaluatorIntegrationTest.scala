package advent.of.tdd.day3

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EnginePartEvaluatorIntegrationTest extends AnyFlatSpec {

  "Engine part evaluator" should "add parts properly" in {

    val sut = new EnginePartEvaluator(new AdjacentCharacterExtractor)

    FileReaderImpl.readLinesFromFile("day3_a_example.txt") match {
      case Left(errorMessage) =>
        fail()
      case Right(fileContents) =>
        sut.calculateSumFromEngineParts(fileContents) should be (4361)
    }
  }

  it should "calculate gear ratio properly" in {

    val sut = new EnginePartEvaluator(new AdjacentCharacterExtractor)

    FileReaderImpl.readLinesFromFile("day3_b_example.txt") match {
      case Left(errorMessage) =>
        fail()
      case Right(fileContents) =>
        sut.calculateGearRatio(fileContents) should be(467835)
    }
  }
}
