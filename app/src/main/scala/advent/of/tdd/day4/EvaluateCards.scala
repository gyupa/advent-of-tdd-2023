package advent.of.tdd.day4

object EvaluateCards {
  def calculateTotalScore(fileContents: Seq[String]): Int = {
    (for (line <- fileContents) yield {
      calculateScore(line)
    }).sum
  }

  private def calculateScore(line: String): Int = {
    val puzzle = line.split(": +")(1)
    val puzzleParts = puzzle.split(" \\| +")
    val (resultsString, guessesString) = (puzzleParts(0), puzzleParts(1))

    println(s"results: $resultsString, guesses: $guessesString")
    println(resultsString.split("[^\\d]+").filter(_.forall(Character.isDigit)).toSeq.toString)
    println(guessesString.split("[^\\d]+").filter(_.forall(Character.isDigit)).toSeq.toString)

    val results = resultsString.split("[^\\d]+").filter(_.forall(Character.isDigit)).map(_.toInt).toSet
    val guesses = guessesString.split("[^\\d]+").filter(_.forall(Character.isDigit)).map(_.toInt).toSet

    val matches = results.intersect(guesses).size

    println(s"matches: $matches, ${Math.pow(2, matches - 1).toInt}")

    if (matches > 0)
      Math.pow(2, matches - 1).toInt
    else
      0
  }
}
