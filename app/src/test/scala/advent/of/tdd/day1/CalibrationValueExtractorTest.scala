package advent.of.tdd.day1

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CalibrationValueExtractorTest extends AnyFlatSpec {

  "Calibration value extractor" should "return string as number if it contains 2 digits only" in {
    CalibrationValueExtractorImpl.readCalibrationValue("42") should be (42)
  }

  it should "be able to extract first and last digit from string containing 2 digits" in {
    CalibrationValueExtractorImpl.readCalibrationValue("1abc2") should be (12)
  }

  it should "be able to extract calibration value if string contains more than 2 digits" in {
    CalibrationValueExtractorImpl.readCalibrationValue("a1b2c3d4e5f") should be(15)
  }

  it should "be able to extract calibration value if string contains single digit only" in {
    CalibrationValueExtractorImpl.readCalibrationValue("treb7uchet") should be(77)
  }

}
