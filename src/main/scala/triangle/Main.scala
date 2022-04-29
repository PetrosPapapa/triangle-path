package triangle

import cats.implicits.*

import cats.effect.{ IO, IOApp, ExitCode }
import cats.effect.implicits.*
import cats.effect.std.Random

import concurrent.duration.*
import util.Try

object Main extends IOApp with Input with RandomTriangle {

  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {

      case None =>
        for { // with no arguments, read from stdin
          triangle <- read[IO]() // read & parse
          code <- triangle match {
            case None => // at least 1 line failed
              IO.println("Failed to parse.").as(ExitCode.Error)
            case Some(s) =>
              Triangle.minPath(s) match { // Calculate the minimal path
                case None    => IO.println("Empty triangle.").as(ExitCode.Error)
                case Some(r) => IO.println(r.output).as(ExitCode.Success)
              }
          }
        } yield (code)

      case Some(n) => /* with an n: Int argument, generate a random triangle of
           * size n */
        Try(n.toInt).toOption match { // parse the argument as an Int
          case None => // Failed to parse argument
            IO.println("Invalid argument.").as(ExitCode.Error)
          case Some(i) =>
            for {
              rand <- Random.scalaUtilRandom[IO] // get a random generator
              triangle <- gen(rand, i) // generate a random triangle
              startTime <- IO.monotonic
              solution = Triangle.minPath(
                triangle
              ) // calculate the minimal path
              endTime <- IO.monotonic
              duration = endTime - startTime
              code <- solution match {
                case None => IO.println("Empty triangle.").as(ExitCode.Error)
                case Some(r) =>
                  IO.println(
                    s"Path cost: ${r.cost} - Duration: ${pretty(duration)}"
                  ).as(ExitCode.Success)
              }
            } yield (code)
        }

    }

  /**
    * Crude pretty printer for `FiniteDuration`
    */
  def pretty(d: FiniteDuration): String = {
    val m = d.toMinutes
    val s = (d - m.minutes).toSeconds
    val n = (d - m.minutes - s.seconds).toMillis
    s"${m}m${s}s${n}ms"
  }
}
