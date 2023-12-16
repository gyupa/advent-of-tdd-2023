package advent.of.tdd.day16

import advent.of.tdd.day16.Direction._
import advent.of.tdd.utils.Matrix

import scala.annotation.tailrec


case class Contraption(
                        area: Matrix[Char]
                      ) {
  private type Position = (Int, Int)
  private type Route = Seq[(Direction.Value, Position)]

  def findCoveredTiles: Int = {
    val routes: Seq[Route] = findRoutesRecursively(Seq(Seq((Right, (0, 0)))), Seq.empty, Set((Right, (0, 0))), 1)
    val positions: Seq[(Int, Int)] = routes.flatMap(route => route.map(_._2))
    positions.toSet.size
  }

  @tailrec
  private def findRoutesRecursively(
                                      currentRoutes: Seq[Route],
                                      completedRoutes: Seq[Route],
                                      tilesAndDirectionsCovered: Set[(Direction.Value, (Int, Int))],
                                      iteration: Long): Seq[Route] = {
    if (currentRoutes.isEmpty) {
      completedRoutes
    } else {

      val newRoutesWithCompletion: Seq[(Route, Boolean)] =
        currentRoutes.flatMap {
          currentRoute =>
            val currentDirectionAndPosition = currentRoute.last
            val nextDirectionsAndPositions: Seq[(Direction.Value, (Int, Int))] = step(currentDirectionAndPosition._1, currentDirectionAndPosition._2)

            if (nextDirectionsAndPositions.isEmpty || nextDirectionsAndPositions.forall(tilesAndDirectionsCovered.contains)) {
              Seq((currentRoute, true)) // current route got completed
            }
            else {
              val result: Seq[(Route, Boolean)] = nextDirectionsAndPositions.filterNot(tilesAndDirectionsCovered.contains).map(currentRoute :+ _).map(_ -> false)
              result
            }
        }

      val (newCompletedRoutes: Seq[Route], newRoutes: Seq[Route]) = {
        val (cr, nr) = newRoutesWithCompletion.partition(_._2)
        (cr.map(_._1), nr.map(_._1))
      }
      val newTilesAndDirectionsDiscovered = newRoutes.map(_.last).toSet

      if (iteration % 10 == 0)
        println(s"i=$iteration, new routes: ${newRoutes.size}, newly completed: ${newCompletedRoutes.size}")

      findRoutesRecursively(newRoutes, completedRoutes ++ newCompletedRoutes, tilesAndDirectionsCovered ++ newTilesAndDirectionsDiscovered, iteration + 1)
    }
  }


  def step(direction: Direction.Value, position: Position): Seq[(Direction.Value, Position)] = {
    val characterAtPosition = area(position._1, position._2)

    (direction, characterAtPosition) match {
      case (Right, '.') if isInLastColumn(position) => Seq.empty
      case (Left, '.') if isInFirstColumn(position) => Seq.empty
      case (Down, '.') if isInLastRow(position) => Seq.empty
      case (Up, '.') if isInFirstRow(position) => Seq.empty
      case (Right, '-') if isInLastColumn(position) => Seq.empty
      case (Left, '-') if isInFirstColumn(position) => Seq.empty
      case (Down, '|') if isInLastRow(position) => Seq.empty
      case (Up, '|') if isInFirstRow(position) => Seq.empty
      case (Right, '\\') if isInLastRow(position) => Seq.empty
      case (Left, '\\') if isInFirstRow(position) => Seq.empty
      case (Up, '\\') if isInFirstColumn(position) => Seq.empty
      case (Down, '\\') if isInLastColumn(position) => Seq.empty
      case (Right, '/') if isInFirstRow(position) => Seq.empty
      case (Left, '/') if isInLastRow(position) => Seq.empty
      case (Up, '/') if isInLastColumn(position) => Seq.empty
      case (Down, '/') if isInFirstColumn(position) => Seq.empty
      case (Right, '.') => Seq((Right, (position._1, position._2 + 1)))
      case (Left, '.') => Seq((Left, (position._1, position._2 - 1)))
      case (Down, '.') => Seq((Down, (position._1 + 1, position._2)))
      case (Up, '.') => Seq((Up, (position._1 - 1, position._2)))
      case (Right, '-') => Seq((Right, (position._1, position._2 + 1)))
      case (Left, '-') => Seq((Left, (position._1, position._2 - 1)))
      case (Down, '|') => Seq((Down, (position._1 + 1, position._2)))
      case (Up, '|') => Seq((Up, (position._1 - 1, position._2)))
      case (Right, '\\') => Seq((Down, (position._1 + 1, position._2)))
      case (Left, '\\') => Seq((Up, (position._1 - 1, position._2)))
      case (Up, '\\') => Seq((Left, (position._1, position._2 - 1)))
      case (Down, '\\') => Seq((Right, (position._1, position._2 + 1)))
      case (Right, '/') => Seq((Up, (position._1 - 1, position._2)))
      case (Left, '/') => Seq((Down, (position._1 + 1, position._2)))
      case (Up, '/') => Seq((Right, (position._1, position._2 + 1)))
      case (Down, '/') => Seq((Left, (position._1, position._2 - 1)))
      case (Right, '|') | (Left, '|') if isInFirstRow(position) => Seq((Down, (position._1 + 1, position._2)))
      case (Right, '|') | (Left, '|') if isInLastRow(position) => Seq((Up, (position._1 - 1, position._2)))
      case (Right, '|') | (Left, '|') => Seq((Up, (position._1 - 1, position._2)), (Down, (position._1 + 1, position._2)))
      case (Up, '-') | (Down, '-') if isInLastColumn(position) => Seq((Left, (position._1, position._2 - 1)))
      case (Up, '-') | (Down, '-') if isInFirstColumn(position) => Seq((Right, (position._1, position._2 + 1)))
      case (Up, '-') | (Down, '-') => Seq((Left, (position._1, position._2 - 1)), (Right, (position._1, position._2 + 1)))
    }
  }

  private def isInLastColumn(position: (Int, Int)) = {
    position._2 == area.numberOfColumns - 1
  }

  private def isInFirstColumn(position: (Int, Int)) = {
    position._2 == 0
  }

  private def isInLastRow(position: (Int, Int)) = {
    position._1 == area.numberOfRows - 1
  }

  private def isInFirstRow(position: (Int, Int)) = {
    position._1 == 0
  }
}

object Contraption {
  def readFromLines(lines: Seq[String]): Contraption = {
    val matrix =
      Matrix(
        lines.map(line => line.indices.map(line(_)))
      )
    Contraption(matrix)
  }
}
