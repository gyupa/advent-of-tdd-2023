package advent.of.tdd.day5

case class MappingRange(
                         sourceRangeStart: Long,
                         targetRangeStart: Long,
                         rangeLength: Long
                       ) {
  def isApplicable(sourceCategory: Long): Boolean = {
    sourceCategory >= sourceRangeStart && sourceCategory < sourceRangeStart + rangeLength
  }

  def applyMapping(sourceCategory: Long): Long = {
    if (isApplicable(sourceCategory))
      sourceCategory + (targetRangeStart - sourceRangeStart)
    else
      throw new IllegalArgumentException("invalid input")
  }
}
