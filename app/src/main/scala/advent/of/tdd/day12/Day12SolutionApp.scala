package advent.of.tdd.day12

import advent.of.tdd.utils.FileReaderImpl

object Day12SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day12_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println(SpringMap.readFromLines(fileContents).calculateSumOfUnfoldedCombinations)

    }

  }
}
