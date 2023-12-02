package advent.of.tdd.day1

import advent.of.tdd.utils.FileReader
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.collection.immutable.Seq

@RunWith(classOf[JUnitRunner])
class TotalScoreCalculatorTest extends AnyFlatSpec with MockFactory {

  "Total score calculation" should "read file and aggregate scores from records" in {
    val fileContents = Seq("a", "b")
    val transformedContents = Seq("c", "d")

    val fileReaderStub = stub[FileReader]
    (fileReaderStub.readLinesFromFile _).when("fn").returns(Right(fileContents))

    val inputDataTransformerStub = stub[InputDataTransformer]
    (inputDataTransformerStub.replaceNumbersAsWordsWithDigits _).when(fileContents).returns(transformedContents)

    val inputDataValidatorStub = stub[InputDataValidator]
    (inputDataValidatorStub.validateInput _).when(transformedContents).returns(true)

    val scoreAggregatorStub = stub[CalibrationValueAggregator]
    (scoreAggregatorStub.calculateTotalCalibrationScore _).when(transformedContents).returns(1234)

    val sut = TotalScoreCalculator(
      fileReader = fileReaderStub,
      inputDataTransformer = inputDataTransformerStub,
      inputDataValidator = inputDataValidatorStub,
      calibrationValueAggregator = scoreAggregatorStub
    )

    sut.calculateScore("fn") should be(Right(1234))
  }

  it should "return error when validation fails" in {
    val fileContents = Seq("a", "b")
    val transformedContents = Seq("c", "d")

    val fileReaderStub = stub[FileReader]
    (fileReaderStub.readLinesFromFile _).when("fn").returns(Right(fileContents))

    val inputDataTransformerStub = stub[InputDataTransformer]
    (inputDataTransformerStub.replaceNumbersAsWordsWithDigits _).when(fileContents).returns(transformedContents)

    val inputDataValidatorStub = stub[InputDataValidator]
    (inputDataValidatorStub.validateInput _).when(transformedContents).returns(false)

    val scoreAggregatorStub = stub[CalibrationValueAggregator]

    val sut = TotalScoreCalculator(
      fileReader = fileReaderStub,
      inputDataTransformer = inputDataTransformerStub,
      inputDataValidator = inputDataValidatorStub,
      calibrationValueAggregator = scoreAggregatorStub
    )

    sut.calculateScore("fn") should be(Left("Invalid input"))
  }

  it should "return error when file read operation fails" in {
    val fileContents = Seq("a", "b")

    val fileReaderStub = stub[FileReader]
    (fileReaderStub.readLinesFromFile _).when("fn").returns(Left("error"))

    val inputDataTransformerStub = stub[InputDataTransformer]
    val inputDataValidatorStub = stub[InputDataValidator]
    val scoreAggregatorStub = stub[CalibrationValueAggregator]

    val sut = TotalScoreCalculator(
      fileReader = fileReaderStub,
      inputDataTransformer = inputDataTransformerStub,
      inputDataValidator = inputDataValidatorStub,
      calibrationValueAggregator = scoreAggregatorStub
    )

    sut.calculateScore("fn") should be(Left("error"))
  }
}
