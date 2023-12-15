package advent.of.tdd.day15

import scala.annotation.tailrec

object AsciiHash {

  def calculateHash(string: String): Int = {
    calculateHashRecursively(string, 0)
  }

  @tailrec
  private def calculateHashRecursively(remainingString: String, currentHash: Int): Int = {
    if (remainingString.isEmpty)
      currentHash
    else {
      val ch = remainingString.head
      val newValue = (ch.toInt + currentHash) * 17 % 256
      calculateHashRecursively(remainingString.tail, newValue)
    }
  }
}
