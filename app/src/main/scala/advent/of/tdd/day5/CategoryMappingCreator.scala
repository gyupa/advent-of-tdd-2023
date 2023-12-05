package advent.of.tdd.day5

case class CategoryMapping(
                            name: String,
                            mappingFunction: Long => Long,
                            rangeTransformations: Seq[RangeTransformation]
                          )

object CategoryMappingCreator {
  def readMappingFromLines(mappingLines: Seq[String]): CategoryMapping = {
    println(s"Reading mapping from lines: $mappingLines")
    val mappingName = mappingLines.head.dropRight(5)
    val mappingRanges =
      for (rangeLine <- mappingLines.tail) yield {
        extractMappingRangeFromLine(rangeLine)
      }

    CategoryMapping(
      name = mappingName,
      mappingFunction = {
        sourceCategory: Long =>
          mappingRanges.find(
            _.isApplicable(sourceCategory)
          ).map(
            _.applyMapping(sourceCategory)
          ).getOrElse(sourceCategory)
      },
      rangeTransformations = {
        mappingRanges.map {
          mappingRange =>
            RangeTransformation(
              sourceRange = Range(mappingRange.sourceRangeStart, mappingRange.rangeLength),
              targetRange = Range(mappingRange.targetRangeStart, mappingRange.rangeLength),
            )
        }
      }
    )
  }

  private def extractMappingRangeFromLine(line: String) = {
    val numbernPattern = "(\\d+)".r
    val numbers: Seq[Long] = numbernPattern.findAllMatchIn(line).map(_.group(1).toLong).toSeq

    MappingRange(
      sourceRangeStart = numbers(1),
      targetRangeStart = numbers.head,
      rangeLength = numbers(2)
    )
  }

}
