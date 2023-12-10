package advent.of.tdd.day8

import scala.annotation.tailrec

object Direction extends Enumeration {
  val L, R = Value
}

case class DesertMap(
                      instructions: Seq[Direction.Value],
                      nodesById: Map[String, Node]
                    ) {

  val lazyListOfInstructionsWithIndex: Seq[(Direction.Value, Int)] = LazyList.continually(instructions).flatten.zipWithIndex

  private val betterInstructions = Instructions(instructions)

  def getNumberOfStepsFromStartToEndFromSingleStartNode: Map[Node, Seq[(Node, Long)]] = {
    val startNodes = nodesById.filter(_._2.id.endsWith("A")).values.toSeq
    startNodes.map(node => node -> findEndStatesForSingleNode(node, 0, Seq.empty)).toMap
  }


  def getNumberOfStepsFromStartToAnyEndStatesFromSingleStartNode: Int = {
    findAnyEndState(nodesById("AAA"), lazyListOfInstructionsWithIndex)._2
  }


  private def isEndNode(node: Node) = node.id.endsWith("Z")



  @tailrec
  private def findAnyEndState(currentNode: Node, instructionsWithIndex: Seq[(Direction.Value, Int)]): (Node, Int) = {
    if (currentNode.id.endsWith("Z"))
      (currentNode, instructionsWithIndex.head._2)
    else {
      val nextNode = executeStep(currentNode, instructionsWithIndex.head._1)
      findAnyEndState(nextNode, instructionsWithIndex.tail)
    }
  }


  def getNumberOfStepsFromStartToEndFromAllStartNodes: Long = {
    val startNodes = nodesById.filter(_._2.id.endsWith("A")).values.toSeq
    println("Number of start nodes " + startNodes.size)
    findEndStates(startNodes, betterInstructions, 0)
  }

  @tailrec
  private def findEndStates(currentNodes: Seq[Node], instructions: Instructions, stepNumber: Long): Long = {

    if (stepNumber % 10000000 == 0) println(s"Executing step $stepNumber, current nodes: $currentNodes")
    if (currentNodes.forall(_.id.endsWith("Z")))
      stepNumber
    else {
      val nextNodes = currentNodes.map(currentNode => executeStep(currentNode, instructions.getDirection(stepNumber)))
      findEndStates(nextNodes, instructions, stepNumber + 1)
    }
  }


  @tailrec
  private def findEndStatesForSingleNode(currentNode: Node, stepNumber: Long, endStatesWithStepNumbers: Seq[(Node, Long)]): Seq[(Node, Long)] = {

    if (isEndNode(currentNode)) println(s"end node found at step number $stepNumber: $currentNode")
    if (stepNumber == 1000000) endStatesWithStepNumbers
    else {
      val nextNode: Node = executeStep(currentNode, betterInstructions.getDirection(stepNumber))
      findEndStatesForSingleNode(
        nextNode,
        stepNumber + 1,
        if (isEndNode(currentNode)) endStatesWithStepNumbers :+ (currentNode, stepNumber) else endStatesWithStepNumbers)
    }
  }


  private def executeStep(from: Node, step: Direction.Value): Node = {
    if (step == Direction.L)
      nodesById(from.leftNodeId)
    else
      nodesById(from.rightNodeId)
  }
}

object DesertMap {
  def createMapFromLines(lines: Seq[String]): DesertMap = {
    val instructions: Seq[Direction.Value] = lines.head.map(char => Direction.withName(char.toString))

    val nodes: Seq[Node] = {
      val linePattern = "(.+) = \\((.+), (.+)\\)".r
      lines.tail.tail.map {
        line =>
          val currentLineMatch = linePattern.findFirstMatchIn(line).get
          Node(currentLineMatch.group(1), currentLineMatch.group(2), currentLineMatch.group(3))
      }
    }

    val nodesById: Map[String, Node] = {
      val groupedNodes: Map[String, Set[Node]] = nodes.toSet.groupBy(_.id)
      groupedNodes.map(pair => (pair._1, pair._2.head))
    }

    DesertMap(
      instructions = instructions,
      nodesById = nodesById
    )

  }
}
