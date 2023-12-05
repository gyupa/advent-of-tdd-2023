package advent.of.tdd.day5

case class PlantingAlmanac(
                          seeds: Seq[Long],
                          categoryMappings: Seq[CategoryMapping]
                          ) {

  val mappingsByName: Map[String, Seq[CategoryMapping]] = categoryMappings.groupBy(_.name)

  def getLocationForSeed(seed: Long): Long = {
    val soil = mappingsByName("seed-to-soil").head.mappingFunction(seed)
    val fertilizer = mappingsByName("soil-to-fertilizer").head.mappingFunction(soil)
    val water = mappingsByName("fertilizer-to-water").head.mappingFunction(fertilizer)
    val light = mappingsByName("water-to-light").head.mappingFunction(water)
    val temperature = mappingsByName("light-to-temperature").head.mappingFunction(light)
    val humidity = mappingsByName("temperature-to-humidity").head.mappingFunction(temperature)
    val location = mappingsByName("humidity-to-location").head.mappingFunction(humidity)
    location
  }

}

object PlantingAlmanac {
  def readFromLines(lines: Seq[String]): PlantingAlmanac = {
    /*val seedsWithRanges: Seq[Long] = {
      val numberPattern = "(\\d+)".r
      numberPattern.findAllMatchIn(lines.head).map(_.group(1).toLong).toSeq
    }

    val seedRangeStartAndRangeSizes: Seq[Seq[Long]] = seedsWithRanges.grouped(2).toSeq
    val seeds: Seq[Long] =
      (for (seedRangStartAndSize <- seedRangeStartAndRangeSizes) yield {
        for (i <- 0 to seedRangStartAndSize(1).toInt - 1) yield {
          seedRangStartAndSize(0) + i
        }
      }).flatten*/

    val numberPattern = "(\\d+)".r
    val seeds = numberPattern.findAllMatchIn(lines.head).map(_.group(1).toLong).toSeq

    val categoryMappings = splitMappings(lines.tail.tail)

    PlantingAlmanac(seeds, categoryMappings)
  }

  private def splitMappings(lines: Seq[String]) = {

    var categories: Seq[CategoryMapping] = Seq()
    var (currentCategory: Seq[String], nextCategories: Seq[String]) = lines.span(_ != "")

    while (nextCategories.nonEmpty) {
      categories = categories :+ CategoryMappingCreator.readMappingFromLines(currentCategory)
      val (currentCategoryNew, nextCategoriesNew) = nextCategories.tail.span(_ != "")
      currentCategory = currentCategoryNew
      nextCategories = nextCategoriesNew
    }
    categories = categories :+ CategoryMappingCreator.readMappingFromLines(currentCategory)

    categories
  }

}
