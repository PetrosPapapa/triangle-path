package triangle

import cats.implicits.*

import cats.effect.{ IO, IOApp, ExitCode }
import cats.effect.implicits.*

object Main extends IOApp with Input {

  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
      case Some(name) =>
        IO.println(s"Hello, $name.").as(ExitCode.Success)
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
