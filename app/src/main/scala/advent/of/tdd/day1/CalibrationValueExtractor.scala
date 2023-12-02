package advent.of.tdd.day1

import scala.collection.immutable.Seq

trait CalibrationValueExtractor {


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


  /**
   * Extracts the score from a single line.
   *
   * @param calibrationString - The assumption is that it contains at least 1 digit. (InputDataValidator ensures it.)
   * @return
   */
  def readCalibrationValue(calibrationString: String): Int = {
    val firstDigitAsInt: Int = extractFirstDigitAndConvertToInt(calibrationString)
    val lastDigitAsInt: Int = extractLastDigitAndConvertToInt(calibrationString)
    10 * firstDigitAsInt + lastDigitAsInt
  }

  private def extractLastDigitAndConvertToInt(calibrationString: String) = {
    val lastDigitAsWordOrNumber: String = findLastIndexOfDigit(calibrationString)
    ReplacementMap(lastDigitAsWordOrNumber).toInt
  }

  private def extractFirstDigitAndConvertToInt(calibrationString: String) = {
    val firstDigitAsWordOrNumber: String = findFirstIndexOfDigit(calibrationString)
    ReplacementMap(firstDigitAsWordOrNumber).toInt
  }

  private def findLastIndexOfDigit(calibrationString: String) = {
    val lastDigitAsWordOrNumber =
      InputDataValidatorImpl.ValidDigitStrings.map(
          digitString => digitString -> calibrationString.lastIndexOf(digitString)
        ).filter(_._2 > -1)
        .maxBy(_._2)
        ._1
    lastDigitAsWordOrNumber
  }

  private def findFirstIndexOfDigit(calibrationString: String) = {
    val firstDigitAsWordOrNumber =
      InputDataValidatorImpl.ValidDigitStrings.map(
          digitString => digitString -> calibrationString.indexOf(digitString)
        ).filter(_._2 > -1)
        .minBy(_._2)
        ._1
    firstDigitAsWordOrNumber
  }
}

object CalibrationValueExtractorImpl extends CalibrationValueExtractor
