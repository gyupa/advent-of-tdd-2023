package advent.of.tdd.day5

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RangeTest extends AnyFlatSpec {

  "A range" should "not change if transformation source is outside of it" in {
    val sut = Range(lowerBound = 20, length = 10)

    sut.applyTransformation(
      RangeTransformation(
        sourceRange = Range(40, 10),
        targetRange = Range(55, 10)
      )
    ) should be (Seq(sut))
  }

  it should "be transformed into single range if transformation source range contains it" in {
    val sut = Range(lowerBound = 20, length = 10)

    sut.applyTransformation(
      RangeTransformation(
        sourceRange = Range(10, 30),
        targetRange = Range(55, 30)
      )
    ) should be(
      Seq(
        Range(lowerBound = 65, length = 10)
      )
    )
  }

  it should "be transformed into two ranges if transformation source is higher and intersects with it" in {
    val sut = Range(lowerBound = 20, length = 10)

    sut.applyTransformation(
      RangeTransformation(
        sourceRange = Range(25, 30),
        targetRange = Range(55, 30)
      )
    ) should be(
      Seq(
        Range(lowerBound = 20, length = 5),
        Range(lowerBound = 55, length = 5)
      )
    )
  }

  it should "be transformed into two ranges if transformation source is lower and intersects with it" in {
    val sut = Range(lowerBound = 20, length = 10)

    sut.applyTransformation(
      RangeTransformation(
        sourceRange = Range(14, 10),
        targetRange = Range(54, 10)
      )
    ) should be(
      Seq(
        Range(lowerBound = 60, length = 4),
        Range(lowerBound = 24, length = 6)
      )
    )
  }

  it should "be transformed into three ranges if it contains transformation source" in {
    val sut = Range(lowerBound = 20, length = 10)

    sut.applyTransformation(
      RangeTransformation(
        sourceRange = Range(21, 6),
        targetRange = Range(51, 6)
      )
    ) should be(
      Seq(
        Range(lowerBound = 20, length = 1),
        Range(lowerBound = 51, length = 6),
        Range(lowerBound = 27, length = 3)
      )
    )
  }
}
