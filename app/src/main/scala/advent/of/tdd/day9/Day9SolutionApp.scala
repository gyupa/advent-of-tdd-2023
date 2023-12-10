package advent.of.tdd.day9

import advent.of.tdd.utils.FileReaderImpl

object Day9SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day9_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println(fileContents.map(Sequence.createSequenceFromLine).map(_.calculateNextElement).sum)
        println(fileContents.map(Sequence.createSequenceFromLine).map(_.calculatePreviousElement).sum)
    }

  }


}
