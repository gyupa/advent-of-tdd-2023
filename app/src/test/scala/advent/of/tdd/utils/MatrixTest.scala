package advent.of.tdd.utils

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MatrixTest extends AnyFlatSpec {

  private val rows =
    Seq(
      Seq('h', 'e', 'l', 'l', 'o'),
      Seq('w', 'o', 'r', 'l', 'd')
    )


  "Matrix of characters" should "return size properly" in {
    val sut = Matrix(rows)
    rows(1)
    sut.numberOfRows should be(2)
    sut.numberOfColumns should be(5)
  }

  it should "return elements properly" in {
    val sut = Matrix(rows)
    sut(2, 5) should be('d')
    sut(1, 1) should be('h')
  }

  it should "return rows and columns properly" in {
    val sut = Matrix(rows)
    sut.rows should be(
      Seq(
        Seq('h', 'e', 'l', 'l', 'o'),
        Seq('w', 'o', 'r', 'l', 'd')
      )
    )
    sut.columns should be(
      Seq(
        Seq('h', 'w'),
        Seq('e', 'o'),
        Seq('l', 'r'),
        Seq('l', 'l'),
        Seq('o', 'd')
      )
    )
  }
}
