package advent.of.tdd.day13

import advent.of.tdd.utils.FileReaderImpl

object Day13SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day13_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        val fileAsString = fileContents.mkString("\r\n")
        val mirrorMapsAsString = fileAsString.split("\\r\\n\\r\\n").toSeq
        //println(mirrorMapsAsString.last)
        val mirrorMapScores: Seq[Int] =
          mirrorMapsAsString.map {
            mirrorMapAsString =>
              println()
              println(mirrorMapAsString)
              val lines: Seq[String] = mirrorMapAsString.split("\r\n").toSeq
              //val score = MirrorMap.readFromLines(lines).calculateTotalScoreOfMirrors
              val score = MirrorMap.readFromLines(lines).calculateRepairedTotalScoreOfMirrors
              println(s"score: $score")
              score
          }
        println(mirrorMapScores.sum)
      // 5244 -> too low
      // 5545 -> too low
    }

  }
}
