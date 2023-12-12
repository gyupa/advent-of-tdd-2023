package advent.of.tdd.day12

case class SpringRow(
                      rowString: String,
                      damagedSprings: Seq[Int]
                    ) {

  def getPotentialCombinations: Int = {
    applyDamagedSpringsOnString(rowString, damagedSprings, 0)
  }

  private def applyDamagedSpringsOnString(originalString: String, remainingDamagedSprings: Seq[Int], currentNumberOfCombinations: Int): Int = {
    if (remainingDamagedSprings.isEmpty) {
      println("Found solution")
      1
    } else {
      val currentDamagedSprings: String = "#" * remainingDamagedSprings.head
      val mostDamagedSpringsString = originalString.replace('?', '#')
      val substringWhereCurrentDamagedStringNeedsToBePlaced = mostDamagedSpringsString.dropRight(
        remainingDamagedSprings.tail.sum + remainingDamagedSprings.tail.size
      )
      println(s"Searching for $currentDamagedSprings in $substringWhereCurrentDamagedStringNeedsToBePlaced, whole: $originalString")

      substringWhereCurrentDamagedStringNeedsToBePlaced.indices.filter {
        (i: Int) =>
          println(s"  str: $substringWhereCurrentDamagedStringNeedsToBePlaced, index: $i, length: ${currentDamagedSprings.length}")
          (i + currentDamagedSprings.length) <= substringWhereCurrentDamagedStringNeedsToBePlaced.length &&
            substringWhereCurrentDamagedStringNeedsToBePlaced.substring(i, i + currentDamagedSprings.length) == currentDamagedSprings
      }.map {
        indexThatFits =>
          println(s"  Found index $indexThatFits that fits")
          if (characterAfterMatchIsNotDamaged(originalString, remainingDamagedSprings, currentDamagedSprings, indexThatFits)) {
            val wrongCharacterPosition = indexThatFits + currentDamagedSprings.size
            println(s"  next character ${originalString(wrongCharacterPosition)} as position $wrongCharacterPosition in string $originalString is wrong")
            0
          } else if (!originalString.take(indexThatFits).forall(Seq('.', '?').contains(_))) {
            println(s"  previous characters are wrong")
            0
          } else
            applyDamagedSpringsOnString(
              originalString = originalString.drop(indexThatFits + currentDamagedSprings.size + 1),
              remainingDamagedSprings = remainingDamagedSprings.tail,
              currentNumberOfCombinations = currentNumberOfCombinations
            )
      }.sum

    }
  }

  def matchIsAtTheEnd(string: String, indexThatFits: Int, size: Int) = {
    string.length == indexThatFits + size
  }

  private def characterAfterMatchIsNotDamaged(string: String, remainingDamagedSprings: Seq[Int], currentDamagedSpringsAsString: String, indexThatFits: Int) = {
    !matchIsAtTheEnd(string, indexThatFits, currentDamagedSpringsAsString.size) && !Seq('.', '?').contains(string(indexThatFits + currentDamagedSpringsAsString.size))
  }
}
