package advent.of.tdd.day6

object Day6SolutionApp {

  def main(args: Array[String]): Unit = {
    println(
      BoatRace(
        duration = 56977793, currentRecord = 499221010971440L
      ).calculateNumberOfWaysToBeatRecord
    )

  }
}
