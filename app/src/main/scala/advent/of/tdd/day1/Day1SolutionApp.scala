package advent.of.tdd.day1

import advent.of.tdd.utils.FileReaderImpl

object Day1SolutionApp {

  def main(args: Array[String]): Unit = {
    val calculator = TotalScoreCalculator(
      FileReaderImpl,
      InputDataTransformerImpl,
      InputDataValidatorImpl,
      CalibrationValueAggregator(CalibrationValueExtractorImpl)
    )

    println(calculator.calculateScore("day1_calibration_input.txt"))
  }

}
