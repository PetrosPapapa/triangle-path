package triangle

import cats.{ FlatMap, MonadError }
import cats.implicits.*

import cats.effect.std.Console
import cats.effect.implicits.*

import util.Try

trait Input {

  def read[F[_] : FlatMap : Console](using e: MonadError[F, Throwable]): List[Option[Row]] => F[Either[List[Option[Row]], Option[List[Row]]]] = l =>
  Console[F].readLine
    .map { line => Left(parse(line) :: l)}
    .orElse(Right(l.sequence).pure[F])

  def parse(line: String): Option[Row] =
    line.split("\\s+")
      .toList
      .traverse(s => Try(s.toInt).toOption)
      .map(Row(_))

}
