package triangle

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RowTests extends AnyWordSpec with Matchers {

  "Row folding" should {

    "calculate costs for a row of 3" in {
      val row = Row(Seq(3,2,1))
      val results = Seq(Result(6), Result(7), Result(5), Result(4))
      
      Row.fold(results, row).map(_.cost) should be (Seq(9, 7, 5))
    }

  }
}
