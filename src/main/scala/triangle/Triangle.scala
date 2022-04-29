package triangle

import annotation.tailrec

case class Row(values: List[Int])

type Triangle = Seq[Row]

object Triangle {

  /**
    * Folds minimal path calculations upwards in the triangle
    * 
    * Starting from the [[Path]]s of the previous (lower) row, 
    * we calculate the minimal step from the current row.
    * 
    *  @param results the result of the previous row
    *  @param row the current row
    *  @return the minimal paths for the current row
    */
  def foldRow(results: Seq[Path], row: Row): Seq[Path] = {
    results
      .sliding(2) // pairs of possible paths in the next row
      .toSeq // conversion will happen sooner or later
      .map(_.minBy(_.cost)) // get the path with the minimum cost
      .lazyZip(row.values) // associate it with the node in the currrent row
      .map(_ + _) // add the current node to the Path
  }

  /**
    * Calculates the minimal path of a triangle
    * 
    * Folds all rows in the triangle using [[fold]].
    * 
    * Returns an empty `Seq` if given an empty triangle.
    * 
    * May return multiple results in the sequence if given an invalid
    * triangle.
    * 
    * @note Expects the rows in reverse order (bottom row first).
    * @param rows The rows of the triangle (bottom row first).
    * @return A sequence containing the minimal path.
    */
  def minPath(triangle: Triangle): Option[Path] = triangle.headOption.flatMap { r =>
    foldAll(triangle.tail, r.values.map(Path(_)))
  }

  @tailrec
  private def foldAll(rows: Seq[Row], acc: Seq[Path] = Seq()): Option[Path] = rows.headOption match {
    case None => acc.headOption
    case Some(r) => foldAll(rows.tail, foldRow(acc, r))
  }
}

