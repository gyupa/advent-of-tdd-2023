package advent.of.tdd.day11

import scala.math.abs

case class SpaceMap(
                     mapAsMatrix: Seq[Seq[Char]],
                     ratio: Long = 1000000
                   ) {

  private val emptySpaceRowIndices: Seq[Int] = mapAsMatrix.indices.filter(
    i => mapAsMatrix(i).forall(_ == '.')
  )

  private val emptySpaceColumnIndices: Seq[Int] = mapAsMatrix.head.indices.filter(
    i => mapAsMatrix.forall(_(i) == '.')
  )

  def calculateSumOfDistancesExpanded: Long = {
    val galaxies = findGalaxies.toSeq

    val sumTimes2: Long = {
      val allNumbers: Seq[Long] = for (galaxy1 <- galaxies; galaxy2 <- galaxies) yield {
        val distance: Long = calculateDistanceAssumingLargeExpansion(galaxy1, galaxy2)
        println(s"distance between $galaxy1 and $galaxy2 is $distance")
        distance
      }

      allNumbers.sum
    }
    sumTimes2 / 2
  }


  def calculateDistanceAssumingLargeExpansion(galaxy1: Galaxy, galaxy2: Galaxy): Long = {
    val normalDistance = abs(galaxy2.position.column - galaxy1.position.column) +
      abs(galaxy2.position.row - galaxy1.position.row)
    val numberEmptySpaceRows =
      if (galaxy2.position.row > galaxy1.position.row)
        (1 to (galaxy2.position.row - galaxy1.position.row)).count(i => emptySpaceRowIndices.contains(i + galaxy1.position.row))
      else
        (1 to (galaxy1.position.row - galaxy2.position.row)).count(i => emptySpaceRowIndices.contains(i + galaxy2.position.row))

    val numberEmptySpaceColumns =
      if (galaxy2.position.column > galaxy1.position.column)
        (1 to (galaxy2.position.column - galaxy1.position.column)).count(i => emptySpaceColumnIndices.contains(i + galaxy1.position.column))
      else
        (1 to (galaxy1.position.column - galaxy2.position.column)).count(i => emptySpaceColumnIndices.contains(i + galaxy2.position.column))

    println(s"numberEmptySpaceRows: $numberEmptySpaceRows, numberEmptySpaceColumns: $numberEmptySpaceColumns, $emptySpaceColumnIndices")

    normalDistance - numberEmptySpaceRows - numberEmptySpaceColumns + ratio * numberEmptySpaceRows.toLong + ratio * numberEmptySpaceColumns.toLong
  }


  def calculateSumOfDistances: Int = {
    val galaxies = findGalaxies.toSeq

    val sumTimes2: Int = {
      val allNumbers = for (galaxy1 <- galaxies; galaxy2 <- galaxies) yield {
        val distance = abs(galaxy2.position.column - galaxy1.position.column) +
          abs(galaxy2.position.row - galaxy1.position.row)
        println(s"distance between $galaxy1 and $galaxy2 is $distance")
        distance
      }

      allNumbers.sum
    }
    sumTimes2 / 2
  }

  def expandSpace: SpaceMap = {
    val rowsExpanded =
      mapAsMatrix.flatMap(
        row =>
          if (row.forall(_ == '.'))
            Seq(row, row)
          else
            Seq(row)
      )

    val columnsExpanded: Seq[Seq[Char]] = {
      for (row <- rowsExpanded) yield {
        val newRow: Seq[Char] =
          (for (i <- mapAsMatrix.head.indices) yield {
            if (rowsExpanded.forall(_(i) == '.'))
              Seq('.', '.')
            else
              Seq(row(i))
          }).flatten
        newRow
      }
    }
    SpaceMap(columnsExpanded)
  }

  def findGalaxies: Set[Galaxy] = {
    (for (row <- mapAsMatrix.indices; column <- mapAsMatrix.head.indices) yield {
      if (mapAsMatrix(row)(column) == '#')
        Some(Galaxy(MapPosition(row, column)))
      else
        None
    }).filter(_.isDefined).map(_.get).toSet
  }
}

object SpaceMap {
  def constructFromLines(lines: Seq[String]): SpaceMap = {
    SpaceMap(lines.map(_.toCharArray.toSeq))
  }
}
