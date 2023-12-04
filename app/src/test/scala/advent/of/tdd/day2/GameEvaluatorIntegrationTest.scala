package advent.of.tdd.day2

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameEvaluatorIntegrationTest extends AnyFlatSpec {

  "Game evaluator" should "return the same result as the example" in {
    val evaluator = new GameEvaluator()
    evaluator.calculateSumOfPossibleGameIds("src/main/resources/day2_a_example.txt") should be (8)
  }
}
