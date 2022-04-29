package triangle

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.OptionValues

class InputTests
    extends AnyWordSpec
    with Matchers
    with OptionValues
    with Input {

  "Line parsing" should {

    "parse a normal line" in {
      parse("1 2 3 4 5").value.values should be(List(1, 2, 3, 4, 5))
    }

    "parse a line with various whitespace" in {
      parse("   1 2\t 3\t\t\t4  5    \t").value.values should be(
        List(1, 2, 3, 4, 5)
      )
    }

    "parse a line with negative numbers" in {
      parse("   1 -2\t 3\t\t\t-4  5    \t").value.values should be(
        List(1, -2, 3, -4, 5)
      )
    }

    "parse a line with just 1 number" in {
      parse("   \t 3\t\t\t      \t").value.values should be(List(3))
    }

    "not parse a line with letters" in {
      parse("   1 2\t 3\t\t\tx  5    \t") should be(None)
    }

    "not parse a line with only whitespace" in {
      parse("   \t \t\t\t      \t") should be(None)
    }

    "not parse an empty line" in {
      parse("") should be(None)
    }
  }

  "validate" should {
    "succeed on a valid triangle with 4-rows" in {
      validate(Seq(r(1, 2, 3, 4), r(5, 6, 7), r(8, 9), r(10))) should be(true)
    }

    "succeed on an empty triangle" in {
      validate(Seq()) should be(true)
    }

    "fail on an inverted triangle with 2-rows" in {
      validate(Seq(r(1), r(2, 3))) should be(false)
    }

    "fail on a head-less triangle with 2 rows" in {
      validate(Seq(r(1, 2), r(3, 4, 5))) should be(false)
    }

    "fail on a 2x2 square" in {
      validate(Seq(r(1, 2), r(3, 4))) should be(false)
    }

  }

  def r(vs: Int*): Row = Row(vs.toList)

}
