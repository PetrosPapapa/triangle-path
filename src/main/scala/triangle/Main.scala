package triangle

import cats.implicits.*

import cats.effect.{ IO, IOApp, ExitCode }
import cats.effect.implicits.*
import cats.effect.std.Random

import util.Try

object Main extends IOApp with Input with RandomRow {

  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
 
     case Some(n) =>
        Try(n.toInt).toOption match {
          case None => IO.println("Invalid argument.").as(ExitCode.Error)
          case Some(i) => for {
            rand <- Random.scalaUtilRandom[IO]
            triangle <- gen(rand, i)
            solution = Row.foldAll(triangle)
            code <- solution match {
              case Seq() => IO.println("Empty triangle.").as(ExitCode.Error)
              case Seq(r) => IO.println(r.output).as(ExitCode.Success)
              case _ => IO.println("Invalid triangle.").as(ExitCode.Error)
            }
          } yield (code)
        }

      case None =>
        List().tailRecM[IO, Option[List[Row]]](read)
          .flatMap( _ match {
            case None => IO.println("Failed to parse.").as(ExitCode.Error)
            case Some(s) =>
              Row.foldAll(s) match {
                case Seq() => IO.println("Empty triangle.").as(ExitCode.Error)
                case Seq(r) => IO.println(r.output).as(ExitCode.Success)
                case _ => IO.println("Invalid triangle.").as(ExitCode.Error)
              }
          })
    }  

}
