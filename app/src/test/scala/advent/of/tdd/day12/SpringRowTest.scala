package advent.of.tdd.day12

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class SpringRowTest extends AnyFlatSpec {

  "Spring row" should "return potential combinations for simple row" in {
    SpringRow("???.###", Seq(1, 1, 3)).getPotentialCombinations should be(1)
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

  it should "unfold 1st row" in {
    SpringRow("???.###", Seq(1, 1, 3)).unfold() should be(
      SpringRow("???.###????.###????.###????.###????.###", Seq(1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3))
    )
  }

  it should "work for unfolded combinations" in {
    SpringRow("????.######..#####.", Seq(1, 6, 5)).getPotentialCombinations should be(4)
    SpringRow("????.######..#####.", Seq(1, 6, 5)).unfold(2).getPotentialCombinations should be(20)
    SpringRow("????.######..#####.", Seq(1, 6, 5)).unfold(3).getPotentialCombinations should be(100)
    SpringRow("????.######..#####.", Seq(1, 6, 5)).unfold(4).getPotentialCombinations should be(500)
    SpringRow("????.######..#####.", Seq(1, 6, 5)).unfold().getPotentialCombinations should be(2500)
  }

  it should "work for many unfolded combinations" in {
    SpringRow("?###????????", Seq(3, 2, 1)).getPotentialCombinations should be(10)
    SpringRow("?###????????", Seq(3, 2, 1)).unfold(2).getPotentialCombinations should be(150)
    SpringRow("?###????????", Seq(3, 2, 1)).unfold(3).getPotentialCombinations should be(2250)
    SpringRow("?###????????", Seq(3, 2, 1)).unfold(4).getPotentialCombinations should be(33750)
    SpringRow("?###????????", Seq(3, 2, 1)).unfold().getPotentialCombinations should be(506250)
  }

}
