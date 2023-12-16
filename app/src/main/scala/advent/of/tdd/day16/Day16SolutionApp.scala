package advent.of.tdd.day16

import advent.of.tdd.utils.FileReaderImpl

object Day16SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day16_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        println("hello")
        println(Contraption.readFromLines(fileContents).findCoveredTiles)
        println("findMaxCoveredTilesIfBeamCanStartFromAnyWhere:" + Contraption.readFromLines(fileContents).findMaxCoveredTilesIfBeamCanStartFromAnyWhere)

    }
  }
}
