package Algorithm;

public class LinkedListAlg {
	public class Node {
		public int value;
		public Node next;
		
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
}
