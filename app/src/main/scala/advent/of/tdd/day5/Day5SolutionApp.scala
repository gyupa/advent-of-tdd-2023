package advent.of.tdd.day5

import advent.of.tdd.utils.FileReaderImpl

object Day5SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day5_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        val pa = PlantingAlmanac.readFromLines(fileContents)

        println(pa.lowestLocationForSeedRanges)

    }
  }
}
