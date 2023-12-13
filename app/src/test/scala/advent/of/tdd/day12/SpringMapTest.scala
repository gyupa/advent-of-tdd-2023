package advent.of.tdd.day12

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SpringMapTest extends AnyFlatSpec {

  private val fileContents =
    Seq(
      "???.### 1,1,3",
      ".??..??...?##. 1,1,3",
      "?#?#?#?#?#?#?#? 1,3,1,6",
      "????.#...#... 4,1,1",
      "????.######..#####. 1,6,5",
      "?###???????? 3,2,1"
    )

  "Spring map" should "be constructed properly from lines" in {
    SpringMap.readFromLines(fileContents) should be (
      SpringMap(
        Seq(
          SpringRow("???.###", Seq(1, 1, 3)),
          SpringRow(".??..??...?##.", Seq(1, 1, 3)),
          SpringRow("?#?#?#?#?#?#?#?", Seq(1, 3, 1, 6)),
          SpringRow("????.#...#...", Seq(4, 1, 1)),
          SpringRow("????.######..#####.", Seq(1, 6, 5)),
          SpringRow("?###????????", Seq(3, 2, 1))
        )
      )
    )
  }

  it should "calculate the sum properly" in {
    SpringMap.readFromLines(fileContents).calculateSumOfCombinations should be (21)
  }

  it should "calculate unfolded sum properly" in {
    SpringMap.readFromLines(fileContents).calculateSumOfUnfoldedCombinations should be(525152)
  }
}
