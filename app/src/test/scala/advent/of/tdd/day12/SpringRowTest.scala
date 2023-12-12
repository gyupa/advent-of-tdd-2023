package advent.of.tdd.day12

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class SpringRowTest extends AnyFlatSpec {

  "Spring row" should "return potential combinations for simple row" in {
    SpringRow("???.###", Seq(1,1,3)).getPotentialCombinations should be (1)
  }

  it should "return potential combinations for more complex row" in {
    SpringRow(".??..??...?##.", Seq(1, 1, 3)).getPotentialCombinations should be(4)
  }

  it should "return potential combinations for row #3" in {
    SpringRow("?#?#?#?#?#?#?#?", Seq(1, 3, 1, 6)).getPotentialCombinations should be(1)
  }

  it should "return potential combinations for row #4" in {
    SpringRow("????.#...#...", Seq(4, 1, 1)).getPotentialCombinations should be(1)
  }

  it should "return potential combinations for row #5" in {
    SpringRow("????.######..#####.", Seq(1, 6, 5)).getPotentialCombinations should be(4)
  }

  it should "return potential combinations for simplified row #6" in {
    SpringRow("???????", Seq(2, 1)).getPotentialCombinations should be(10)
  }

  it should "return potential combinations for other simplified row #6" in {
    SpringRow("?###?", Seq(3)).getPotentialCombinations should be(1)
  }

  it should "return potential combinations for very complex row" in {
    SpringRow("?###????????", Seq(3, 2, 1)).getPotentialCombinations should be(10)
  }

  it should "work for other test cases, too" in {
    SpringRow("??#??.?", Seq(2)).getPotentialCombinations should be(2)
    SpringRow("?????.?", Seq(2)).getPotentialCombinations should be(4)
    SpringRow("#???.??", Seq(2)).getPotentialCombinations should be(1)
    SpringRow("#???.??", Seq(2, 1)).getPotentialCombinations should be(3)
    SpringRow(".?#??.??", Seq(2, 1)).getPotentialCombinations should be(5)
    SpringRow("??.??", Seq(1)).getPotentialCombinations should be(4)
    SpringRow("??.??#.", Seq(1)).getPotentialCombinations should be(1)

  }

}
