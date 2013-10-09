package Algorithm;

import java.util.Stack;

public class LinkedListAlg {
	public class Node {
		public int value;
		public Node next;

		public Node() {
		}

		public Node(int val) {
			value = val;
			next = null;
		}

		public Node(int val, Node ne) {
			value = val;
			next = ne;
		}
	}

	/*-----------------------------reverse linked list using non-recursion----------------------*/
	public Node reverseListNonRecur(Node head) {
		if (head == null) {
			return null;
		} else if (head.next == null) {
			return head;
		}
		Node prev = head;
		Node cur = head.next;
		head.next = null;
		Node next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		return prev;
	}

	/*-----------------------------reverse linked list using recursion----------------------*/
	public Node reverseListRecur(Node head) {
		if (head == null) {
			return null;
		} else if (head.next == null) {
			return head;
		} else {
			Node next = head.next;
			head.next = null;
			return reverseRecurUtil(head, next);
		}
	}

	public Node reverseRecurUtil(Node prev, Node cur) {
		if (cur == null) {
			return prev;
		} else {
			Node newHead = reverseRecurUtil(cur, cur.next);
			cur.next = prev;
			return newHead;
		}
	}

	/*------------------------add 2 numbers--------------------*/
	/*6->1->7 + 2->9->5     <=>    617 + 295 */
	/*assume both length are the same, otherwise need do some padding*/
	public Node addNumberNode(Node num1, Node num2) {
		Stack<Integer> numS1 = new Stack<Integer>();
		Stack<Integer> numS2 = new Stack<Integer>();

		while (num1 != null) {
			numS1.add(num1.value);
		}

		while (num2 != null) {
			numS2.add(num2.value);
		}

		Node prev = null;
		Node cur = null;
		int sum = 0;
		int carry = 0;

		while (numS1.size() > 0 && numS2.size() > 0) {
			int a = numS1.pop();
			int b = numS2.pop();
			sum = (a + b) % 10;
			carry = (a + b) / 10;
			cur = new Node(sum);
			cur.next = prev;
			prev = cur;
		}
		if (carry > 0) {
			cur = new Node(carry);
			cur.next = prev;
		}
		return cur;
	}
}
