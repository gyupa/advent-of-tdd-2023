package advent.of.tdd.day15

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LensContainerTest extends AnyFlatSpec {

  private val instructionsInExample =
    Seq(
      Instruction(Operation.Add, "rn", Some(1)),
      Instruction(Operation.Remove, "cm", None),
      Instruction(Operation.Add, "qp", Some(3)),
      Instruction(Operation.Add, "cm", Some(2)),
      Instruction(Operation.Remove, "qp", None),
      Instruction(Operation.Add, "pc", Some(4)),
      Instruction(Operation.Add, "ot", Some(9)),
      Instruction(Operation.Add, "ab", Some(5)),
      Instruction(Operation.Remove, "pc", None),
      Instruction(Operation.Add, "pc", Some(6)),
      Instruction(Operation.Add, "ot", Some(7))
    )


  "Lens container" should "be created from string properly" in {
    LensContainer("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7") should be(
      LensContainer(instructionsInExample)
    )
  }

  it should "formulate boxes properly" in {
    LensContainer(instructionsInExample).boxes should be(
      Map(
        0 -> Seq(Lens("rn", 1), Lens("cm", 2)),
        1 -> Seq(),
        3 -> Seq(Lens("ot", 7), Lens("ab", 5), Lens("pc", 6))
      )
    )
  }

  it should "calculate score properly" in {
    LensContainer(instructionsInExample).calculateScore should be(145)
  }
}
