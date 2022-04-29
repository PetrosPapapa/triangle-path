package triangle

import cats.{ FlatMap, MonadError }
import cats.implicits.*

import cats.effect.std.Console
import cats.effect.implicits.*

import util.Try

trait Input {

  /**
    * Attempts to read a line from console and parse it as a [[Row]]
    * 
    * The function's type is manipulated to be used with `tailRecM`. 
    * This means that it is aimed at parsing one line at a time,
    * returning a `Left` of the accumulated rows, then a `Right` when
    * it has finished reading input.
    * 
    * Uses [[parse]] to parse the line, then adds it to a list of 
    * previously parsed rows and returns the result in a `Left`. 
    * 
    * Upon encountering EOF, it returns a `Right` of the sequenced result.
    * This means it returns `None` if any of the lines failed to parse.
    * 
    * It would be better to return information about which line failed
    * to parse specifically, but this would complicate the type of the
    * function even more so I avoided it for simplicity of code.
    * 
    * I also chose to use cats's `Console` which does not generally
    * expect to see an EOF and throws an exception (here caught by 
    * `orElse`), but it works fine for the purposes of the exercise.
    * 
    * @tparam F An effect type supporting `Console`, `FlatMap` 
    *           and `MonadError`
    * @return A function for parsing a sequence of lines from stdin
    *         using `tailRecM`.
    */
  def read[F[_] : FlatMap : Console](using e: MonadError[F, Throwable]): List[Option[Row]] => F[Either[List[Option[Row]], Option[List[Row]]]] = l =>
  Console[F].readLine
    .map { line => Left(parse(line) :: l)}
    .orElse(Right(l.sequence).pure[F])

  /**
    * Parses a line of numbers corresponding to a row of the triangle.
    * 
    * Assumes numbers are separated by whitespace.
    * 
    * @param line The line to parse.
    * @return The parsed [[Row]] or `None` if we failed
    *         to parse any of the numbers.
    */
  def parse(line: String): Option[Row] =
    line.trim
      .split("\\s+")
      .toList
      .traverse(s => Try(s.toInt).toOption) // if one fails, the entire result will be None
      .map(Row(_))

}
