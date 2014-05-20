package funsets

import common._

/**
 * 2. Purely Functional Sets.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): Set = {
    def single(e: Int): Boolean = e == elem
    single
  }

  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: Set, t: Set): Set = {
    def un(e: Int): Boolean = {
      if (contains(s, e) || contains(t, e)) true
      else false
    }
    un
  }

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: Set, t: Set): Set = {
    def inter(e: Int): Boolean = {
      if (contains(s, e)) contains(t, e)
      else false
    }
    inter
  }

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = {
    def df(e: Int): Boolean = {
      if (contains(s, e)) !contains(t, e)
      else contains(t, e)
    }
    df
  }

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, p: Int => Boolean): Set = {
    def ft(e: Int): Boolean = {
      if (contains(s, e)) p(e)
      else false
    }
    ft
  }

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (contains(s, a)) p(a) & iter(a + 1)
      else if (a > bound) true
      else iter(a + 1)
    }
    iter(-bound)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (filter(s, p)(a)) true
      else if (a > bound) false
      else iter(a + 1)
    }
    iter(-bound)
  }
  
  /**
   * utilize forall
   */
  def exists1(s: Set, p: Int => Boolean): Boolean = {
    return !forall(s, ((num: Int) => !p(num)))
  }

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: Set, f: Int => Int): Set = {
    def mp(a: Int): Set = {
      if (a > bound) (num: Int) => false
      else if (contains(s, a)) union(singletonSet(f(a)), mp(a + 1))
      else mp(a + 1)
    }
    mp(-bound)
  }
  
  def map1(s: Set, f: Int => Int): Set = {
    def mp(a: Int): Boolean = {
      contains(s, fnc(a, f))
    }
    mp
  }
  
  def fnc(a: Int, f: Int => Int): Int = {
    def inc(i: Int): Int = {
      if (i > bound) i
      else if (f(i) == a) i
      else inc(i + 1)
    }
    inc(-bound)
  }

  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
