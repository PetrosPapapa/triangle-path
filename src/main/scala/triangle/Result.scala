package triangle

case class Result(path: List[Int]) {
  def +(node: Int): Result = copy(
    path = node :: path // we are going bottom-up so adding to the front
  )

  def pathToString: String = path.mkString(" + ")

  def cost: Int = path.sum

  def output: String = s"Minimal path is: $pathToString  = $cost"
}

object Result {
  def apply(node: Int): Result = Result(List(node))
}


