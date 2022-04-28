package triangle

case class Path(path: List[Int], cost: Int) {
  def +(node: Int): Path = copy(
    path = node :: path, // we are going bottom-up so adding to the front
    cost = cost + node
  )

  def pathToString: String = path.mkString(" + ")

  def output: String = s"Minimal path is: $pathToString  = $cost"
}

object Path {
  def apply(node: Int): Path = Path(List(node), node)
}


