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
    ) should be(Seq(sut))
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

  it should "calculate intersect properly" in {
    Range(10, 6).intersect(Range(14, 8)) should be (Some(Range(14, 2)))
    Range(10, 6).intersect(Range(11, 2)) should be (Some(Range(11, 2)))
    Range(10, 6).intersect(Range(6, 6)) should be (Some(Range(10, 2)))
    Range(10, 6).intersect(Range(26, 6)) should be (None)
  }

  it should "subtract other range properly" in {
    Range(10, 6).minus(Range(14, 8)) should be(Seq(Range(10, 4)))
    Range(10, 6).minus(Range(11, 2)) should be(Seq(Range(10, 1), Range(13, 3)))
    Range(10, 6).minus(Range(6, 6)) should be(Seq(Range(12, 4)))
    Range(10, 6).minus(Range(26, 6)) should be(Seq(Range(10, 6)))
    Range(10, 6).minus(Range(1, 20)) should be (Seq())
    Range(10, 6).minus(Range(10, 6)) should be (Seq())
  }

  it should "be transformed by multiple transformations properly" in {
    val sut = Range(lowerBound = 20, length = 10)

    sut.applyMultipleTransformations(
      Seq(
        RangeTransformation(
          sourceRange = Range(22, 3),
          targetRange = Range(25, 3)
        ),
        RangeTransformation(
          sourceRange = Range(25, 5),
          targetRange = Range(8, 5)
        )
      )
    ) should be(
      Seq(
        Range(lowerBound = 20, length = 2),
        Range(lowerBound = 25, length = 3),
        Range(lowerBound = 8, length = 5)
      )
    )
  }
}
