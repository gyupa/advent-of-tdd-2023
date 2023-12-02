package advent.of.tdd.day1

trait InputDataValidator {

  val ValidDigitStrings: Seq[String] = Seq(
    "1", "2", "3", "4", "5", "6", "7", "8", "9",
    "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
  )


  def validateInput(inputLines: Seq[String]): Boolean = {
    inputLines.forall(containsDigit)
  }

  private def containsDigit(line: String) = {
    ValidDigitStrings.exists(line.contains)
  }
}
object InputDataValidatorImpl extends InputDataValidator