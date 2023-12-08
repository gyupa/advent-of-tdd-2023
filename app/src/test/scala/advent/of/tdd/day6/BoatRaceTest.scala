package advent.of.tdd.day6

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoatRaceTest extends AnyFlatSpec {

  "Boat race" should "calculate distance properly" in {
    val sut = BoatRace(
      duration = 7,
      currentRecord = 9
    )

    sut.calculateDistance(0) should be (0)
    sut.calculateDistance(1) should be (6)
    sut.calculateDistance(2) should be (10)
    sut.calculateDistance(3) should be (12)
    sut.calculateDistance(4) should be (12)
    sut.calculateDistance(5) should be (10)
    sut.calculateDistance(6) should be (6)
    sut.calculateDistance(7) should be (0)
  }

  it should "return the number of ways to beet the records" in {
    val sut = BoatRace(
      duration = 7,
      currentRecord = 9
    )

    sut.calculateNumberOfWaysToBeatRecord should be(4)
  }
}
