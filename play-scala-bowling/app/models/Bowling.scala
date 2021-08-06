package bowling
import scala.util.Random.nextInt

/** Player could just be a string, but we may want to expand it so a case class works */
case class Player(name: String)
case class Frames(shots: List[List[Int]], scores: List[Int])
case class GameStats(player: String, frames: Frames)

/** The main class is for a player playing a game */
class PlayerGame(player: Player) {

  val shots = playGame
  val frames = new Frames(shots, calcScores(shots.map(_.reverse).toList, List(0)))
  val name = player.name
  val gameStats = new GameStats(name, frames)

/**
 * A game is split into the first nine frames which share the same rules
 * The final frame has different rules and is treated separately
 */
  def playGame() = {
    val frames9 = 0 to 8 map (_ => {
      val pinsDown1 = nextInt(10 + 1)
      if (pinsDown1 == 10) List(pinsDown1)
      else {
        val pinsDown2 = nextInt((10 - pinsDown1) + 1)
        pinsDown2 :: List(pinsDown1)
      }
    })

    /** The final frame is more complicated */
    def lastFrameShots(shots: List[Int]): List[Int] = {
      shots.size match {
        case 3 => shots
        case 2 => {
          if (shots.head == 10) lastFrameShots(nextInt(10 + 1) :: shots)
          if (shots.sum > 9)
            lastFrameShots(nextInt((10 - shots.head) + 1) :: shots)
          else shots
        }
        case 1 =>
          if (shots.head == 10) lastFrameShots(nextInt(10 + 1) :: shots)
          else lastFrameShots(nextInt((10 - shots.head) + 1) :: shots)
      }
    }

    (frames9 :+ lastFrameShots(List(nextInt(10 + 1)))).toList
  }

    /** Calculate the score for each frame */
    def calcScores(rawScores: List[List[Int]], scores: List[Int]): List[Int] = {
    if (rawScores.size > 0) {
      if (rawScores.head.size == 1)
        calcScores(
          rawScores.tail,
          10 + rawScores.tail.head.sum + scores.head :: scores
        )
      else if (rawScores.head.sum == 10)
        calcScores(
          rawScores.tail,
          10 + rawScores.tail.head.head + scores.head :: scores
        )
      else
        calcScores(rawScores.tail, rawScores.head.sum + scores.head :: scores)
    } else
      scores.reverse.drop(1) // Initiated the scores with a zero value which needs to be dropped
  }

}
