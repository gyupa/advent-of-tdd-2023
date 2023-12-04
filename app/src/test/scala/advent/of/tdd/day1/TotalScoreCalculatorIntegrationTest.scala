package advent.of.tdd.day1

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TotalScoreCalculatorIntegrationTest extends AnyFlatSpec {

  "Integration test" should "verify example in task A description" in {
    val calculator = TotalScoreCalculator(
      FileReaderImpl,
      InputDataValidatorImpl,
      CalibrationValueAggregator(CalibrationValueExtractorImpl)
    )

    calculator.calculateScore("src/main/resources/day1_a_example.txt").toOption.get should be (142)
  }

  it should "verify example in task B description" in {
    val calculator = TotalScoreCalculator(
      FileReaderImpl,
      InputDataValidatorImpl,
      CalibrationValueAggregator(CalibrationValueExtractorImpl)
    )

    calculator.calculateScore("src/main/resources/day1_b_example.txt").toOption.get should be(281)
  }
}
