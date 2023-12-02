package advent.of.tdd.utils

import scala.io.Source

trait FileReader {
  def readLinesFromFile(path: String): Either[String, Seq[String]] = {
    try {
      Right(Source.fromResource(path).getLines().toSeq)
    } catch {
      case _ : Throwable => Left("Cannot read file")
    }
  }

}

object FileReaderImpl extends FileReader