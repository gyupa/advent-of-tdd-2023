package advent.of.tdd.day3

import scala.collection.immutable.Seq

class EnginePartEvaluator(val adjacentExtractor: AdjacentCharacterExtractor) {
  def calculateGearRatio(lines: Seq[String]): Int = {
    val gearRatiosByLine: Seq[Int] = extractGearPartsPerLine(lines)
    println(s"gearRatiosByLine: $gearRatiosByLine")
    gearRatiosByLine.sum
  }

  private def extractGearPartsPerLine(lines: Seq[String]) = {
    for (i <- lines.indices) yield {
      val adjNumbersForGearCandidates: Seq[Seq[Int]] = adjacentExtractor.extractAdjacentNumbersForGearCandidatesInLine(
        line = lines(i),
        previousLine = if (i == 0) None else Some(lines(i - 1)),
        nextLine = if (i == lines.length - 1) None else Some(lines(i + 1))
      )
      println(s"adjNumbersForGearCandidates: $adjNumbersForGearCandidates")
      adjNumbersForGearCandidates.filter {
        neighbourNumbers: Seq[Int] => neighbourNumbers.size == 2
      }.map {
        neighbourNumbers: Seq[Int] => neighbourNumbers.head * neighbourNumbers(1)
      }.sum
    }
  }

  def calculateSumFromEngineParts(lines: Seq[String]): Int = {
    val sumByLine: Seq[Int] = for (i <- lines.indices) yield {
      adjacentExtractor.extractAdjacentCellsFromNumbersInLine(
        line = lines(i),
        previousLine = if (i == 0) None else Some(lines(i - 1)),
        nextLine = if (i == lines.length - 1) None else Some(lines(i + 1))
      ).filterNot(
        pair => pair._2.forall(char => char == '.')
      ).map(_._1).sum
    }
    sumByLine.sum

  }
}