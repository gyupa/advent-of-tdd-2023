package advent.of.tdd.day4

import advent.of.tdd.utils.FileReaderImpl

object Day4SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("main/resources/day4_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println(EvaluateCards.calculateTotalScore(fileContents))

    }
  }

}
