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

  private val ReplacementMap: Map[String, String] = Map(
    "one" -> "1",
    "two" -> "2",
    "three" -> "3",
    "four" -> "4",
    "five" -> "5",
    "six" -> "6",
    "seven" -> "7",
    "eight" -> "8",
    "nine" -> "9"
  ).withDefault(a => a)


  private def extractDigits2(calibrationString: String) = {
    val firstDigitAsWordOrNumber =
      InputDataValidatorImpl.ValidDigitStrings.map(
          digitString => digitString -> calibrationString.indexOf(digitString)
        ).filter(_._2 > -1)
        .minBy(_._2)
        ._1

    val firstDigitAsInt = ReplacementMap(firstDigitAsWordOrNumber).toInt

    val lastDigitAsWordOrNumber =
      InputDataValidatorImpl.ValidDigitStrings.map(
          digitString => digitString -> calibrationString.lastIndexOf(digitString)
        ).filter(_._2 > -1)
        .maxBy(_._2)
        ._1
    val lastDigitAsInt = ReplacementMap(lastDigitAsWordOrNumber).toInt

    10 * firstDigitAsInt + lastDigitAsInt
  }
}

object CalibrationValueExtractorImpl extends CalibrationValueExtractor
