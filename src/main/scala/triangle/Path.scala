package triangle

case class Path(path: List[Int], cost: Int) {

  /**
    * Adds a node to the front of the path
    * 
    * @param node The node to add.
    * @return The new path.
    */
  def +(node: Int): Path = copy(
    path = node :: path, // we are going bottom-up so adding to the front
    cost = cost + node
  )

  /** 
    *  A String representation of the path.
    */
  def pathToString: String = path.mkString(" + ")

  /**
    * Generates the required output for the calculated minimal path.
    */
  def output: String = s"Minimal path is: $pathToString  = $cost"
}

object Path {
  def apply(node: Int): Path = Path(List(node), node)
}


