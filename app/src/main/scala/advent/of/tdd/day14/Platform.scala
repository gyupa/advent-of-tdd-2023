package advent.of.tdd.day14

import advent.of.tdd.utils.Matrix

import scala.annotation.tailrec

case class Platform(
                     matrix: Matrix[Char]
                   ) {
  val roundedRockPositions: Set[(Int, Int)] = {
    matrix.rows.indices.flatMap(index => matrix.rows(index).zipWithIndex.filter(_._1 == 'O').map(pair => (index, pair._2))).toSet
  }

  private def rollRoundedToTheLeft(sequence: Seq[Char]): Seq[Char] = {
    getNewSequenceRecursively(sequence, Seq.empty)
  }

  @tailrec
  private def getNewSequenceRecursively(remainingSequence: Seq[Char], currentOutput: Seq[Char]): Seq[Char] = {
    if (remainingSequence.isEmpty)
      currentOutput
    else {
      val nextRoundRockIndex: Int = remainingSequence.indexOf('O')
      val nextCubeRockIndex: Int = remainingSequence.indexOf('#')
      val (newCharacter, remSeq) =
        if (nextRoundRockIndex == -1)
          (remainingSequence.head, remainingSequence.tail)
        else if (nextCubeRockIndex == -1)
          ('O', remainingSequence.zipWithIndex.filterNot(_._2 == nextRoundRockIndex).map(_._1))
        else if (nextRoundRockIndex < nextCubeRockIndex)
          ('O', remainingSequence.zipWithIndex.filterNot(_._2 == nextRoundRockIndex).map(_._1))
        else
          (remainingSequence.head, remainingSequence.tail)
      getNewSequenceRecursively(remSeq, currentOutput :+ newCharacter)
    }
  }

  def rollNorth: Platform = {
    println(matrix)
    println()
    val newMatrix = Matrix(Matrix.columnsToRows(matrix.columns.map(rollRoundedToTheLeft)))
    println(newMatrix)
    Platform(newMatrix)
  }

  def calculateScore: Int = {
    matrix.rows.indices.map(index => matrix.rows(index).count(_ == 'O') * (matrix.numberOfRows - index)).sum
  }


}

object Platform {

  def readFromLines(lines: Seq[String]): Platform = {
    val matrix =
      Matrix(
        lines.map(line => line.indices.map(line(_)))
      )

    Platform(matrix)
  }


}