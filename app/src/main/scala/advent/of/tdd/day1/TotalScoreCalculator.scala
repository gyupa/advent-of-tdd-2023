package advent.of.tdd.day1

import advent.of.tdd.utils.FileReader

class TotalScoreCalculator(
                            val fileReader: FileReader,
                            val inputDataValidator: InputDataValidator,
                            val calibrationValueAggregator: CalibrationValueAggregator
                          ) {
  def calculateScore(fileName: String): Either[String, Int] = {
    val calibrationStringsOrError = fileReader.readLinesFromFile(fileName)

    calibrationStringsOrError match {
      case Left(errorMessage) => Left(errorMessage)
      case Right(calibrationStrings) =>
        if (inputDataValidator.validateInput(calibrationStrings))
          Right(calibrationValueAggregator.calculateTotalCalibrationScore(calibrationStrings))
        else
          Left("Invalid input")
    }
  }

}

object TotalScoreCalculator {
  def apply(
             fileReader: FileReader,
             inputDataValidator: InputDataValidator,
             calibrationValueAggregator: CalibrationValueAggregator) =
    new TotalScoreCalculator(fileReader, inputDataValidator, calibrationValueAggregator)
}