package advent.of.tdd.day12

case class SpringRow(
                      rowString: String,
                      damagedSprings: Seq[Int]
                    ) {

  def unfold(times: Int = 5): SpringRow = {
    SpringRow(
      rowString = (rowString + "?") * (times - 1) + rowString,
      damagedSprings = (1 to times).flatMap(_ => damagedSprings)
    )
  }

  def getPotentialCombinations: Long = {
    applyDamagedSpringsOnString(rowString, damagedSprings, 0, 0)
  }

  private def applyDamagedSpringsOnString(originalString: String, remainingDamagedSprings: Seq[Int], currentNumberOfCombinations: Long, level: Int): Int = {
    if (remainingDamagedSprings.isEmpty && originalString.contains('#')) {
      //println(" " * level + "NOT a solution! (no more replacement to apply and remaining string contains #)")
      0
    } else if (remainingDamagedSprings.isEmpty) {
      //println(" " * level + "Found solution")
      1
    } else {
      val currentDamagedSprings: String = "#" * remainingDamagedSprings.head
      val substringWhereCurrentDamagedStringNeedsToBePlaced = originalString.dropRight(
        remainingDamagedSprings.tail.sum + remainingDamagedSprings.tail.size
      )
      //println(" " * level + s"Searching for $currentDamagedSprings in $substringWhereCurrentDamagedStringNeedsToBePlaced, whole: $originalString")

      substringWhereCurrentDamagedStringNeedsToBePlaced.indices.filter {
        (i: Int) =>
          //println(" " * level + s"str: $substringWhereCurrentDamagedStringNeedsToBePlaced, index: $i, length: ${currentDamagedSprings.length}")
          (i + currentDamagedSprings.length) <= substringWhereCurrentDamagedStringNeedsToBePlaced.length &&
            substringWhereCurrentDamagedStringNeedsToBePlaced.substring(i, i + currentDamagedSprings.length).forall(Seq('?', '#').contains(_)) &&
            substringWhereCurrentDamagedStringNeedsToBePlaced.substring(0, i).forall(Seq('?', '.').contains(_)) &&
            (originalString.length == i + currentDamagedSprings.length ||
              Seq('?', '.').contains(originalString(i + currentDamagedSprings.length)))
      }.map {
        indexThatFits =>
          //println(" " * level + s"Found index $indexThatFits that fits")

          applyDamagedSpringsOnString(
            originalString = originalString.drop(indexThatFits + currentDamagedSprings.length + 1),
            remainingDamagedSprings = remainingDamagedSprings.tail,
            currentNumberOfCombinations = currentNumberOfCombinations,
            level + 2
          )
      }.sum

    }
  }

}
