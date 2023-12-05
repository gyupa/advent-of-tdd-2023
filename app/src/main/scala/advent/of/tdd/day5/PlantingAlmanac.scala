package advent.of.tdd.day5

import scala.collection.immutable.Seq

case class PlantingAlmanac(
                            seedsRanges: Seq[Range],
                            categoryMappings: Seq[CategoryMapping]
                          ) {

  def applyMappingOnRange(categoryRange: Range, rangeTransformations: Seq[RangeTransformation]): Seq[Range] = {
    println(s"Apply mapping $rangeTransformations on $categoryRange")
    if (rangeTransformations.isEmpty)
      Seq(categoryRange)
    else {
      val targetRanges: Seq[Range] = categoryRange.applyTransformation(rangeTransformations.head)

      targetRanges.map(
        targetRange => applyMappingOnRange(targetRange, rangeTransformations.tail)
      ).flatten
      //applyMappingOnRange(seedRange, rangeTransformations.tail, acc ++ seedRange.applyTransformation(rangeTransformations.head))
    }
  }

  def lowestLocationForSeedRanges = {
    seedsRanges.map(getLocationRangesForSeedRange).flatten.map(_.lowerBound).min
  }

  def traceSeed(seed: Long) = {
    getLocationRangesForSeedRange(Range(seed, 1))
  }

  def getLocationRangesForSeedRange(seedRange: Range): Seq[Range] = {
    val soilRanges: Seq[Range] = applyMapping(Seq(seedRange), "seed-to-soil")
    val fertilizerRanges = applyMapping(soilRanges, "soil-to-fertilizer")
    val waterRanges = applyMapping(fertilizerRanges, "fertilizer-to-water")
    val lightRanges = applyMapping(waterRanges, "water-to-light")
    val temperatureRanges = applyMapping(lightRanges, "light-to-temperature")
    val humidityRanges = applyMapping(temperatureRanges, "temperature-to-humidity")
    applyMapping(humidityRanges, "humidity-to-location")
  }

  private def applyMapping(ranges: Seq[Range], mappingName: String) = {
    println(s"applying mapping $mappingName")
    ranges.map(
      applyMappingOnRange(_, mappingsByName(mappingName).head.rangeTransformations)
    ).flatten
  }

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
    val seedsWithRanges: Seq[Long] = {
      val numberPattern = "(\\d+)".r
      numberPattern.findAllMatchIn(lines.head).map(_.group(1).toLong).toSeq
    }

    val seedRanges: Seq[Range] =
      seedsWithRanges.grouped(2).toSeq.map(
        pair => Range(lowerBound = pair.head, length = pair(1))
      )


    /*val numberPattern = "(\\d+)".r
    val seeds = numberPattern.findAllMatchIn(lines.head).map(_.group(1).toLong).toSeq*/

    val categoryMappings = splitMappings(lines.tail.tail)

    PlantingAlmanac(seedRanges, categoryMappings)
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
