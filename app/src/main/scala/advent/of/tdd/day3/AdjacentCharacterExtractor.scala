package advent.of.tdd.day3

class AdjacentCharacterExtractor {

  private val numberPattern = "(\\d+)".r

  def extractAdjacentCellsFromNumbersInLine(
                                             line: String,
                                             previousLine: Option[String],
                                             nextLine: Option[String]
                                           ): Seq[(Int, String)] = {
    val extractNumbersPattern = "[^\\d]*(\\d+)[^\\d]*".r

    extractNumbersPattern.findAllMatchIn(line).map {
      extractedNumberMatch =>
        val extractedNumberAsString: String = extractedNumberMatch.group(1)
        val (extractedStartPosition, extractedEndPosition) = (extractedNumberMatch.start(1), extractedNumberMatch.end(1))
        println(s"extractedGearAsString: $extractedNumberAsString, start: $extractedStartPosition, end: $extractedEndPosition")

        extractedNumberMatch.group(1).toInt ->
          extractAdjacentCharactersForNumberMatch(line, previousLine, nextLine, extractedStartPosition, extractedEndPosition)
    }
  }.toSeq

  private def extractAdjacentCharactersForNumberMatch(line: String, previousLine: Option[String], nextLine: Option[String], extractedStartPosition: Int, extractedEndPosition: Int) = {
    val adjacentCells =
      extractAdjacentCharactersFromNeighbourLine(previousLine, extractedStartPosition, extractedEndPosition) +
        extractLeftNeighbour(line, extractedStartPosition).getOrElse("") +
        extractRightNeighbour(line, extractedEndPosition).getOrElse("") +
        extractAdjacentCharactersFromNeighbourLine(nextLine, extractedStartPosition, extractedEndPosition)
    adjacentCells
  }

  private def extractLeftNeighbour(line: String, startPosition: Int) = {
    if (startPosition == 0)
      None
    else
      Some(line.charAt(startPosition - 1).toString)
  }

  private def extractRightNeighbour(line: String, endPosition: Int) = {
    if (endPosition == line.length)
      None
    else
      Some(line.charAt(endPosition).toString)
  }

  private def extractAdjacentCharactersFromNeighbourLine(optionalLine: Option[String], matchStartPosition: Int, matchEndPosition: Int) = {
    optionalLine.map { line =>
      val adjacentStartPosition = Seq(0, matchStartPosition - 1).max
      val adjacentEndPosition = Seq(line.length, matchEndPosition + 1).min

      line.substring(adjacentStartPosition, adjacentEndPosition)
    }.getOrElse("")
  }

  private def extractAdjacentNumbersForGearMatch(
                                                  line: String,
                                                  previousLine: Option[String],
                                                  nextLine: Option[String],
                                                  extractedStartPosition: Int,
                                                  extractedEndPosition: Int
                                                ): Seq[Int] = {

    val prevLineNeighbouringNumbers: Seq[Int] =
      extractAdjacentNumbersFromNeighbourLines(previousLine, extractedStartPosition, extractedEndPosition)

    val nextLineNeighbouringNumbers: Seq[Int] =
      extractAdjacentNumbersFromNeighbourLines(nextLine, extractedStartPosition, extractedEndPosition)

    val currentLineNeighbouringNumbers: Seq[Int] = {
      numberPattern.findAllMatchIn(line).filter {
        numberMatch =>
          (numberMatch.end == extractedStartPosition) ||
            (numberMatch.start == extractedEndPosition)

      }.toSeq.map(_.group(1).toInt)

    }
    prevLineNeighbouringNumbers ++ currentLineNeighbouringNumbers ++ nextLineNeighbouringNumbers
  }

  private def extractAdjacentNumbersFromNeighbourLines(
                                                        neighbourLine: Option[String],
                                                        extractedStartPosition: Int,
                                                        extractedEndPosition: Int
                                                      ) = {
    neighbourLine.map(
      prevLine => numberPattern.findAllMatchIn(prevLine).filter {
        numberMatch =>
          val range = Range.inclusive(extractedStartPosition, extractedEndPosition)
          range.contains(numberMatch.start) || range.contains(numberMatch.end) ||
            (numberMatch.start < range.start && numberMatch.end > range.end)
      }.toSeq.map(_.group(1).toInt)
    ).getOrElse(Seq.empty)
  }

  def extractAdjacentNumbersForGearCandidatesInLine(
                                                     line: String,
                                                     previousLine: Option[String],
                                                     nextLine: Option[String]
                                                   ): Seq[Seq[Int]] = {
    val extractGearsPattern = "(\\*)".r

    extractGearsPattern.findAllMatchIn(line).map {
      extractedGearsMatch =>
        val extractedGearAsString: String = extractedGearsMatch.group(1)
        val (extractedStartPosition, extractedEndPosition) = (extractedGearsMatch.start(1), extractedGearsMatch.end(1))
        println(s"extractedGearAsString: $extractedGearAsString, start: $extractedStartPosition, end: $extractedEndPosition")

        extractAdjacentNumbersForGearMatch(line, previousLine, nextLine, extractedStartPosition, extractedEndPosition)
    }.toSeq
  }
}
