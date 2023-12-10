package advent.of.tdd.day9

import scala.annotation.tailrec

case class Sequence(
                     values: Seq[Long]
                   ) {
  def findLastElementsOfDiffSequences: Seq[Long] = {
    findLastElementsRecursively(values, Seq(values.last))
  }

  @tailrec
  private def findLastElementsRecursively(currentSequence: Seq[Long], lastValuesSoFar: Seq[Long]): Seq[Long] = {
    if (currentSequence.isEmpty)
      lastValuesSoFar
    else if (currentSequence.forall(_ == 0)) {
      lastValuesSoFar
    }
    else {
      val diffs: Seq[Long] =
        for (i <- currentSequence.indices.dropRight(1)) yield {
          currentSequence(i + 1) - currentSequence(i)
        }
      findLastElementsRecursively(diffs, lastValuesSoFar :+ diffs.last)
    }
  }

  def calculateNextElement: Long = {
    findLastElementsOfDiffSequences.sum
  }

  def calculatePreviousElement: Long = {
    val firstValues: Seq[Long] = findFirstElementsOfDiffSequences
    println(s"first values: $firstValues")
    firstValues.foldRight(0.toLong)((value: Long, accumulator: Long) => {
      value - accumulator
    })
  }

  def findFirstElementsOfDiffSequences: Seq[Long] = {
    findFirstElementsRecursively(values, Seq(values.head))
  }

  @tailrec
  private def findFirstElementsRecursively(currentSequence: Seq[Long], firstValuesSoFar: Seq[Long]): Seq[Long] = {
    if (currentSequence.isEmpty)
      firstValuesSoFar
    else if (currentSequence.forall(_ == 0)) {
      firstValuesSoFar
    }
    else {
      val diffs: Seq[Long] =
        for (i <- currentSequence.indices.dropRight(1)) yield {
          currentSequence(i + 1) - currentSequence(i)
        }
      findFirstElementsRecursively(diffs, firstValuesSoFar :+ diffs.head)
    }
  }
}

object Sequence {
  def createSequenceFromLine(line: String): Sequence = {
    Sequence(line.split(" +").map(_.toLong).toSeq)
  }
}
