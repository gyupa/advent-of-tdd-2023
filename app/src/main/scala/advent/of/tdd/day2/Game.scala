package advent.of.tdd.day2

case class Game (
  id: Int,
  turns: Seq[Turn]
) {
  def maxNumberOfBlueBalls: Int = turns.map(_.numberOfBlueBalls).max
  def maxNumberOfRedBalls: Int = turns.map(_.numberOfRedBalls).max
  def maxNumberOfGreenBalls: Int = turns.map(_.numberOfGreenBalls).max
}

case class Turn (
  numberOfBlueBalls: Int,
  numberOfRedBalls: Int,
  numberOfGreenBalls: Int
)
