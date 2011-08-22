package misc

object Queue {
  def apply[A](elements : A *) = new Queue[A](elements.toList)
}

class Queue[A] private (elements:List[A]) {
  def dequeue: (A, Queue[A]) = elements.head -> Queue(elements.tail :_*)
}