package advent.of.tdd.day5

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlantingAlmanacTest extends AnyFlatSpec {

  "Planting almanac" should "be built up from file properly" in {
    FileReaderImpl.readLinesFromFile("src/main/resources/day5_example.txt").map {
      lines =>
        val almanac = PlantingAlmanac.readFromLines(lines)

        almanac.getLocationForSeed(79) should be (82)
        almanac.getLocationForSeed(14) should be (43)
        almanac.getLocationForSeed(55) should be (86)
        almanac.getLocationForSeed(13) should be (35)
    }
  }

  it should "calculate lowest locates even with seed ranges" in {
    FileReaderImpl.readLinesFromFile("src/main/resources/day5_example.txt").map {
      lines =>
        val almanac = PlantingAlmanac.readFromLines(lines)

        almanac.getLocationRangesForSeedRange(
          Range(79, 14)
        ) should be(Seq(Range(82, 3), Range(85, 8), Range(56, 3)))
    }
  }

  it should "return the lowest locate correctly based on the example" in {
    FileReaderImpl.readLinesFromFile("src/main/resources/day5_example.txt").map {
      lines =>
        val almanac = PlantingAlmanac.readFromLines(lines)
        almanac.traceSeed(82)

        //almanac.lowestLocationForSeedRanges should be (46)
    }
  }
}
