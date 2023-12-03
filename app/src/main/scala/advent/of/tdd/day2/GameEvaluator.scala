package advent.of.tdd.day2

import advent.of.tdd.utils.FileReaderImpl

class GameEvaluator {
  def calculateSumOfPossibleGameIds(inputFileName: String): Int = {
    FileReaderImpl.readLinesFromFile(inputFileName).map(
      gamesAsString =>
        for (
          gameAsString: String <- gamesAsString
        ) yield
          GameReader.readGame(gameAsString)
    ).map(
      games =>
        games.filter(
          game =>
            game.maxNumberOfBlueBalls <= 14 &&
              game.maxNumberOfRedBalls <= 12 &&
              game.maxNumberOfGreenBalls <= 13
        )
    ).map(
      games => games.map(_.id).sum
    ).getOrElse(0)
  }

  def calculatePowerOfMinumumNumberOfBalls(inputFileName: String): Int = {
    FileReaderImpl.readLinesFromFile(inputFileName).map(
      gamesAsString =>
        for (
          gameAsString: String <- gamesAsString
        ) yield
          GameReader.readGame(gameAsString)
    ).map(
      games =>
        games.map(
          game =>
            game.maxNumberOfBlueBalls *
              game.maxNumberOfRedBalls *
              game.maxNumberOfGreenBalls
        )
    ).map(
      _.sum
    ).getOrElse(0)
  }

}
