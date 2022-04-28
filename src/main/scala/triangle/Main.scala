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
            case None => IO.println("No parse.").as(ExitCode.Error)
            case Some(s) => IO.println(s).as(ExitCode.Success)
          })
    }  

}
