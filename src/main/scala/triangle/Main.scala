package triangle

import cats.implicits.*

import cats.effect.{ IO, IOApp, ExitCode }
import cats.effect.implicits.*
import cats.effect.std.Random

import util.Try

object Main extends IOApp with Input with RandomRow {

  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {

      case None => // with no arguments, read from stdin
        List().tailRecM[IO, Option[List[Row]]](read) // read & parse
          .flatMap( _ match {
            case None => // at least 1 line failed
              IO.println("Failed to parse.").as(ExitCode.Error) 
            case Some(s) =>
              if (!validate(s)) 
              then IO.println("Invalid triangle.").as(ExitCode.Error)
              else Row.foldAllRec(s) match { // Calculate the minimal path
                case Seq() => IO.println("Empty triangle.").as(ExitCode.Error)
                case Seq(r) => IO.println(r.output).as(ExitCode.Success)
                case _ => IO.println("Invalid triangle.").as(ExitCode.Error)
              }
          })

     case Some(n) => // with an n: Int argument, generate a random triangle of size n
        Try(n.toInt).toOption match { // parse the argument as an Int
          case None => // Failed to parse argument
            IO.println("Invalid argument.").as(ExitCode.Error)
          case Some(i) => for {
            rand <- Random.scalaUtilRandom[IO] // get a random generator
            triangle <- gen(rand, i) // generate a random triangle
            solution = Row.foldAllRec(triangle) // calculate the minimal path
            code <- solution match {
              case Seq() => IO.println("Empty triangle.").as(ExitCode.Error)
              case Seq(r) => IO.println(r.output).as(ExitCode.Success)
              case _ => IO.println("Invalid triangle.").as(ExitCode.Error)
            }
          } yield (code)
        }

    }  

}
