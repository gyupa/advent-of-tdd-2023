package advent.of.tdd.day1

class CalibrationValueAggregator(val calibrationValueExtractor: CalibrationValueExtractor) {

  /**
   * Calculates total calibration score from input.
   *
   * @param calibrationRecords The assumption is that each line of the input contains at least 1 digit.
   * @return
   */
  def calculateTotalCalibrationScore(calibrationRecords: Seq[String]): Int = {
    calibrationRecords.map(
      calibrationValueExtractor.readCalibrationValue
    ).sum
  }

}

object CalibrationValueAggregator {
  def apply(calibrationValueExtractor: CalibrationValueExtractor): CalibrationValueAggregator =
    new CalibrationValueAggregator(calibrationValueExtractor)
}
