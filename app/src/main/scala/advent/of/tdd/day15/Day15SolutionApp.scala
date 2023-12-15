package advent.of.tdd.day15

import advent.of.tdd.utils.FileReaderImpl

object Day15SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day15_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println("hello")
        val numbers = fileContents.head.split(",").map(AsciiHash.calculateHash).sum
        println(numbers)
        println(LensContainer(fileContents.head).calculateScore)
    }
  }
}
