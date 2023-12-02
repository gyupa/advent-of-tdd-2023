package advent.of.tdd.day1

import advent.of.tdd.utils.FileReader

class TotalScoreCalculator(
                            val fileReader: FileReader,
                            val inputDataTransformer: InputDataTransformer,
                            val inputDataValidator: InputDataValidator,
                            val calibrationValueAggregator: CalibrationValueAggregator
                          ) {
  def calculateScore(fileName: String): Either[String, Int] = {
    val calibrationStringsOrError = fileReader.readLinesFromFile(fileName)

    calibrationStringsOrError match {
      case Left(errorMessage) => Left(errorMessage)
      case Right(calibrationStrings) => Right(calibrationValueAggregator.calculateTotalCalibrationScore(calibrationStrings))
    }
  }

}

object TotalScoreCalculator {
  def apply(
             fileReader: FileReader,
             inputDataTransformer: InputDataTransformer,
             inputDataValidator: InputDataValidator,
             calibrationValueAggregator: CalibrationValueAggregator) =
    new TotalScoreCalculator(fileReader, inputDataTransformer, inputDataValidator, calibrationValueAggregator)
}