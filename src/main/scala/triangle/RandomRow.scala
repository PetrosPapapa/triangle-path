package triangle

import cats.effect.std.Random
import cats.Applicative
import cats.implicits.*

import collection.immutable.Queue
import annotation.tailrec

trait RandomRow {

  @tailrec
  final def linear(n: Int, q: Queue[Row] = Queue()): Queue[Row] = 
    if n <= 0 then q
    else linear(n-1, q :+ Row((1 to n).toList))

  def randomize[F[_] : Applicative](rand: Random[F], rows: Seq[Row]): F[Seq[Row]] =
    rows.map(r => 
      rand.shuffleList(r.values).map(vs => r.copy(values = vs))
    ).sequence

  def gen[F[_] : Applicative](rand: Random[F], n: Int): F[Seq[Row]] = 
    randomize(rand, linear(n))

}

