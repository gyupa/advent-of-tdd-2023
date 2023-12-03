package advent.of.tdd.day2

object Day2SolutionApp {

  def main(args: Array[String]): Unit = {
    val evaluator = new GameEvaluator()

    val taskAResult = evaluator.calculateSumOfPossibleGameIds("day2_input.txt")

    println(s"Task A result: $taskAResult")

    val taskBResult = evaluator.calculatePowerOfMinumumNumberOfBalls("day2_input.txt")
    println(s"Task B result: $taskBResult")

  }
}
