package advent.of.tdd.day14

import advent.of.tdd.utils.Matrix

import scala.:+
import scala.annotation.tailrec

case class Platform(
                     matrix: Matrix[Char]
                   ) {
  @tailrec
  final def roll(i: Int, states: Seq[Set[(Int, Int)]] = Seq.empty): Platform = {
    if (i == 0)
      this
    else if (states.contains(state)) {
      println(s"REPEATING STATE!!! - $i iterations left - $calculateScore")
      println(s"index: ${states.indexOf(state)}")
      val newPlatform = rollNorth.rollWest.rollSouth.rollEast
      newPlatform.roll(i - 1, states :+ state)
    }
    else {
      //println(matrix)
      //println()
      val newPlatform = rollNorth.rollWest.rollSouth.rollEast
      //println("+++++++++++++")
      //println(newPlatform.matrix)
      //println("------------")
      if (isIdentical(newPlatform)) {
        println(s"IDENTICAL!!! - $i iterations left")
        this
      } else {
        if (i % 1000 == 0) println(s"$i iterations left")
        newPlatform.roll(i - 1, states :+ state)
      }
    }
  }

  def state: Set[(Int, Int)] = roundedRockPositions

  def roundedRockPositions: Set[(Int, Int)] = {
    matrix.rows.indices.flatMap(index => matrix.rows(index).zipWithIndex.filter(_._1 == 'O').map(pair => (index, pair._2))).toSet
  }

  private def isIdentical(otherPlatform: Platform) = {
    roundedRockPositions == otherPlatform.roundedRockPositions
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
    val newMatrix = Matrix(Matrix.columnsToRows(matrix.columns.map(rollRoundedToTheLeft)))
    Platform(newMatrix)
  }

  def rollWest: Platform = {
    val newMatrix = Matrix(matrix.rows.map(rollRoundedToTheLeft))
    Platform(newMatrix)
  }

  def rollSouth: Platform = {
    val newMatrix = Matrix(Matrix.columnsToRows(matrix.columns.map(_.reverse).map(rollRoundedToTheLeft).map(_.reverse)))
    Platform(newMatrix)
  }

  def rollEast: Platform = {
    val newMatrix = Matrix(matrix.rows.map(_.reverse).map(rollRoundedToTheLeft).map(_.reverse))
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