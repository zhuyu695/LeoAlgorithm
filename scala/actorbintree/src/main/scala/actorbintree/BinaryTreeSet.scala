/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import akka.actor._
import scala.collection.immutable.Queue

object BinaryTreeSet {

  trait Operation {
    def requester: ActorRef
    def id: Int
    def elem: Int
  }

  trait OperationReply {
    def id: Int
  }

  /** Request with identifier `id` to insert an element `elem` into the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to check whether an element `elem` is present
    * in the tree. The actor at reference `requester` should be notified when
    * this operation is completed.
    */
  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to remove the element `elem` from the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request to perform garbage collection*/
  case object GC

  /** Holds the answer to the Contains request with identifier `id`.
    * `result` is true if and only if the element is present in the tree.
    */
  case class ContainsResult(id: Int, result: Boolean) extends OperationReply
  
  /** Message to signal successful completion of an insert or remove operation. */
  case class OperationFinished(id: Int) extends OperationReply

}


class BinaryTreeSet extends Actor {
  import BinaryTreeSet._
  import BinaryTreeNode._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = true))

  var root = createRoot

  // optional
  var pendingQueue = Queue.empty[Operation]

  // optional
  def receive = normal

  // optional
  /** Accepts `Operation` and `GC` messages. */
  val normal: Receive = { 
      case message: Operation => {
          root ! message
      }  
      case GC => {
          val dest = createRoot
          context.become(garbageCollecting(dest))
          root ! CopyTo(dest)
      }
  }

  // optional
  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive = {
      case GC => None
      case op: Operation => {
          pendingQueue.enqueue(op)
      }
      case CopyFinished => {
          root ! PoisonPill
          root = newRoot
          
          while (!pendingQueue.isEmpty) {
              val (message, lighterQueue) = pendingQueue.dequeue
              pendingQueue = lighterQueue
              root ! message
          }
          
          pendingQueue = Queue.empty[Operation]
          
          context.become(normal)
      }
  }

}

object BinaryTreeNode {
  trait Position

  case object Left extends Position
  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)
  case object CopyFinished

  def props(elem: Int, initiallyRemoved: Boolean) = Props(classOf[BinaryTreeNode],  elem, initiallyRemoved)
}

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor {
  import BinaryTreeNode._
  import BinaryTreeSet._

  var subtrees = Map[Position, ActorRef]()
  var removed = initiallyRemoved

  // optional
  def receive = normal

  // optional
  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive = {
      case message @ Insert(sender, id, elem) => {
          val done = OperationFinished(id)
          if (this.elem == elem) {
              this.removed = false
              sender ! done
          } else if (this.elem > elem) {
              subtrees.get(Left) match {
                  case Some(left) => left ! message
                  case None => {
                      subtrees = subtrees.updated(Left, newActor(id, elem))
                      sender ! done
                  }
              }
          } else if (this.elem < elem) {
              subtrees.get(Right) match {
                  case Some(right) => right ! message
                  case None => {
                      subtrees = subtrees.updated(Right, newActor(id, elem))
                      sender ! done
                  }
              }
          }
      }
      case message @ Contains(sender, id, elem) => {
          val notContained = ContainsResult(id, false)
          if (this.elem == elem) {
              if (this.removed == false) sender ! ContainsResult(id, true)
              else sender ! notContained
          } else if (this.elem > elem) {
              subtrees.get(Left) match {
                  case Some(left) => left ! message
                  case None => sender ! notContained
              }
          } else if (this.elem < elem) {
              subtrees.get(Right) match {
                  case Some(right) => right ! message
                  case None => sender ! notContained
              }
          }
      }
      case message @ Remove(sender, id, elem) => {
          val done = OperationFinished(id)
          if (this.elem == elem) {
              this.removed = true
              sender ! done
          } else if (this.elem < elem) {
              subtrees.get(Left) match {
                  case Some(left) => left ! message
                  case None => sender ! done
              }
          } else if (this.elem > elem) {
              subtrees.get(Right) match {
                  case Some(right) => right ! message
                  case None => sender ! done
              }
          }
      }
      case CopyTo(treeNode) => copy(treeNode)
  }
  
  def copy(destination: ActorRef): Unit = {
      val expected = (subtrees collect {case (_, child) => child}).toSet
      
      this.removed match {
          case true if expected.isEmpty => context.parent ! CopyFinished
          case _ => {
              if (this.removed == false) destination ! Insert(self, elem*(-1), elem)
              expected foreach {
                  child => child ! CopyTo(destination)
              }
          }
          val insertConfirmed = removed
          context.become(copying(expected, insertConfirmed))
      }
  }
  
  def newActor(id: Int, elem: Int): ActorRef = {
    val name = "node-i" + id + "e" + elem
    context.actorOf(BinaryTreeNode.props(elem, false), name)
  }

  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = {
      case message @ CopyFinished => {
          expected.size match {
              case 1 if insertConfirmed => context.parent ! CopyFinished
              case 0 if insertConfirmed => context.parent ! CopyFinished
              case _ => context.become(copying(expected - sender, insertConfirmed))
          }
      }
      case selfInsertDone @ OperationFinished(id) => {
          expected.size match {
              case 0 => context.parent ! CopyFinished
              case _ => context.become(copying(expected, true))
          }
      }
  }

}
