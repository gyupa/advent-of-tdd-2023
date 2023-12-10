package advent.of.tdd.day10

import advent.of.tdd.utils.FileReaderImpl
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

import scala.collection.immutable.Seq

@RunWith(classOf[JUnitRunner])
class PipeMapTest extends AnyFlatSpec {

  private val examplePipeMap =
    Seq(
      "7-F7-",
      ".FJ|7",
      "SJLL7",
      "|F--J",
      "LJ.LJ"
    )

  "Pipe map" should "find start position from input" in {
    PipeMap.createPipeMapFromFile(examplePipeMap).startNode.mapPosition should be(
      MapPosition(2, 0)
    )
  }

  "Pipe map" should "read input properly" in {
    PipeMap.createPipeMapFromFile(examplePipeMap) should be(
      PipeMap(
        mapAsMatrix = Seq(
          Seq('7', '-', 'F', '7', '-'),
          Seq('.', 'F', 'J', '|', '7'),
          Seq('S', 'J', 'L', 'L', '7'),
          Seq('|', 'F', '-', '-', 'J'),
          Seq('L', 'J', '.', 'L', 'J')
        ),
        startNode = PipePathNode(
          fromDirection = MapDirection.E,
          character = 'S',
          mapPosition = MapPosition(2, 0)
        )
      )
    )
  }

  "Pipe map" should "calculate circle from input properly" in {
    PipeMap.createPipeMapFromFile(examplePipeMap).findCircleFromStartNode.size should be(17)
  }

  "Pipe map" should "read the eastern neighbour properly when it exists" in {
    for (c <- Seq('-', 'J', '7', 'S'))
      PipeMap.readEasternNeighbour(
        MapPosition(0, 0), Seq(Seq('X', c))
      ) should be(Some(MapPosition(0, 1)))
  }

  "Pipe map" should "read the eastern neighbour properly when it does not exists" in {
    for (c <- Seq('.', '|', 'L', 'F'))
      PipeMap.readEasternNeighbour(
        MapPosition(0, 0), Seq(Seq('X', c))
      ) should be(None)
  }

  "Pipe map" should "read the western neighbour properly when it exists" in {
    for (c <- Seq('-', 'L', 'F', 'S'))
      PipeMap.readWesternNeighbour(
        MapPosition(0, 1), Seq(Seq(c, 'X'))
      ) should be(Some(MapPosition(0, 0)))
  }

  "Pipe map" should "read the western neighbour properly when it does not exists" in {
    for (c <- Seq('.', '|', 'J', '7'))
      PipeMap.readWesternNeighbour(
        MapPosition(0, 0), Seq(Seq('X', c))
      ) should be(None)
  }

  "Pipe map" should "read the northern neighbour properly when it exists" in {
    for (c <- Seq('|', '7', 'F', 'S'))
      PipeMap.readNorthernNeighbour(
        MapPosition(1, 0), Seq(Seq(c), Seq('X'))
      ) should be(Some(MapPosition(0, 0)))
  }

  "Pipe map" should "read the northern neighbour properly when it does not exists" in {
    for (c <- Seq('.', '-', 'L', 'J'))
      PipeMap.readNorthernNeighbour(
        MapPosition(1, 0), Seq(Seq(c), Seq('X'))
      ) should be(None)
  }

  "Pipe map" should "read the southern neighbour properly when it exists" in {
    for (c <- Seq('|', 'L', 'J', 'S'))
      PipeMap.readSouthernNeighbour(
        MapPosition(0, 0), Seq(Seq('X'), Seq(c))
      ) should be(Some(MapPosition(1, 0)))
  }

  "Pipe map" should "read the southern neighbour properly when it does not exists" in {
    for (c <- Seq('.', '-', '7', 'F'))
      PipeMap.readSouthernNeighbour(
        MapPosition(0, 0), Seq(Seq('X'), Seq(c))
      ) should be(None)
  }

  it should "return next node when it is L" in {
    val map = Seq(Seq('X', '.'), Seq('L', 'Y'))
    val sut = PipeMap(mapAsMatrix = map, startNode = null)
    sut.getNextNode(PipePathNode(MapDirection.E, 'L', MapPosition(1, 0))) should be(PipePathNode(MapDirection.S, 'X', MapPosition(0, 0)))
    sut.getNextNode(PipePathNode(MapDirection.N, 'L', MapPosition(1, 0))) should be(PipePathNode(MapDirection.W, 'Y', MapPosition(1, 1)))
  }

  it should "return next node when it is F" in {
    val map = Seq(Seq('F', 'X'), Seq('Y', '.'))
    val sut = PipeMap(mapAsMatrix = map, startNode = null)
    sut.getNextNode(PipePathNode(MapDirection.E, 'F', MapPosition(0, 0))) should be(PipePathNode(MapDirection.N, 'Y', MapPosition(1, 0)))
    sut.getNextNode(PipePathNode(MapDirection.S, 'F', MapPosition(0, 0))) should be(PipePathNode(MapDirection.W, 'X', MapPosition(0, 1)))
  }

  it should "return next node when it is -" in {
    val map = Seq(Seq('X', '-', 'Y'))
    val sut = PipeMap(mapAsMatrix = map, startNode = null)
    sut.getNextNode(PipePathNode(MapDirection.E, '-', MapPosition(0, 1))) should be(PipePathNode(MapDirection.E, 'X', MapPosition(0, 0)))
    sut.getNextNode(PipePathNode(MapDirection.W, '-', MapPosition(0, 1))) should be(PipePathNode(MapDirection.W, 'Y', MapPosition(0, 2)))
  }

  it should "return next node when it is J" in {
    val map = Seq(Seq('.', 'X'), Seq('Y', 'J'))
    val sut = PipeMap(mapAsMatrix = map, startNode = null)
    sut.getNextNode(PipePathNode(MapDirection.W, 'J', MapPosition(1, 1))) should be(PipePathNode(MapDirection.S, 'X', MapPosition(0, 1)))
    sut.getNextNode(PipePathNode(MapDirection.N, 'J', MapPosition(1, 1))) should be(PipePathNode(MapDirection.E, 'Y', MapPosition(1, 0)))
  }

  it should "return next node when it is 7" in {
    val map = Seq(Seq('X', '7'), Seq('.', 'Y'))
    val sut = PipeMap(mapAsMatrix = map, startNode = null)
    sut.getNextNode(PipePathNode(MapDirection.W, '7', MapPosition(0, 1))) should be(PipePathNode(MapDirection.N, 'Y', MapPosition(1, 1)))
    sut.getNextNode(PipePathNode(MapDirection.S, '7', MapPosition(0, 1))) should be(PipePathNode(MapDirection.E, 'X', MapPosition(0, 0)))
  }

  it should "return next node when it is |" in {
    val map = Seq(Seq('X'), Seq('|'), Seq('Y'))
    val sut = PipeMap(mapAsMatrix = map, startNode = null)
    sut.getNextNode(PipePathNode(MapDirection.N, '|', MapPosition(1, 0))) should be(PipePathNode(MapDirection.N, 'Y', MapPosition(2, 0)))
    sut.getNextNode(PipePathNode(MapDirection.S, '|', MapPosition(1, 0))) should be(PipePathNode(MapDirection.S, 'X', MapPosition(0, 0)))
  }

  it should "find all nodes to the left" in {
    val linesEither: Either[String, Seq[String]] = FileReaderImpl.readLinesFromFile("src/main/resources/day10_example.txt")

    linesEither.map {
      lines =>
        val sut = PipeMap.createPipeMapFromFile(lines)
        val positionsToTheLeft = sut.findAllMapPositionsToTheLeft
        println(s"positions to left: $positionsToTheLeft")
        positionsToTheLeft.size should be (4)
    }.orElse(fail())
  }

  it should "calculate character replaced by start node" in {
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('|'), Seq('S'), Seq('|'))) should be ('|')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('-', 'S', '7'))) should be ('-')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('|', '.'), Seq('S', '7'))) should be ('L')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('.', '|'), Seq('-', 'S'))) should be ('J')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('-', 'S'), Seq('.', 'L'))) should be ('7')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('S', 'J'), Seq('|', '.'))) should be ('F')
  }

  it should "calculate characters to the left" in {
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('|'), Seq('S'), Seq('|'))) should be('|')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('-', 'S', '7'))) should be('-')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('|', '.'), Seq('S', '7'))) should be('L')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('.', '|'), Seq('-', 'S'))) should be('J')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('-', 'S'), Seq('.', 'L'))) should be('7')
    PipeMap.getStartNodeReplacedCharacter(Seq(Seq('S', 'J'), Seq('|', '.'))) should be('F')
  }

}
