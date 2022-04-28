package triangle

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.LoneElement

class RowTests extends AnyWordSpec with Matchers with LoneElement {

  "Row folding" should {

    "calculate costs for a row of 3" in {
      val row = Row(List(3,2,1))
      val results = Seq(Result(6), Result(7), Result(5), Result(4))
      
      Row.fold(results, row).map(_.cost) should be (Seq(9, 7, 5))
    }

  }


  "Row.foldAll" should {

    "calculate the example" in {
      val rows = Seq(
        Row(List(11,2,10,9)),
        Row(List(3,8,5)),
        Row(List(6,3)),
        Row(List(7)),
      )

      Row.foldAll(rows).loneElement should be (Result(List(7, 6, 3, 2)))
    }

  }
}
