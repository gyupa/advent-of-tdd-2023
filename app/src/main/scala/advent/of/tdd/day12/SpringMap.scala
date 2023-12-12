package advent.of.tdd.day12

case class SpringMap(
                      springRows: Seq[SpringRow]
                    ) {
  def calculateSumOfCombinations: Int = {
    springRows.map(_.getPotentialCombinations).sum
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
