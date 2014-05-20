package example

object MainWeek1 extends App {
	//pascal triangle
    def pascal(c: Int, r: Int): Int =
  		if (c == 0 || c == r) return 1
  		else return pascal(c - 1, r - 1) + pascal(c, r - 1)
  		
  	//check parenthesis
	def balance(chars: List[Char]): Boolean = {
		def check(cs: List[Char], leftPara: Int): Boolean = {
			if (leftPara < 0) return false
			if (cs.isEmpty)
				if (leftPara == 0) return true
				else return false
			if (cs.head == '(') return check(cs.tail, leftPara + 1)
			else if (cs.head == ')') return check(cs.tail, leftPara - 1)
			else return check(cs.tail, leftPara)
		}
		return check(chars, 0)
	}
    
    //counting change
	def countChange(money: Int, coins: List[Int]): Int = {
		if (money == 0) 1
		else if (money < 0) 0
		else if (coins.isEmpty) 0
		else countChange(money - coins.head, coins) + countChange(money, coins.tail)
	}
}