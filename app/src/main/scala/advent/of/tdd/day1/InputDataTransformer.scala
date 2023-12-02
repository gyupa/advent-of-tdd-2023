package advent.of.tdd.day1

import scala.annotation.tailrec

trait InputDataTransformer {

  val ReplacementMap: Map[String, String] = Map(
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

  val ReverseReplacementMap: Map[String, String] = ReplacementMap.map(entry => entry._1.reverse -> entry._2)

  def replaceNumbersAsWordsWithDigits(calibrationStrings: Seq[String]): Seq[String] = {
    calibrationStrings.map(replaceRecursivelyFromLeft("", _))
      .map(string => replaceRecursivelyFromRight("", string.reverse).reverse)
  }

  @tailrec
  private def replaceRecursivelyFromLeft(newPartialCalibrationString: String, remainingCalibrationString: String): String = {
    if (remainingCalibrationString.isEmpty)
      newPartialCalibrationString
    else {
      val replacementOption: Option[(String, String)] = ReplacementMap.find{
        pair => remainingCalibrationString.startsWith(pair._1)
      }

      replacementOption match {
        case Some((digitAsWord, digitAsNumber)) =>
          newPartialCalibrationString + digitAsNumber + remainingCalibrationString.drop(digitAsWord.length)
        case None =>
          replaceRecursivelyFromLeft(newPartialCalibrationString + remainingCalibrationString.head, remainingCalibrationString.tail)
      }
    }
  }

  @tailrec
  private def replaceRecursivelyFromRight(newPartialCalibrationString: String, remainingCalibrationString: String): String = {
    if (remainingCalibrationString.isEmpty)
      newPartialCalibrationString
    else {
      val replacementOption: Option[(String, String)] = ReverseReplacementMap.find {
        pair => remainingCalibrationString.startsWith(pair._1)
      }

      replacementOption match {
        case Some((digitAsWord, digitAsNumber)) =>
          newPartialCalibrationString + digitAsNumber + remainingCalibrationString.drop(digitAsWord.length)
        case None =>
          replaceRecursivelyFromRight(newPartialCalibrationString + remainingCalibrationString.head, remainingCalibrationString.tail)
      }
    }
  }

}

object InputDataTransformerImpl extends InputDataTransformer
