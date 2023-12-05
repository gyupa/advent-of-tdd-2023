package advent.of.tdd.day5

case class Range(
                  lowerBound: Long,
                  length: Long
                ) {
  def minus(otherRange: Range): Seq[Range] = {
    if (isOutsideOf(otherRange))
      Seq(this)
    else if (contains(otherRange) && lowerBound == otherRange.lowerBound && upperBound == otherRange.upperBound)
      Seq()
    else if (contains(otherRange) && lowerBound == otherRange.lowerBound)
      Seq(
        Range(otherRange.upperBound + 1, upperBound - otherRange.upperBound)
      )
    else if (contains(otherRange) && upperBound == otherRange.upperBound)
      Seq(
        Range(lowerBound, otherRange.lowerBound - lowerBound)
      )
    else if (contains(otherRange))
      Seq(
        Range(lowerBound, otherRange.lowerBound - lowerBound),
        Range(otherRange.upperBound + 1, upperBound - otherRange.upperBound)
      )
    else if (otherRange.contains(this))
      Seq()
    else if (lowerBound < otherRange.lowerBound)
      Seq(
        Range(lowerBound, otherRange.lowerBound - lowerBound)
      )
    else // upperbound > otherRange.upperBound
      Seq(
        Range(otherRange.upperBound + 1, upperBound - otherRange.upperBound)
      )
  }

  def intersect(otherRange: Range): Option[Range] = {
    if (isOutsideOf(otherRange))
      None
    else {
      val newLowerBound = Math.max(lowerBound, otherRange.lowerBound)
      Some(Range(newLowerBound, Math.min(upperBound, otherRange.upperBound) - newLowerBound + 1))
    }
  }

  def getRangesNotTransformed(range: Range, transformations: Seq[RangeTransformation]): Seq[Range] = {
    if (transformations.isEmpty)
      Seq(range)
    else {
      println(s"removing transformation sources $transformations from range $range")
      val remainingRanges: Seq[Range] = range.minus(transformations.head.sourceRange).filterNot(_.length == 0)
      println(s"removing transformation source result: $remainingRanges")
      remainingRanges.flatMap(getRangesNotTransformed(_, transformations.tail))
    }
  }

  private def getRangesTransformed(range: Range, transformations: Seq[RangeTransformation]) = {
    transformations.map(transformation =>
      range.intersect(transformation.sourceRange).map(_.applyTransformation(transformation))
    ).filter(_.isDefined).flatMap(_.get)
  }

  def applyMultipleTransformations(transformations: Seq[RangeTransformation]): Seq[Range] = {
    getRangesNotTransformed(this, transformations) ++ getRangesTransformed(this, transformations)
  }

  def contains(range: Range): Boolean = {
    lowerBound <= range.lowerBound && upperBound >= range.upperBound
  }

  private def isOutsideOf(range: Range): Boolean = {
    upperBound < range.lowerBound || lowerBound > range.upperBound
  }

  private val upperBound = lowerBound + length - 1

  def applyTransformation(transformation: RangeTransformation): Seq[Range] = {
    if (transformation.sourceRange.contains(this))
      Seq(Range(lowerBound + (transformation.targetRange.lowerBound - transformation.sourceRange.lowerBound), length))
    else if (isOutsideOf(transformation.sourceRange))
      Seq(this)
    else if (contains(transformation.sourceRange))
      Seq(
        Range(lowerBound, transformation.sourceRange.lowerBound - lowerBound),
        Range(transformation.targetRange.lowerBound, transformation.sourceRange.length),
        Range(transformation.sourceRange.upperBound + 1, upperBound - transformation.sourceRange.upperBound)
      )
    else if (lowerBound < transformation.sourceRange.lowerBound)
      Seq(
        Range(lowerBound, transformation.sourceRange.lowerBound - lowerBound),
        Range(transformation.targetRange.lowerBound, upperBound - transformation.sourceRange.lowerBound + 1)
      )
    else // (lowerBound > transformation.sourceRange.lowerBound)
      Seq(
        Range(transformation.targetRange.lowerBound + lowerBound - transformation.sourceRange.lowerBound, transformation.sourceRange.upperBound - lowerBound + 1),
        Range(transformation.sourceRange.upperBound + 1, upperBound - transformation.sourceRange.upperBound)
      )
  }

}

case class RangeTransformation(
                                sourceRange: Range,
                                targetRange: Range
                              )
