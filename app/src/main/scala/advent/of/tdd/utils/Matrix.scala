package advent.of.tdd.utils

case class Matrix[T](rows: Seq[Seq[T]])  {

  assert(rows.map(_.size).toSet.size == 1)
  val numberOfRows: Int = rows.size
  val numberOfColumns: Int = rows.head.size
  val columns: Seq[Seq[T]] = rows.head.indices.map(i => rows.map(_(i)))

  def apply(rowIndex: Int, columnIndex: Int): T = rows(rowIndex)(columnIndex)

  override def toString: String = {
    rows.map(row => row.mkString(" ")).mkString("\r\n")
  }

}
object Matrix {
  def columnsToRows[T](columns: Seq[Seq[T]]): Seq[Seq[T]] = {
    columns.head.indices.map(i => columns.map(_(i)))
  }
}
