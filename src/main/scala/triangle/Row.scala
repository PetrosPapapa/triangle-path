package triangle

case class Row(values: Seq[Int])

object Row {
  def fold(results: Seq[Result], row: Row): Seq[Result] = {
    results
      .sliding(2)
      .toSeq // conversion will happen sooner or later
      .map(_.minBy(_.cost))
      .lazyZip(row.values)
      .map(_ + _)
  }

  def foldAll(rows: Seq[Row]): Seq[Result] = rows.headOption match {
    case None => Seq()
    case Some(r) => rows.tail.foldLeft(r.values.map(Result(_)))(fold)
  }
}

case class Result(description: String, cost: Int) {
  def +(node: Int): Result = copy(
    description = node.toString + " + " + description, // we are going bottom-up!
    cost = cost + node
  )
}

object Result {
  def apply(node: Int): Result = Result(node.toString, node)
}


