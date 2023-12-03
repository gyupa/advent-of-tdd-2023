package advent.of.tdd.day2

import scala.language.postfixOps
import scala.util.matching.Regex

object GameReader {

  def readGame(gameDescription: String): Game = {
    val pattern = "Game (\\d+): (.*)".r
    (
      for (patternMatch: Regex.Match <- pattern.findFirstMatchIn(gameDescription)) yield {
        println(s"game id: ${patternMatch.group(1)} turns: ${patternMatch.group(2)}")
        val turnsAsString = patternMatch.group(2)
        val turnsInGame: Array[Turn] =
          for (turn <- turnsAsString.split(";")) yield
            readTurn(turn)
        Game(
          id = patternMatch.group(1).toInt,
          turns = turnsInGame
        )
      }
      ).get

  }

  private def readTurn(turn: String) = {
    val currentTurnData: Array[(String, Int)] = for (numberOfBallsAndColor <- turn.split(",")) yield {
      val numberOfBallsAndColorPattern = "(\\d+) (blue|red|green)".r

      (for (ballsAndColorPatternMatch: Regex.Match <- numberOfBallsAndColorPattern.findFirstMatchIn(numberOfBallsAndColor)) yield {
        val numberOfBalls = ballsAndColorPatternMatch.group(1).toInt
        val colorOfBalls = ballsAndColorPatternMatch.group(2)
        (colorOfBalls, numberOfBalls)
      }).get
    }
    val turnMap: PartialFunction[String, Int] = currentTurnData.toMap.withDefault(_ => 0)
    val currentTurn =
      Turn(
        numberOfBlueBalls = turnMap("blue"),
        numberOfRedBalls = turnMap("red"),
        numberOfGreenBalls = turnMap("green")
      )
    currentTurn
  }
}
