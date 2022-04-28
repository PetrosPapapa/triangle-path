package triangle

case class Result(path: String, cost: Int) {
  def +(node: Int): Result = copy(
    path = node.toString + " + " + path, // we are going bottom-up so adding to the front
    cost = cost + node
  )

  def output: String = s"Minimal path is: $path = $cost"
}

object Result {
  def apply(node: Int): Result = Result(node.toString, node)
}


