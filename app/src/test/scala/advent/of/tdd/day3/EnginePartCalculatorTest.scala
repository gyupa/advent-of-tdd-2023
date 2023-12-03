package advent.of.tdd.day3

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EnginePartCalculatorTest extends AnyFlatSpec with MockFactory {

  "Engine part evaluator" should "find engine part from single line" in {

    val lines = Seq("...$123...")

    val adjacentExtractorStub = stub[AdjacentCharacterExtractor]
    (adjacentExtractorStub.extractAdjacentCellsFromNumbersInLine _).when(
      lines.head, None, None
    ).returns(
      Seq(123 -> "$.")
    )

    val sut = new EnginePartEvaluator(adjacentExtractorStub)

    sut.calculateSumFromEngineParts(lines) should be (123)
  }

  it should "sum all engine part ids and ignore other numbers" in {

    val lines = Seq("...$123...456...78#")

    val adjacentExtractorStub = stub[AdjacentCharacterExtractor]
    (adjacentExtractorStub.extractAdjacentCellsFromNumbersInLine _).when(
      lines.head, None, None
    ).returns(
      Seq(123 -> "$.", 456 -> "..", 78 -> ".#")
    )

    val sut = new EnginePartEvaluator(adjacentExtractorStub)

    sut.calculateSumFromEngineParts(lines) should be(201)
  }

  it should "sum all engine part ids and ignore other numbers from multiple lines" in {

    val lines = Seq(
      "...$123...456...78#",
      "5.......$..........",
      "&........12........"
    )

    val adjacentExtractorStub = stub[AdjacentCharacterExtractor]
    (adjacentExtractorStub.extractAdjacentCellsFromNumbersInLine _).when(
      lines.head, None, Some(lines(1))
    ).returns(
      Seq(123 -> "$.", 456 -> "..", 78 -> ".#")
    )

    (adjacentExtractorStub.extractAdjacentCellsFromNumbersInLine _).when(
      lines(1), Some(lines.head), Some(lines(2))
    ).returns(
      Seq(5 -> "...&.")
    )

    (adjacentExtractorStub.extractAdjacentCellsFromNumbersInLine _).when(
      lines(2), Some(lines(1)), None
    ).returns(
      Seq(12 -> "$.........")
    )

    val sut = new EnginePartEvaluator(adjacentExtractorStub)

    sut.calculateSumFromEngineParts(lines) should be(218)
  }

  it should "extract gear from single line" in {
    val lines = Seq("...$123.2*10*78#")

    val adjacentExtractorStub = stub[AdjacentCharacterExtractor]
    (adjacentExtractorStub.extractAdjacentNumbersForGearCandidatesInLine _).when(
      lines.head, None, None
    ).returns(
      Seq(Seq(2, 10), Seq(10, 78))
    )

    val sut = new EnginePartEvaluator(adjacentExtractorStub)

    sut.calculateGearRatio(lines) should be(800)
  }
}
