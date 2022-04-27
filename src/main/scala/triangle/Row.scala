package triangle

import cats.effect.std.Random
import cats.Applicative
import cats.implicits.*

import collection.immutable.Queue
import annotation.tailrec

case class Row(values: List[Int])

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


  @tailrec
  def linear(n: Int, q: Queue[Row] = Queue()): Queue[Row] = 
    if n <= 0 then q
    else linear(n-1, q :+ Row((1 to n).toList))

  def randomize[F[_] : Random : Applicative](rows: Seq[Row]): F[Seq[Row]] =
    rows.map(r => 
      Random[F].shuffleList(r.values).map(vs => r.copy(values = vs))
    ).sequence

  def gen[F[_] : Random : Applicative](n: Int): F[Seq[Row]] = 
    randomize(linear(n))

}

case class Result(path: String, cost: Int) {
  def +(node: Int): Result = copy(
    path = node.toString + " + " + path, // we are going bottom-up!
    cost = cost + node
  )
}

object Result {
  def apply(node: Int): Result = Result(node.toString, node)
}


