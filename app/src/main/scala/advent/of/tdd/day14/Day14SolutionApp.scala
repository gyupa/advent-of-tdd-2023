package advent.of.tdd.day14

import advent.of.tdd.utils.FileReaderImpl

object Day14SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day14_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println(Platform.readFromLines(lines = fileContents).calculateScore)
    }

  }
}
