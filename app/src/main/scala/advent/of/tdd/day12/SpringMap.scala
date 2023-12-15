package advent.of.tdd.day12

case class SpringMap(
                      springRows: Seq[SpringRow]
                    ) {
  def calculateSumOfCombinations: Long = {
    springRows.map(_.getPotentialCombinations).sum
  }

  def calculateSumOfUnfoldedCombinations(times: Int = 5): Long = {
    val rowsWithUnfoldedRows: Seq[(SpringRow, SpringRow)] = springRows.map(springRow => springRow -> springRow.unfold(2))
    rowsWithUnfoldedRows.map {
      pair =>
        val originalCombinations = pair._1.getPotentialCombinations
        val combinationsAfterUnfoldingTwice = pair._2.getPotentialCombinations
        val ratio: Double = combinationsAfterUnfoldingTwice.toDouble / originalCombinations
        //val combinationsAfterUnfoldingNTimes = ratio * ratio * ratio * ratio * originalCombinations.toLong

        val combinationsAfterUnfoldingThreeTimes = pair._1.unfold(3).getPotentialCombinations
        val newRatio = combinationsAfterUnfoldingThreeTimes.toDouble / combinationsAfterUnfoldingTwice
        if (newRatio != ratio)
          println(s"Found undexpected diff: $newRatio vs $ratio ($combinationsAfterUnfoldingThreeTimes)")


        val ratioIncrease = newRatio - ratio

        //val combinationsAfterUnfoldingNTimes = math.pow(ratio, times - 1).toLong * originalCombinations.toLong
        val combinationsAfterUnfoldingNTimes = ratio.toLong * (ratio + ratioIncrease) * (ratio + ratioIncrease * 2) * (ratio + ratioIncrease * 3) * originalCombinations
        println(s"Found $combinationsAfterUnfoldingTwice combinations instead of $originalCombinations after unfolding, ratio: $ratio, final: $combinationsAfterUnfoldingNTimes")


        val combinationsAfterUnfolding4Times = pair._1.unfold(4).getPotentialCombinations
        val ratio3 = combinationsAfterUnfolding4Times.toDouble / combinationsAfterUnfoldingThreeTimes
        if (ratio3 != newRatio)
          println(s"Found unexpected diff: $ratio3 vs $newRatio vs $ratio ($combinationsAfterUnfolding4Times)")

        val combinationsAfterUnfolding5Times = pair._1.unfold().getPotentialCombinations
        val ratio4 = combinationsAfterUnfolding5Times.toDouble / combinationsAfterUnfolding4Times
        if (ratio4 != ratio3)
          println(s"Found unexpected diff: $ratio4 vs $ratio3 vs $newRatio vs $ratio ($combinationsAfterUnfolding5Times)")

        combinationsAfterUnfoldingNTimes.toLong
    }.sum
  }

}

object SpringMap {
  def readFromLines(lines: Seq[String]): SpringMap = {
    SpringMap(
      lines.map {
        line =>
          val (rowString, damagedSpringsAsString) = {
            val splitStr = line.split(" ")
            (splitStr(0), splitStr(1))
          }
          SpringRow(
            rowString, damagedSpringsAsString.split(",").map(_.toInt)
          )
      }
    )
  }
}
