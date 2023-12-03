package advent.of.tdd.day2

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameReaderTest extends AnyFlatSpec {

  "Game reader" should "read game with single turn" in {
    GameReader.readGame("Game 1: 3 blue, 4 red") should be (
      Game(
        id = 1,
        turns = Seq(
          Turn(
            numberOfBlueBalls = 3,
            numberOfRedBalls = 4,
            numberOfGreenBalls = 0
          )
        )
      )
    )
  }

  "Game reader" should "read game with multiple turns" in {
    GameReader.readGame("Game 2: 3 blue, 4 red; 5 blue, 2 red, 1 green") should be(
      Game(
        id = 2,
        turns = Seq(
          Turn(
            numberOfBlueBalls = 3,
            numberOfRedBalls = 4,
            numberOfGreenBalls = 0
          ),
          Turn(
            numberOfBlueBalls = 5,
            numberOfRedBalls = 2,
            numberOfGreenBalls = 1
          )
        )
      )
    )
  }
}
