package advent.of.tdd.day8

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DesertMapTest extends AnyFlatSpec {

  "Map" should "be created from file properly" in {
    val linesEither: Either[String, Seq[String]] = FileReaderImpl.readLinesFromFile("src/main/resources/day8_example.txt")

    linesEither.map {
      lines =>
        val expectedMap =
          DesertMap(
            instructions = Seq(
              Direction.L,
              Direction.L,
              Direction.R
            ),
            nodesById = Map(
              "AAA" -> Node("AAA", "BBB", "BBB"),
              "BBB" -> Node("BBB", "AAA", "ZZZ"),
              "ZZZ" -> Node("ZZZ", "ZZZ", "ZZZ")
            )
          )

        DesertMap.createMapFromLines(lines) should be(expectedMap)
    }.orElse(fail())

  }

  it should "calculate number of steps for example properly" in {
    val sut =
      DesertMap(
        instructions = Seq(
          Direction.L,
          Direction.L,
          Direction.R
        ),
        nodesById = Map(
          "AAA" -> Node("AAA", "BBB", "BBB"),
          "BBB" -> Node("BBB", "AAA", "ZZZ"),
          "ZZZ" -> Node("ZZZ", "ZZZ", "ZZZ")
        )
      )

    sut.getNumberOfStepsFromStartToAnyEndStatesFromSingleStartNode should be(6)

  }

  it should "calculate steps from multiple start nodes properly" in {
    val linesEither: Either[String, Seq[String]] = FileReaderImpl.readLinesFromFile("src/main/resources/day8_example2.txt")

    linesEither.map {
      lines =>
        DesertMap.createMapFromLines(lines).getNumberOfStepsFromStartToEndFromAllStartNodes should be(6)
    }.orElse(fail())

  }

}
