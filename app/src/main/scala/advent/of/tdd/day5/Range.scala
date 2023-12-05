package advent.of.tdd.day5

case class Range(
                  lowerBound: Int,
                  length: Int
                ) {
  private def contains(range: Range): Boolean = {
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
