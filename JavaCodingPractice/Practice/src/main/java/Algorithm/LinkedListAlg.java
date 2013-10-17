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

	/*------------remove duplicate in sorted list------------*/
	public Node removeDup(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node cur = head;
		while (cur.next != null) {
			if (cur.value == cur.next.value)
				cur.next = cur.next.next;
			else
				cur = cur.next;
		}
		return head;
	}

	/*-----------reverse nodes in K group-------------*/
	/*For example,
	  Given this linked list: 1->2->3->4->5

	  For k = 2, you should return: 2->1->4->3->5

	  For k = 3, you should return: 3->2->1->4->5*/
	public Node reverseKGroup(Node head, int k) {
		Node prev;
		Node scan;
		Node tmp;
		Node guard = new Node();
		guard.next = head;
		head = guard;
		prev = head;
		scan = prev;
		int step = 0;

		while (scan != null) {
			step = k;
			while (scan != null && --step >= 0) {
				scan = scan.next;
			}
			if (scan == null) {
				break;
			}
			step = k - 1;
			scan = prev.next;
			while (--step >= 0 && scan.next != null) {
				tmp = scan.next;
				scan.next = scan.next.next;
				tmp.next = prev.next;
				prev.next = tmp;
			}
			prev = scan;
		}
		return head.next;
	}

	/*-----------Swap nodes in pairs--------------*/
	/*Given 1->2->3->4, you should return the list as 2->1->4->3.*/
	public Node swapInPair(Node head) {
		Node dummy = new Node();
		dummy.next = head;
		Node prev = dummy;
		Node tmp = head;
		while (prev.next != null && prev.next.next != null) {
			tmp = prev.next;
			prev.next = tmp.next;
			tmp.next = prev.next.next;
			prev.next.next = tmp;

			prev = tmp;
		}
		return dummy.next;
	}
}
