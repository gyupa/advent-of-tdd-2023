package advent.of.tdd.day15

import scala.annotation.tailrec


case class LensContainer(
                           instructions: Seq[Instruction]
                         ) {
  def calculateScore: Int = {
    (for (currentHashBoxPair <- boxes;
         currentLensSlot <- currentHashBoxPair._2.indices
         ) yield
      (currentHashBoxPair._1 + 1) * (currentLensSlot + 1) * currentHashBoxPair._2(currentLensSlot).focalLength
    ).sum
  }


  @tailrec
  private def buildMapRecursively(remainingInstructions: Seq[Instruction], currentLensContainer: Map[Int, Seq[Lens]]): Map[Int, Seq[Lens]] = {
    if (remainingInstructions.isEmpty)
      currentLensContainer
    else {
      val currentInstruction = remainingInstructions.head
      val currentLabelHash: Int = AsciiHash.calculateHash(currentInstruction.label)
      println(s"currentInstruction: $currentInstruction, currentLabelHash: $currentLabelHash")

      val newLensContainer =
        if (currentInstruction.operation == Operation.Add) {
          val lensToAdd = Lens(currentInstruction.label, currentInstruction.focalLength.get)

          if (currentLensContainer.contains(currentLabelHash)) {
            val box: Seq[Lens] = currentLensContainer(currentLabelHash)
            if (box.exists(lens => lens.label == lensToAdd.label)) {
              currentLensContainer.map(
                hashToSeqPair =>
                  if (hashToSeqPair._1 != currentLabelHash)
                    hashToSeqPair
                  else
                    hashToSeqPair._1 -> hashToSeqPair._2.map(
                      lens =>
                        if (lens.label == lensToAdd.label)
                          lensToAdd
                        else
                          lens
                    )
              )
            }
            else {
              println(s"  adding lens $lensToAdd to index $currentLabelHash")
              currentLensContainer + (currentLabelHash -> (box :+ lensToAdd))
            }
          } else {
            println(s"  adding lens $lensToAdd to index $currentLabelHash")
            currentLensContainer + (currentLabelHash -> Seq(lensToAdd))
          }
        } else {
          val labelToRemove = currentInstruction.label
          println(s"  removing lens label $labelToRemove from box $currentLabelHash")
          currentLensContainer.map(
            hashToSeqPair =>
              if (hashToSeqPair._1 != currentLabelHash)
                hashToSeqPair
              else
                hashToSeqPair._1 -> hashToSeqPair._2.filterNot(_.label == labelToRemove)
          )
        }

      println(s"newLensContainer: $newLensContainer")

      buildMapRecursively(remainingInstructions.tail, newLensContainer)
    }
  }

  val boxes: Map[Int, Seq[Lens]] = {
    buildMapRecursively(instructions, Map.empty[Int, Seq[Lens]])
  }
}

object LensContainer {
  def apply(instructionSequence: String): LensContainer = {
    val instructions = {
      val instructionsAsString = instructionSequence.split(",").toSeq
      instructionsAsString.map(
        instructionAsString =>
          if (instructionAsString.last == '-')
            Instruction(Operation.Remove, instructionAsString.dropRight(1), None)
          else {
            val parts = instructionAsString.split("=")
            Instruction(Operation.Add, parts(0), Some(parts(1).toInt))
          }
      )
    }
    LensContainer(instructions)
  }
}