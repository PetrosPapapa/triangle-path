package triangle

import cats.effect.std.Random
import cats.Applicative
import cats.implicits.*

import collection.immutable.Queue
import annotation.tailrec

trait RandomTriangle {

  /**
    * Constructs a triangle of a given size with linear rows (1..n)
    *
    * @param n
    *   The size of the triangle.
    * @param q
    *   The rows generated so far.
    * @return
    *   The generated triangle.
    */
  @tailrec
  final def linear(n: Int, q: Queue[Row] = Queue()): Triangle =
    if n <= 0 then q.toSeq
    else linear(n - 1, q :+ Row((1 to n).toList))

  /**
    * Shuffles the nodes in each row of a triangle.
    *
    * @tparam F
    *   An effectful `Applicative``.
    * @param rand
    *   An instance of `Random` for the shuffling.
    * @param rows
    *   The triangle to shuffle.
    * @return
    *   The shuffled triangle.
    */
  def randomize[F[_] : Applicative](
      rand: Random[F],
      triangle: Triangle
  ): F[Triangle] =
    triangle.traverse(r =>
      rand.shuffleList(r.values).map(vs => r.copy(values = vs))
    )

  /**
    * Constructs a triangle of a given size with randomized rows of nodes (1..n)
    *
    * @tparam F
    *   An effectful `Applicative``.
    * @param rand
    *   An instance of `Random` for the shuffling.
    * @param n
    *   The size of the triangle.
    * @return
    *   The generated triangle.
    */
  def gen[F[_] : Applicative](rand: Random[F], n: Int): F[Triangle] =
    randomize(rand, linear(n))

}
