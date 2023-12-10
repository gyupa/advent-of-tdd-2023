package advent.of.tdd.day8

case class Instructions(instructions: Seq[Direction.Value]) {

  private val size = instructions.size

  def getDirection(stepNumber: Long): Direction.Value = {
    instructions((stepNumber % size).toInt)
  }
}
