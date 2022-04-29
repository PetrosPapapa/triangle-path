package triangle

import cats.implicits.*

import cats.effect.{ Resource, Sync }
import cats.effect.implicits.*

import annotation.tailrec
import io.Source
import util.Try

trait Input {

  /**
    * Reads all lines from stdin and attempts to parse a triangle
    * 
    * @tparam F A synchronous effect type
    * @return Some triangle if successfully parsed and validated
    *         otherwise `None`
    */

  def read[F[_] : Sync](): F[Option[Triangle]] =
    Resource
      .fromAutoCloseable(Sync[F].delay(Source.stdin))
      .use ( 
        _
          .getLines() // read all lines
          .toSeq
          .traverse(parse) // parse each row - fail if one fails
          .map(_.reverse) // we want the triangle inverted
          .filter(validate) // validate triangle
          .pure[F]
      )


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
    line
      .trim
      .split("\\s+")
      .toList
      .traverse(s => Try(s.toInt).toOption) // if one fails, the entire result will be None
      .map(Row(_))


  /**
    * Validates the structure of an (inverted) triangle.
    * 
    * @param triangle The triangle as a sequence of rows.
    * @return true if the structure is valid, false otherwise
    */
  def validate(triangle: Triangle): Boolean = validateRec(triangle, triangle.length)

  @tailrec
  private def validateRec(triangle: Triangle, length: Int): Boolean = triangle.headOption match {
    case None => true
    case Some(h) => if (h.values.length != length) then false else validateRec(triangle.tail, length - 1)
  }

}
