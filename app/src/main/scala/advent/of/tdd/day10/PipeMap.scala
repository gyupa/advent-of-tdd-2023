package advent.of.tdd.day10

import scala.annotation.tailrec

case class PipeMap(
                    mapAsMatrix: Seq[Seq[Char]],
                    startNode: PipePathNode
                  ) {
  def findAllMapPositionsToTheLeft: Set[MapPosition] = {
    val circle: Seq[PipePathNode] = findCircleFromStartNode

    println(circle)

    circle.flatMap(node => findPointsToTheLeft(node, circle)).toSet
  }

  def findAllNodesToTheRight: Set[MapPosition] = {
    val circle: Seq[PipePathNode] = findCircleFromStartNode

    circle.flatMap(node => findPointsToTheRight(node, circle)).toSet
  }

  def findPointsToTheLeft(node: PipePathNode, circle: Seq[PipePathNode]): Seq[MapPosition] = {
    val realCharacter =
      if (node == startNode)
        PipeMap.getStartNodeReplacedCharacter(mapAsMatrix)
      else
        node.character

    println(s"real char $realCharacter, direction left, node $node")

    (realCharacter, node.fromDirection) match {
      case ('-', MapDirection.E) =>
        findPointsToTheBottomFromNode(node.mapPosition, Seq.empty, circle)
      case ('-', MapDirection.W) =>
        findPointsToTheTopFromNode(node.mapPosition, Seq.empty, circle)
      case ('|', MapDirection.S) =>
        findPointsToTheLeftFromNode(node.mapPosition, Seq.empty, circle)
      case ('|', MapDirection.N) =>
        findPointsToTheRightFromNode(node.mapPosition, Seq.empty, circle)
      case ('F', MapDirection.S) =>
        findPointsToTheTopFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheLeftFromNode(node.mapPosition, Seq.empty, circle)
      case ('F', MapDirection.E) =>
        Seq.empty
      case ('L', MapDirection.N) =>
        Seq.empty
      case ('L', MapDirection.E) =>
        findPointsToTheBottomFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheLeftFromNode(node.mapPosition, Seq.empty, circle)
      case ('7', MapDirection.W) =>
        findPointsToTheTopFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheRightFromNode(node.mapPosition, Seq.empty, circle)
      case ('7', MapDirection.S) =>
        Seq.empty
      case ('J', MapDirection.N) =>
        findPointsToTheBottomFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheRightFromNode(node.mapPosition, Seq.empty, circle)
      case ('J', MapDirection.W) =>
        Seq.empty
    }

  }


  def findPointsToTheRight(node: PipePathNode, circle: Seq[PipePathNode]): Seq[MapPosition] = {
    val realCharacter =
      if (node == startNode)
        PipeMap.getStartNodeReplacedCharacter(mapAsMatrix)
      else
        node.character

    println(s"real char $realCharacter, direction right, node $node")

    (realCharacter, node.fromDirection) match {
      case ('-', MapDirection.W) =>
        findPointsToTheBottomFromNode(node.mapPosition, Seq.empty, circle)
      case ('-', MapDirection.E) =>
        findPointsToTheTopFromNode(node.mapPosition, Seq.empty, circle)
      case ('|', MapDirection.N) =>
        findPointsToTheLeftFromNode(node.mapPosition, Seq.empty, circle)
      case ('|', MapDirection.S) =>
        findPointsToTheRightFromNode(node.mapPosition, Seq.empty, circle)
      case ('F', MapDirection.E) =>
        findPointsToTheTopFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheLeftFromNode(node.mapPosition, Seq.empty, circle)
      case ('F', MapDirection.S) =>
        Seq.empty
      case ('L', MapDirection.E) =>
        Seq.empty
      case ('L', MapDirection.N) =>
        findPointsToTheBottomFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheLeftFromNode(node.mapPosition, Seq.empty, circle)
      case ('7', MapDirection.S) =>
        findPointsToTheTopFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheRightFromNode(node.mapPosition, Seq.empty, circle)
      case ('7', MapDirection.W) =>
        Seq.empty
      case ('J', MapDirection.W) =>
        findPointsToTheBottomFromNode(node.mapPosition, Seq.empty, circle) ++ findPointsToTheRightFromNode(node.mapPosition, Seq.empty, circle)
      case ('J', MapDirection.N) =>
        Seq.empty
    }

  }

  def findPointsToTheTopFromNode(currentPosition: MapPosition, pointsToTheTop: Seq[MapPosition], circle: Seq[PipePathNode]): Seq[MapPosition] = {
    if (currentPosition.row == 0)
      throw new IllegalStateException(s"this must be wrong direction, position: $currentPosition, direction top")
    else if (circle.exists(node => node.mapPosition == MapPosition(currentPosition.row - 1, currentPosition.column)))
      pointsToTheTop
    else
      findPointsToTheTopFromNode(
        currentPosition = MapPosition(currentPosition.row - 1, currentPosition.column),
        pointsToTheTop = pointsToTheTop :+ MapPosition(currentPosition.row - 1, currentPosition.column),
        circle = circle
      )
  }

  def findPointsToTheBottomFromNode(currentPosition: MapPosition, pointsToTheTop: Seq[MapPosition], circle: Seq[PipePathNode]): Seq[MapPosition] = {
    if (currentPosition.row == mapAsMatrix.size - 1)
      throw new IllegalStateException(s"this must be wrong direction, position: $currentPosition, direction bottom")
    else if (circle.exists(node => node.mapPosition == MapPosition(currentPosition.row + 1, currentPosition.column)))
      pointsToTheTop
    else
      findPointsToTheTopFromNode(
        currentPosition = MapPosition(currentPosition.row + 1, currentPosition.column),
        pointsToTheTop = pointsToTheTop :+ MapPosition(currentPosition.row + 1, currentPosition.column),
        circle = circle
      )
  }

  def findPointsToTheLeftFromNode(currentPosition: MapPosition, pointsToTheTop: Seq[MapPosition], circle: Seq[PipePathNode]): Seq[MapPosition] = {
    if (currentPosition.column == 0)
      throw new IllegalStateException(s"this must be wrong direction, position: $currentPosition, direction left")
    else if (circle.exists(node => node.mapPosition == MapPosition(currentPosition.row, currentPosition.column - 1)))
      pointsToTheTop
    else
      findPointsToTheLeftFromNode(
        currentPosition = MapPosition(currentPosition.row, currentPosition.column - 1),
        pointsToTheTop = pointsToTheTop :+ MapPosition(currentPosition.row, currentPosition.column - 1),
        circle = circle
      )
  }

  def findPointsToTheRightFromNode(currentPosition: MapPosition, pointsToTheTop: Seq[MapPosition], circle: Seq[PipePathNode]): Seq[MapPosition] = {
    if (currentPosition.column == mapAsMatrix.head.size - 1)
      throw new IllegalStateException(s"this must be wrong direction, position: $currentPosition, direction right")
    else if (circle.exists(node => node.mapPosition == MapPosition(currentPosition.row, currentPosition.column + 1)))
      pointsToTheTop
    else
      findPointsToTheRightFromNode(
        currentPosition = MapPosition(currentPosition.row, currentPosition.column + 1),
        pointsToTheTop = pointsToTheTop :+ MapPosition(currentPosition.row, currentPosition.column + 1),
        circle = circle
      )
  }

  def findCircleFromStartNode: Seq[PipePathNode] = {
    findCircle(getNextNode(startNode), Seq(startNode))
  }

  @tailrec
  private def findCircle(currentNode: PipePathNode, currentPath: Seq[PipePathNode]): Seq[PipePathNode] = {
    if (currentNode.character == 'S')
      currentPath :+ currentNode
    else
      findCircle(getNextNode(currentNode), currentPath :+ currentNode)
  }

  private def nextNode(fromDirection: MapDirection.Value, position: MapPosition, pipeMap: Seq[Seq[Char]]) = {
    PipePathNode(fromDirection = fromDirection, character = pipeMap(position.row)(position.column), position)
  }


  def getNextNode(node: PipePathNode): PipePathNode = {
    (node.character, node.fromDirection) match {
      case (c, MapDirection.E) if c == 'L' =>
        nextNode(MapDirection.S, MapPosition(node.mapPosition.row - 1, node.mapPosition.column), mapAsMatrix)
      case (c, MapDirection.N) if c == 'L' =>
        nextNode(MapDirection.W, MapPosition(node.mapPosition.row, node.mapPosition.column + 1), mapAsMatrix)
      case (c, MapDirection.E) if c == 'F' =>
        nextNode(MapDirection.N, MapPosition(node.mapPosition.row + 1, node.mapPosition.column), mapAsMatrix)
      case (c, MapDirection.S) if c == 'F' =>
        nextNode(MapDirection.W, MapPosition(node.mapPosition.row, node.mapPosition.column + 1), mapAsMatrix)
      case (c, MapDirection.E) if c == '-' =>
        nextNode(MapDirection.E, MapPosition(node.mapPosition.row, node.mapPosition.column - 1), mapAsMatrix)
      case (c, MapDirection.W) if c == '-' =>
        nextNode(MapDirection.W, MapPosition(node.mapPosition.row, node.mapPosition.column + 1), mapAsMatrix)
      case (c, MapDirection.W) if c == 'J' =>
        nextNode(MapDirection.S, MapPosition(node.mapPosition.row - 1, node.mapPosition.column), mapAsMatrix)
      case (c, MapDirection.N) if c == 'J' =>
        nextNode(MapDirection.E, MapPosition(node.mapPosition.row, node.mapPosition.column - 1), mapAsMatrix)
      case (c, MapDirection.W) if c == '7' =>
        nextNode(MapDirection.N, MapPosition(node.mapPosition.row + 1, node.mapPosition.column), mapAsMatrix)
      case (c, MapDirection.S) if c == '7' =>
        nextNode(MapDirection.E, MapPosition(node.mapPosition.row, node.mapPosition.column - 1), mapAsMatrix)
      case (c, MapDirection.S) if c == '|' =>
        nextNode(MapDirection.S, MapPosition(node.mapPosition.row - 1, node.mapPosition.column), mapAsMatrix)
      case (c, MapDirection.N) if c == '|' =>
        nextNode(MapDirection.N, MapPosition(node.mapPosition.row + 1, node.mapPosition.column), mapAsMatrix)
      case (c, d) if c == 'S' =>
        val neighbours: Map[MapDirection.Value, Option[MapPosition]] = PipeMap.getNeighbours(startNode, mapAsMatrix)
        val (toDirection, position): (MapDirection.Value, Option[MapPosition]) = neighbours.find(pair => pair._2.isDefined && pair._1 != startNode.fromDirection).get

        val nextNodeFromDirection = toDirection match {
          case MapDirection.E => MapDirection.W
          case MapDirection.W => MapDirection.E
          case MapDirection.N => MapDirection.S
          case MapDirection.S => MapDirection.N
        }

        PipePathNode(
          fromDirection = nextNodeFromDirection,
          character = mapAsMatrix(position.get.row)(position.get.column),
          mapPosition = position.get
        )

      case (c, d) =>
        throw new IllegalStateException(s"Map not valid: $c - $d")
    }
  }

}

object PipeMap {

  def readEasternNeighbour(position: MapPosition, pipeMapAsMatrixOfCharacters: Seq[Seq[Char]]): Option[MapPosition] = {
    val currentRow = pipeMapAsMatrixOfCharacters(position.row)

    if (currentRow.size > position.column + 1 && Set('-', 'J', '7', 'S').contains(currentRow(position.column + 1)))
      Some(MapPosition(position.row, position.column + 1))
    else
      None
  }

  def readWesternNeighbour(position: MapPosition, pipeMapAsMatrixOfCharacters: Seq[Seq[Char]]): Option[MapPosition] = {
    val currentRow = pipeMapAsMatrixOfCharacters(position.row)

    if (position.column > 0 && Set('-', 'L', 'F', 'S').contains(currentRow(position.column - 1)))
      Some(MapPosition(position.row, position.column - 1))
    else
      None
  }

  def readNorthernNeighbour(position: MapPosition, pipeMapAsMatrixOfCharacters: Seq[Seq[Char]]): Option[MapPosition] = {
    if (position.row == 0)
      None
    else if (Set('|', '7', 'F', 'S').contains(pipeMapAsMatrixOfCharacters(position.row - 1)(position.column)))
      Some(MapPosition(position.row - 1, position.column))
    else
      None
  }

  def readSouthernNeighbour(position: MapPosition, pipeMapAsMatrixOfCharacters: Seq[Seq[Char]]): Option[MapPosition] = {
    if (position.row == pipeMapAsMatrixOfCharacters.size - 1)
      None
    else if (Set('|', 'L', 'J', 'S').contains(pipeMapAsMatrixOfCharacters(position.row + 1)(position.column)))
      Some(MapPosition(position.row + 1, position.column))
    else
      None
  }


  def getStartNodePosition(pipeMapAsString: Seq[Seq[Char]]): MapPosition = {
    val row: Int = pipeMapAsString.indexWhere(row => row.contains('S'))
    MapPosition(
      row = row,
      column = pipeMapAsString(row).indexOf('S')
    )
  }

  def getNeighbours(node: PipePathNode, pipeMapAsString: Seq[Seq[Char]]): Map[MapDirection.Value, Option[MapPosition]] = {
    Map(
      MapDirection.E -> readEasternNeighbour(node.mapPosition, pipeMapAsString),
      MapDirection.S -> readSouthernNeighbour(node.mapPosition, pipeMapAsString),
      MapDirection.W -> readWesternNeighbour(node.mapPosition, pipeMapAsString),
      MapDirection.N -> readNorthernNeighbour(node.mapPosition, pipeMapAsString)
    )
  }

  def getStartNodeReplacedCharacter(pipeMapAsString: Seq[Seq[Char]]): Char = {
    val startNode = getStartNode(pipeMapAsString)
    val (neighbourDir1: MapDirection.Value, neighbourDir2: MapDirection.Value) = {
      val neighbours: Seq[MapDirection.Value] = getNeighbours(startNode, pipeMapAsString).filter(_._2.isDefined).keys.toSeq
      println(neighbours)
      (neighbours.head, neighbours(1))
    }
    (neighbourDir1, neighbourDir2) match {
      case (MapDirection.E, MapDirection.W) | (MapDirection.W, MapDirection.E) => '-'
      case (MapDirection.N, MapDirection.S) | (MapDirection.S, MapDirection.N) => '|'
      case (MapDirection.N, MapDirection.W) | (MapDirection.W, MapDirection.N) => 'J'
      case (MapDirection.N, MapDirection.E) | (MapDirection.E, MapDirection.N) => 'L'
      case (MapDirection.S, MapDirection.E) | (MapDirection.E, MapDirection.S) => 'F'
      case (MapDirection.S, MapDirection.W) | (MapDirection.W, MapDirection.S) => '7'
    }

  }

  def getStartNode(pipeMapAsString: Seq[Seq[Char]]): PipePathNode = {
    val position = getStartNodePosition(pipeMapAsString)

    val fromDirection =
      if (readEasternNeighbour(position, pipeMapAsString).isDefined)
        MapDirection.E
      else if (readSouthernNeighbour(position, pipeMapAsString).isDefined)
        MapDirection.S
      else if (readWesternNeighbour(position, pipeMapAsString).isDefined)
        MapDirection.W
      else
        MapDirection.N

    PipePathNode(
      fromDirection = fromDirection,
      character = pipeMapAsString(position.row)(position.column),
      mapPosition = position
    )
  }


  def createPipeMapFromFile(pipeMap: Seq[String]): PipeMap = {
    val pipeMapAsMatrixOfChars: Seq[Seq[Char]] = pipeMap.map(_.toCharArray.toSeq)

    val startNode = getStartNode(pipeMapAsMatrixOfChars)

    PipeMap(
      mapAsMatrix = pipeMapAsMatrixOfChars,
      startNode = startNode
    )
  }
}
