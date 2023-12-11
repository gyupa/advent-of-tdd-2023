package advent.of.tdd.day11

import advent.of.tdd.utils.FileReaderImpl

object Day11SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day11_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        val spaceMap = SpaceMap.constructFromLines(fileContents)

        println(spaceMap.expandSpace.calculateSumOfDistances)
        println(spaceMap.calculateSumOfDistancesExpanded)
    }

  }

}
