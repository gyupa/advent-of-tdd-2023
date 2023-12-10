package advent.of.tdd.day10

import advent.of.tdd.utils.FileReaderImpl

object Day10SolutionApp {

  def main(args: Array[String]): Unit = {
    FileReaderImpl.readLinesFromFile("src/main/resources/day10_input.txt") match {
      case Left(errorMessage) =>
        println(s"Error: $errorMessage")
      case Right(fileContents) =>
        val circle = PipeMap.createPipeMapFromFile(fileContents).findCircleFromStartNode
        println("Length of circle: " + circle.size)
        println("Length of path to farthest node: " + ((circle.size - 1) / 2))

        val nodesToTheRight = PipeMap.createPipeMapFromFile(fileContents).findAllNodesToTheRight
        println("nodes to one side: " + nodesToTheRight.size)

    }

  }

}
