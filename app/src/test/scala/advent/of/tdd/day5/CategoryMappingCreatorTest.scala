package advent.of.tdd.day5

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper


@RunWith(classOf[JUnitRunner])
class CategoryMappingCreatorTest extends AnyFlatSpec {

  "Category mapping creator" should "create mapping function for 1st example" in {
    val mappingLines: Seq[String] =
      Seq(
        "seed-to-soil map:",
        "50 98 2",
        "52 50 48"
      )

    val mappingFunction: Long => Long = CategoryMappingCreator.readMappingFromLines(mappingLines).mappingFunction

    mappingFunction(1) should be (1)
    mappingFunction(49) should be (49)
    mappingFunction(50) should be (52)
    mappingFunction(97) should be (99)
    mappingFunction(98) should be (50)
  }

}
