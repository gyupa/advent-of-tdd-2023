package advent.of.tdd.day15

object Operation extends Enumeration {
  val Add, Remove = Value
}

case class Instruction(
                        operation: Operation.Value,
                        label: String,
                        focalLength: Option[Int]
                      )
