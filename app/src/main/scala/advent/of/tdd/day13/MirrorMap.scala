package advent.of.tdd.day13

case class MirrorMap(
                      rows: Seq[String],
                      columns: Seq[String]
                    ) {
  def calculateTotalScoreOfMirrors: Int = {
    val rowScore = MirrorMap.findPositionsOfMirrors(rows).headOption.getOrElse(0) * 100
    val columnScore = MirrorMap.findPositionsOfMirrors(columns).headOption.getOrElse(0)
    if (rowScore > 0 && columnScore > 0)
      println(s"found both rows and columns: $rowScore, $columnScore")
    rowScore + columnScore
  }

  def calculateRepairedTotalScoreOfMirrors: Int = {
    val rowScore = MirrorMap.findPositionsOfBrokenMirrors(rows).headOption.getOrElse(0) * 100
    val columnScore = MirrorMap.findPositionsOfBrokenMirrors(columns).headOption.getOrElse(0)
    if (rowScore > 0 && columnScore > 0)
      println(s"found both rows and columns: $rowScore, $columnScore")
    rowScore + columnScore
  }

}

object MirrorMap {
  def areColumnsOrRowsSymmetric(columnsOrRows: Seq[String]): Boolean = {
    columnsOrRows.drop(columnsOrRows.size / 2).reverse == columnsOrRows.take(columnsOrRows.size / 2)
  }

  /*
  idea for part2: calculate diff vs the proper mirror -> if diff is 1 then we have a new reflection
  but this will impact the columns as well!!
   */

  def isMirrorAfterRow(i: Int, columnsOrRows: Seq[String]): Boolean = {
    if (i == columnsOrRows.size)
      false
    else
      areColumnsOrRowsSymmetric(
        if (i <= columnsOrRows.size / 2)
          columnsOrRows.take(i * 2)
        else {
          //println(s"position: $i, is this symmetric: ${columnsOrRows.takeRight((columnsOrRows.size - i) * 2)}")
          columnsOrRows.takeRight((columnsOrRows.size - i) * 2)
        }
      )
  }

  def isBrokenMirrorAfterRow(i: Int, columnsOrRows: Seq[String]): Boolean = {
    if (i == columnsOrRows.size)
      false
    else {
      val potentialBrokenMirrorColumnsOrRows =
        if (i <= columnsOrRows.size / 2)
          columnsOrRows.take(i * 2)
        else {
          //println(s"position: $i, is this symmetric: ${columnsOrRows.takeRight((columnsOrRows.size - i) * 2)}")
          columnsOrRows.takeRight((columnsOrRows.size - i) * 2)
        }
      println(s"position: $i, is this symmetric: $potentialBrokenMirrorColumnsOrRows")
      val split: (Seq[String], Seq[String]) = potentialBrokenMirrorColumnsOrRows.splitAt(potentialBrokenMirrorColumnsOrRows.size / 2)

      calculateDifference(split._1, split._2.reverse) == 1
    }
  }

  def findPositionsOfMirrors(columnsOrRows: Seq[String]): Seq[Int] = {
    columnsOrRows.indices.filter(i => isMirrorAfterRow(i + 1, columnsOrRows)).map(_ + 1)
  }

  def findPositionsOfBrokenMirrors(columnsOrRows: Seq[String]): Seq[Int] = {
    columnsOrRows.indices.filter(i => isBrokenMirrorAfterRow(i + 1, columnsOrRows)).map(_ + 1)
  }

  def readFromLines(lines: Seq[String]): MirrorMap = {
    MirrorMap(
      rows = lines,
      columns = (0 until lines.head.length).map {
        index =>
          val currentColumnChars: Seq[String] = lines.map(_(index).toString)
          currentColumnChars.mkString
      }
    )
  }

  private def calculateDifference(seq1: Seq[String], seq2: Seq[String]) = {
    val result: Int = seq1.indices.map(i => calculateDifferenceOfStrings(seq1(i), seq2(i))).sum
    if (result == 1) println(s"found 1 difference between: $seq1 and $seq2")
    result
  }

  private def calculateDifferenceOfStrings(str1: String, str2: String): Int = {
    str1.indices.count(i => str1(i) != str2(i))
  }

}
