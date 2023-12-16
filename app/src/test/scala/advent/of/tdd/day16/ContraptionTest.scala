package advent.of.tdd.day16

import advent.of.tdd.day16.Direction._
import advent.of.tdd.utils.Matrix
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.collection.immutable.Seq

@RunWith(classOf[JUnitRunner])
class ContraptionTest extends AnyFlatSpec {

  "Contraption" should "let beam go through through empty space" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '.', '.'),
        Seq('.', '.', '.')
      )
    )

    Contraption(matrix).step(Right, (0, 0)) should be(Seq((Right, (0, 1))))
    Contraption(matrix).step(Left, (0, 1)) should be(Seq((Left, (0, 0))))
    Contraption(matrix).step(Down, (0, 0)) should be(Seq((Down, (1, 0))))
    Contraption(matrix).step(Up, (1, 0)) should be(Seq((Up, (0, 0))))
  }

  it should "return empty result at the edges" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '.', '.'),
        Seq('.', '.', '.')
      )
    )

    Contraption(matrix).step(Right, (0, 2)) should be(Seq.empty)
    Contraption(matrix).step(Up, (0, 2)) should be(Seq.empty)
    Contraption(matrix).step(Left, (1, 0)) should be(Seq.empty)
    Contraption(matrix).step(Down, (1, 0)) should be(Seq.empty)
  }

  it should "go through pointy end of splitter" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '-', '.'),
        Seq('.', '.', '|'),
        Seq('.', '.', '.')
      )
    )

    Contraption(matrix).step(Right, (0, 1)) should be(Seq((Right, (0, 2))))
    Contraption(matrix).step(Left, (0, 1)) should be(Seq((Left, (0, 0))))
    Contraption(matrix).step(Down, (1, 2)) should be(Seq((Down, (2, 2))))
    Contraption(matrix).step(Up, (1, 2)) should be(Seq((Up, (0, 2))))
  }

  it should "go through flat side of splitter" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '.', '.', '.'),
        Seq('.', '-', '|', '.'),
        Seq('.', '.', '.', '.')
      )
    )

    Contraption(matrix).step(Right, (1, 2)) should be(Seq((Up, (0, 2)), (Down, (2, 2))))
    Contraption(matrix).step(Left, (1, 2)) should be(Seq((Up, (0, 2)), (Down, (2, 2))))
    Contraption(matrix).step(Up, (1, 1)) should be(Seq((Left, (1, 0)), (Right, (1, 2))))
    Contraption(matrix).step(Down, (1, 1)) should be(Seq((Left, (1, 0)), (Right, (1, 2))))
  }

  it should "return empty on the edges for splitter" in {
    val matrix = Matrix(
      Seq(
        Seq('|', '.', '-'),
        Seq('.', '.', '.'),
        Seq('-', '.', '|')
      )
    )

    Contraption(matrix).step(Right, (0, 2)) should be(Seq.empty)
    Contraption(matrix).step(Up, (0, 0)) should be(Seq.empty)
    Contraption(matrix).step(Left, (2, 0)) should be(Seq.empty)
    Contraption(matrix).step(Down, (2, 2)) should be(Seq.empty)
    Contraption(matrix).step(Right, (0, 0)) should be(Seq((Down, (1, 0))))
    Contraption(matrix).step(Right, (2, 2)) should be(Seq((Up, (1, 2))))
    Contraption(matrix).step(Left, (0, 0)) should be(Seq((Down, (1, 0))))
    Contraption(matrix).step(Left, (2, 2)) should be(Seq((Up, (1, 2))))
    Contraption(matrix).step(Up, (0, 2)) should be(Seq((Left, (0, 1))))
    Contraption(matrix).step(Up, (2, 0)) should be(Seq((Right, (2, 1))))
    Contraption(matrix).step(Down, (0, 2)) should be(Seq((Left, (0, 1))))
    Contraption(matrix).step(Down, (2, 0)) should be(Seq((Right, (2, 1))))
  }

  it should "handle mirrors" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '.', '.', '.'),
        Seq('.', '/', '\\', '.'),
        Seq('.', '.', '.', '.')
      )
    )

    // handle \
    Contraption(matrix).step(Right, (1, 2)) should be(Seq((Down, (2, 2))))
    Contraption(matrix).step(Left, (1, 2)) should be(Seq((Up, (0, 2))))
    Contraption(matrix).step(Up, (1, 2)) should be(Seq((Left, (1, 1))))
    Contraption(matrix).step(Down, (1, 2)) should be(Seq((Right, (1, 3))))

    // handle /
    Contraption(matrix).step(Right, (1, 1)) should be(Seq((Up, (0, 1))))
    Contraption(matrix).step(Left, (1, 1)) should be(Seq((Down, (2, 1))))
    Contraption(matrix).step(Up, (1, 1)) should be(Seq((Right, (1, 2))))
    Contraption(matrix).step(Down, (1, 1)) should be(Seq((Left, (1, 0))))
  }

  it should "handle mirrors at the edges" in {
    val matrix = Matrix(
      Seq(
        Seq('\\', '.', '/'),
        Seq('.', '.', '.'),
        Seq('/', '.', '\\')
      )
    )

    Contraption(matrix).step(Right, (0, 2)) should be(Seq.empty)
    Contraption(matrix).step(Up, (0, 2)) should be(Seq.empty)
    Contraption(matrix).step(Left, (2, 0)) should be(Seq.empty)
    Contraption(matrix).step(Down, (2, 0)) should be(Seq.empty)
    Contraption(matrix).step(Left, (0, 0)) should be(Seq.empty)
    Contraption(matrix).step(Up, (0, 0)) should be(Seq.empty)
    Contraption(matrix).step(Right, (2, 2)) should be(Seq.empty)
    Contraption(matrix).step(Down, (2, 2)) should be(Seq.empty)
  }

  it should "work with example" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '|', '.', '.', '.', '\\', '.', '.', '.', '.'),
        Seq('|', '.', '-', '.', '\\', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '|', '-', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '|', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
        Seq('.', '.', '.', '.', '.', '.', '.', '.', '.', '\\'),
        Seq('.', '.', '.', '.', '/', '.', '\\', '\\', '.', '.'),
        Seq('.', '-', '.', '-', '/', '.', '.', '|', '.', '.'),
        Seq('.', '|', '.', '.', '.', '.', '-', '|', '.', '\\'),
        Seq('.', '.', '/', '/', '.', '|', '.', '.', '.', '.')
      )
    )
    Contraption(matrix).findCoveredTiles should be (46)
  }

  /*it should "enable beam go through through empty space" in {
    val matrix = Matrix(
      Seq(
        Seq('.', '\\', '.'),
        Seq('.', '.', '.')
      )
    )

    Contraption(matrix).findRoutes should be(
      Seq((0, 0), (0, 1), (0, 2))
    )
  }*/
}
