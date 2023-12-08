package advent.of.tdd.day6

case class BoatRace(
                     duration: Int,
                     currentRecord: Long
                   ) {
  def calculateNumberOfWaysToBeatRecord: Long = {
    (0 to duration).map(calculateDistance).count({
      distance =>
        distance > currentRecord
    })
  }

  def calculateDistance(buttonHoldTime: Int): Long = {
    buttonHoldTime.toLong * (duration - buttonHoldTime).toLong
  }
}


