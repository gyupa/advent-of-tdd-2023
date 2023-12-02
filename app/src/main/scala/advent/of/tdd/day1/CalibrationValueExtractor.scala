package advent.of.tdd.day1

import scala.collection.immutable.Seq

trait CalibrationValueExtractor {

  /**
   * Extracts the score from a single line.
   *
   * @param calibrationString - The assumption is that it contains at least 1 digit. (InputDataValidator ensures it.)
   * @return
   */
  def readCalibrationValue(calibrationString: String): Int = {
    //val digits: Seq[Int] = extractDigits(calibrationString)
    //digits.head * 10 + digits.last
    extractDigits2(calibrationString)
  }

  private def extractDigits(calibrationString: String) = {
    calibrationString.toSeq.filter(_.isDigit).map(_.asDigit)
  }

  private val digits = Seq(
    "1", "2", "3", "4", "5", "6", "7", "8", "9",
    "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
  )

  private def extractDigits2(calibrationString: String) = {
    val firstDigitAsWordOrNumber =
      digits.map(
          digitString => digitString -> calibrationString.indexOf(digitString)
        ).filter(_._2 > -1)
        .minBy(_._2)
        ._1

    val firstDigitAsInt = InputDataTransformerImpl.ReplacementMap(firstDigitAsWordOrNumber).toInt

    val lastDigitAsWordOrNumber =
      digits.map(
          digitString => digitString -> calibrationString.lastIndexOf(digitString)
        ).filter(_._2 > -1)
        .maxBy(_._2)
        ._1
    val lastDigitAsInt = InputDataTransformerImpl.ReplacementMap(lastDigitAsWordOrNumber).toInt

    10 * firstDigitAsInt + lastDigitAsInt
  }
}

object CalibrationValueExtractorImpl extends CalibrationValueExtractor
