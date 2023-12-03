package advent.of.tdd.utils

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileReaderTest extends AnyFlatSpec {

  "File reader" should "read empty file into empty sequence" in {
    FileReaderImpl.readLinesFromFile("main/resources/file_reader_test/empty_file.txt") should be(
      Right(Seq.empty)
    )
  }

  it should "read file ending with new line properly" in {
    FileReaderImpl.readLinesFromFile("main/resources/file_reader_test/file_ending_with_new_line.txt") should be(
      Right(Seq("one", "two", "three"))
    )
  }

  it should "read last line properly when file does not end with new line" in {
    FileReaderImpl.readLinesFromFile("main/resources/file_reader_test/file_not_ending_with_new_line.txt") should be(
      Right(Seq("one", "two", "three", "four"))
    )
  }

  it should "return failure if cannot read file" in {
    FileReaderImpl.readLinesFromFile("main/resources/non_existing_file.txt") should be(
      Left("Cannot read file")
    )
  }

}
