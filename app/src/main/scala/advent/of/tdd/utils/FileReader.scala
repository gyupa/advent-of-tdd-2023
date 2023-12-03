package advent.of.tdd.utils

import scala.io.Source
import scala.util.Using

trait FileReader {
  def readLinesFromFile(path: String): Either[String, Seq[String]] = {
    Using(Source.fromFile(path)) {
      _.getLines().toSeq
    }.toEither.left.map{a => "Cannot read file"}
  }

}

object FileReaderImpl extends FileReader