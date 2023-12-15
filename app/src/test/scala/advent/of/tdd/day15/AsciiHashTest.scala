package advent.of.tdd.day15

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AsciiHashTest extends AnyFlatSpec {

  "Ascii hash" should "return good result for examples" in {
    AsciiHash.calculateHash("rn=1") should be(30)
    AsciiHash.calculateHash("cm-") should be(253)
    AsciiHash.calculateHash("qp=3") should be(97)
    AsciiHash.calculateHash("cm=2") should be(47)
    AsciiHash.calculateHash("qp-") should be(14)
    AsciiHash.calculateHash("pc=4") should be(180)
    AsciiHash.calculateHash("ot=9") should be(9)
    AsciiHash.calculateHash("ab=5") should be(197)
    AsciiHash.calculateHash("pc-") should be(48)
    AsciiHash.calculateHash("pc=6") should be(214)
    AsciiHash.calculateHash("ot=7") should be(231)
  }

}
