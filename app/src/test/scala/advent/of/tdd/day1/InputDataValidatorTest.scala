package advent.of.tdd.day1

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.language.postfixOps

@RunWith(classOf[JUnitRunner])
class InputDataValidatorTest extends AnyFlatSpec {

  "Input data validator" should "return true for empty file" in {
    InputDataValidatorImpl.validateInput(Seq.empty) should be (true)
  }

  it should "return true if all line contain digit" in {
    InputDataValidatorImpl.validateInput(Seq("asdf4ghjk", "5678")) should be(true)
  }

  it should "return false if single line is empty" in {
    InputDataValidatorImpl.validateInput(Seq("")) should be(false)
  }

  it should "return false if it contains line without digits" in {
    InputDataValidatorImpl.validateInput(Seq("s3d4f5", "aaa", "334f")) should be(false)
  }
}
