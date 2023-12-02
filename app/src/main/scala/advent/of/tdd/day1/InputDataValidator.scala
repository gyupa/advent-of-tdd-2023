package advent.of.tdd.day1

trait InputDataValidator {
  def validateInput(inputLines: Seq[String]): Boolean = {
    inputLines.forall(containsDigit)
  }

  private def containsDigit(line: String) = {
    line.exists(_.isDigit)
  }
}
object InputDataValidatorImpl extends InputDataValidator