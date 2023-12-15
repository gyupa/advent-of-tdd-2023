package advent.of.tdd.day14

import advent.of.tdd.utils.Matrix

case class Platform(
                     matrix: Matrix[Char]
                   ) {
  def calculateScore: Int = {
    //matrix.columns.head.zipWithIndex.filter(_._1 == '0').map(pair => (matrix.numberOfRows - pair._2)).sum
    matrix.columns.map {
      column =>
        val roundedRockIndices: Seq[Int] = column.zipWithIndex.filter(_._1 == 'O').map(_._2)
        val cubeShapedRockIndices: Seq[Int] = column.zipWithIndex.filter(_._1 == '#').map(_._2)
        println(s"new column: $column")
        println(s"  round: $roundedRockIndices, cube: $cubeShapedRockIndices")

        val scoreBeforeLastCube = cubeShapedRockIndices.indices.map(
          indexofCurrentCube => {
            val countRoundedFrom = if (indexofCurrentCube == 0) 0 else cubeShapedRockIndices(indexofCurrentCube - 1) + 1
            val countRoundedTo = cubeShapedRockIndices(indexofCurrentCube)
            println(s"  from: $countRoundedFrom, to: $countRoundedTo")
            val numRounded = roundedRockIndices.count(index => index >= countRoundedFrom && index < countRoundedTo)
            val maxScore =
              if (indexofCurrentCube == 0)
                column.size
              else
                column.size - cubeShapedRockIndices(indexofCurrentCube - 1) - 1
            println(s"  numrounded: $numRounded, maxscore: $maxScore")
            (0 until numRounded).map(maxScore - _).sum
          }
        ).sum

        val scoreAfterLastCube = {
          val numberOfRoundInTheEnd = roundedRockIndices.count(_ > cubeShapedRockIndices.lastOption.getOrElse(0))
          val maxScore =
            if (cubeShapedRockIndices.isEmpty)
              matrix.columns.head.size
            else
              matrix.columns.head.size - cubeShapedRockIndices.last - 1
          (0 until numberOfRoundInTheEnd).map(maxScore - _).sum
        }

        println(s"  score before last cube: $scoreBeforeLastCube, score after: $scoreAfterLastCube, total: ${scoreBeforeLastCube + scoreAfterLastCube}")
        scoreBeforeLastCube + scoreAfterLastCube
    }.sum
  }

}

object Platform {
  def readFromLines(lines: Seq[String]): Platform = {
    val matrix =
      Matrix(
        lines.map(line => line.indices.map(line(_)))
      )

    println(matrix)
    Platform(matrix)
  }
}