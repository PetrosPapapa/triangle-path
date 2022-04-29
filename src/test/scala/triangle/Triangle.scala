package triangle

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.OptionValues

class TriangleTests extends AnyWordSpec with Matchers with OptionValues {

  "Row folding" should {

    "give an empty result for an empty row" in {
      val row = Row(List())
      val results = Seq(Path(6), Path(7))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq())
    }

    "calculate cost for a row of 1" in {
      val row = Row(List(1))
      val results = Seq(Path(6), Path(7))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq(7))
    }

    "calculate costs for a row of 2" in {
      val row = Row(List(1, 2))
      val results = Seq(Path(6), Path(7), Path(6))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq(7, 8))
    }

    "calculate costs for a row of 3" in {
      val row = Row(List(3, 2, 1))
      val results = Seq(Path(6), Path(7), Path(5), Path(4))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq(9, 7, 5))
    }

    "ignore paths if the row is too short" in { /* it would be better to throw
     * some error here, */
      // but we assume this will never happen
      val row = Row(List(3, 2, 1))
      val results =
        Seq(Path(6), Path(7), Path(5), Path(1), Path(1), Path(1), Path(1))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq(9, 7, 2))
    }

    "ignore part of the the row if the list of paths is too short" in {
      // it would be better to throw some error here,
      // but we assume this will never happen
      val row = Row(List(3, 2, 1, 1, 1, 1, 1))
      val results = Seq(Path(6), Path(7), Path(5))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq(9, 7))
    }

    "work with negative numbers" in {
      val row = Row(List(3, -2, 1))
      val results = Seq(Path(6), Path(-7), Path(5), Path(-4))

      Triangle.foldRow(results, row).map(_.cost) should be(Seq(-4, -9, -3))
    }

  }

  "Triangle.foldRowAllRec" should {

    "calculate the example" in {
      val rows = Seq(
        Row(List(11, 2, 10, 9)),
        Row(List(3, 8, 5)),
        Row(List(6, 3)),
        Row(List(7))
      )

      Triangle.minPath(rows).value should be(Path(List(7, 6, 3, 2), 18))
    }

    "give an empty result for an empty triangle" in {
      val rows = Seq()

      Triangle.minPath(rows) should be(None)
    }

    "work for a triangle with 1 row" in {
      val rows = Seq(
        Row(List(7))
      )

      Triangle.minPath(rows).value should be(Path(List(7), 7))
    }

    "work for a triangle with 2 rows" in {
      val rows = Seq(
        Row(List(8, 2)),
        Row(List(7))
      )

      Triangle.minPath(rows).value should be(Path(List(7, 2), 9))
    }

    "work for a triangle with 3 rows" in {
      val rows = Seq(
        Row(List(10, 0, 10)),
        Row(List(8, 2)),
        Row(List(7))
      )

      Triangle.minPath(rows).value should be(Path(List(7, 2, 0), 9))
    }

    "treat an inverted triangle like a line" in { /* it would be better to throw
     * some error here, */
      // but we assume this will never happen
      val rows = Seq(
        Row(List(7)),
        Row(List(8, 2)),
        Row(List(10, 0, 10))
      )

      Triangle.minPath(rows).value should be(Path(List(10, 8, 7), 25))
    }

    "give some path for an invalid triangle" in { /* it would be better to throw
     * some error here, */
      // but we assume this will never happen
      val rows = Seq(
        Row(List(10, 0, 10, 0)),
        Row(List(8, 2, 4)),
        Row(List(7, 3))
      )

      Triangle.minPath(rows).isDefined should be(true)
    }
  }
}
