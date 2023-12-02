package advent.of.tdd.day1

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner
import org.scalamock.scalatest.MockFactory

@RunWith(classOf[JUnitRunner])
class CalibrationValueAggregatorTest extends AnyFlatSpec with MockFactory {

  "CalibrationValueAggregator" should "return 0 for empty sequence" in {
    val extractor = stub[CalibrationValueExtractor]

    CalibrationValueAggregator(extractor).calculateTotalCalibrationScore(Seq()) should be (0)
  }

  it should "return the score the record for list size of 1" in {
    val extractor = stub[CalibrationValueExtractor]
    (extractor.readCalibrationValue _).when("a").returns(15)

    CalibrationValueAggregator(extractor).calculateTotalCalibrationScore(Seq("a")) should be(15)
  }

  it should "return the sum the scores in the sequence" in {
    val extractor = stub[CalibrationValueExtractor]
    (extractor.readCalibrationValue _).when("a").returns(15)
    (extractor.readCalibrationValue _).when("b").returns(16)
    (extractor.readCalibrationValue _).when("c").returns(17)

    CalibrationValueAggregator(extractor).calculateTotalCalibrationScore(Seq("a", "b", "c")) should be(48)
  }

}
