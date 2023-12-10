package advent.of.tdd.day8

import advent.of.tdd.utils.FileReaderImpl

object Day8SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day8_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        val desertMap = DesertMap.createMapFromLines(fileContents)

        println(desertMap.getNumberOfStepsFromStartToEndFromSingleStartNode)

    }

  }

}
