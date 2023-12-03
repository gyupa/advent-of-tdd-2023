package advent.of.tdd.day3

import advent.of.tdd.utils.FileReaderImpl

object Day3SolutionApp {

  def main(args: Array[String]): Unit = {
    val evaluator = new EnginePartEvaluator(new AdjacentCharacterExtractor)

    FileReaderImpl.readLinesFromFile("day3_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println(evaluator.calculateSumFromEngineParts(fileContents))
        println(evaluator.calculateGearRatio(fileContents))
    }

  }
}
