package advent.of.tdd.day13

case class MirrorMap(
                      rows: Seq[String],
                      columns: Seq[String]
                    ) {
  def calculateTotalScoreOfMirrors: Int = {
    MirrorMap.findPositionsOfMirrors(rows).headOption.getOrElse(0) * 100 + MirrorMap.findPositionsOfMirrors(columns).headOption.getOrElse(0)
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
          println(s"position: $i, is this symmetric: ${columnsOrRows.takeRight((columnsOrRows.size - i) * 2)}")
          columnsOrRows.takeRight((columnsOrRows.size - i) * 2)
        }
      )
  }

  def findPositionsOfMirrors(columnsOrRows: Seq[String]): Seq[Int] = {
    columnsOrRows.indices.filter(i => isMirrorAfterRow(i + 1, columnsOrRows)).map(_ + 1)
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

}
