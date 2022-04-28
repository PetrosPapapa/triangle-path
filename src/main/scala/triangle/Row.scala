package triangle

import annotation.tailrec

case class Row(values: List[Int])

object Row {

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
  def fold(results: Seq[Path], row: Row): Seq[Path] = {
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
  def foldAll(rows: Seq[Row]): Seq[Path] = rows.headOption match {
    case None => Seq()
    case Some(r) => rows.tail.foldLeft(r.values.map(Path(_)))(fold)
  }

  /**
    * Calculates the minimal path of a triangle
    * 
    * Tail recursive version of [[foldAll]].
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
  def foldAllRec(rows: Seq[Row]): Seq[Path] = rows.headOption match {
    case None => Seq()
    case Some(r) => foldAllRecAcc(rows.tail, r.values.map(Path(_)))
  }

  @tailrec
  private def foldAllRecAcc(rows: Seq[Row], acc: Seq[Path] = Seq()): Seq[Path] = rows.headOption match {
    case None => acc
    case Some(r) => foldAllRecAcc(rows.tail, fold(acc, r))
  }
}

