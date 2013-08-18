package leo.proj.cache;

import org.junit.Test;

public class DoublyLinkedListTest {
	@Test
	public void testDoublyLinkedList() {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		list.addHead(new Node<Integer>(new Block<Integer>(1)));
		list.addHead(new Node<Integer>(new Block<Integer>(2)));
		list.addTail(new Node<Integer>(new Block<Integer>(9)));
		list.addHead(new Node<Integer>(new Block<Integer>(3)));
		list.addTail(new Node<Integer>(new Block<Integer>(11)));
		list.add(2, new Node<Integer>(new Block<Integer>(0)));
		System.out.println(list);
		list.remove(list.size());
		System.out.println(list);
	}
}
