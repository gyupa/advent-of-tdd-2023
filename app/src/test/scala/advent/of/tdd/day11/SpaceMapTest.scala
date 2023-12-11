package advent.of.tdd.day11

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.collection.immutable.Seq

@RunWith(classOf[JUnitRunner])
class SpaceMapTest extends AnyFlatSpec {


  "Space map" should "expand properly" in {
    val originalSpaceMap =
      Seq(
        Seq('.', '.', '.', '#', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
        Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '#', '.', '.', '.', '.', '.')
      )

    val expandedSpaceMap =
      Seq(
        Seq('.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
        Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.'),
        Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
        Seq('#', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.')
      )

    SpaceMap(originalSpaceMap).expandSpace should be(SpaceMap(expandedSpaceMap))
  }

  it should "get galaxies properly" in {
    val sut =
      SpaceMap(
        Seq(
          Seq('.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
          Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.'),
          Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
          Seq('#', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.')
        )
      )

    sut.findGalaxies should be(
      Set(
        Galaxy(MapPosition(0, 4)),
        Galaxy(MapPosition(1, 9)),
        Galaxy(MapPosition(2, 0)),
        Galaxy(MapPosition(5, 8)),
        Galaxy(MapPosition(6, 1)),
        Galaxy(MapPosition(7, 12)),
        Galaxy(MapPosition(10, 9)),
        Galaxy(MapPosition(11, 0)),
        Galaxy(MapPosition(11, 5))
      )
    )
  }

  it should "calculate sum of distance between any two galaxies" in {
    val sut =
      SpaceMap(
        Seq(
          Seq('.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
          Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '.'),
          Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
          Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
          Seq('#', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.')
        )
      )

    sut.calculateSumOfDistances should be(374)
  }

  it should "calculate expanded distance between two galaxies" in {
    val originalSpaceMap =
      Seq(
        Seq('.', '.', '.', '#', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
        Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '#', '.', '.', '.', '.', '.')
      )

    val sut = SpaceMap(originalSpaceMap)

    sut.calculateDistanceAssumingLargeExpansion(
      Galaxy(MapPosition(2, 0)),
      Galaxy(MapPosition(4, 6))
    ) should be(3000005)
    sut.calculateDistanceAssumingLargeExpansion(
      Galaxy(MapPosition(4, 6)),
      Galaxy(MapPosition(2, 0))
    ) should be(3000005)
    sut.calculateDistanceAssumingLargeExpansion(
      Galaxy(MapPosition(4, 6)),
      Galaxy(MapPosition(9, 4))
    ) should be(2000005)
    sut.calculateDistanceAssumingLargeExpansion(
      Galaxy(MapPosition(9, 4)),
      Galaxy(MapPosition(4, 6))
    ) should be(2000005)
  }

  it should "calculate sum of distance expanded by 10" in {
    val originalSpaceMap =
      Seq(
        Seq('.', '.', '.', '#', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
        Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '#', '.', '.', '.', '.', '.')
      )

    val sut = SpaceMap(originalSpaceMap, ratio = 10)

    sut.calculateSumOfDistancesExpanded should be (1030)
  }

  it should "calculate sum of distance expanded by 100" in {
    val originalSpaceMap =
      Seq(
        Seq('.', '.', '.', '#', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '#', '.', '.', '.'),
        Seq('.', '#', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
        Seq('#', '.', '.', '.', '#', '.', '.', '.', '.', '.')
      )

    val sut = SpaceMap(originalSpaceMap, ratio = 100)

    sut.calculateSumOfDistancesExpanded should be(8410)
  }
}
