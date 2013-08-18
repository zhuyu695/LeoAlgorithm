package leo.proj.cache;

public class DoublyLinkedList<E> {
	private Node<E> head = new Node<E>(null);
	private Node<E> tail = new Node<E>(null);
	private int length = 0;

	//constructor
	DoublyLinkedList() {
		head.setPrev(null);
		head.setNext(tail);
		tail.setPrev(head);
		tail.setNext(null);
	}

	//get
	public Node<E> get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > length) {
			throw new IndexOutOfBoundsException();
		} else {
			Node<E> cursor = head;
			for (int i = 0; i < index; i++) {
				cursor = cursor.getNext();
			}
			return cursor;
		}
	}

	public Node<E> getTail() {
		return tail;
	}

	public Node<E> getHead() {
		return head;
	}

	//remove
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index == 0) {
			throw new IndexOutOfBoundsException();
		} else {
			Node<E> result = get(index);
			result.getNext().setPrev(result.getPrev());
			result.getPrev().setNext(result.getNext());
			length--;
			return result.getEntry().getValue();
		}
	}

	//add
	public void add(int index, Node<E> temp) throws IndexOutOfBoundsException {
		Node<E> cursor = get(index);
		temp.setPrev(cursor);
		temp.setNext(cursor.getNext());
		cursor.getNext().setPrev(temp);
		cursor.setNext(temp);
		length++;
	}

	public void addHead(Node<E> temp) {
		Node<E> cursor = head;
		temp.setPrev(cursor);
		temp.setNext(cursor.getNext());
		cursor.getNext().setPrev(temp);
		cursor.setNext(temp);
		length++;
	}

	public void addTail(Node<E> temp) {
		Node<E> cursor = tail.getPrev();
		temp.setPrev(cursor);
		temp.setNext(cursor.getNext());
		cursor.getNext().setPrev(temp);
		cursor.setNext(temp);
		length++;
	}

	public void deleteNode(Node<E> temp) {
		if (temp.equals(head.getNext())) {
			head.setNext(temp.getNext());
			temp.getNext().setPrev(head);
		} else if(temp.equals(tail)) {
			tail.setPrev(temp.getPrev());
		} else {
			temp.getPrev().setNext(temp.getNext());
			temp.getNext().setPrev(temp.getPrev());
		}
		length--;
	}

	//utils
	public int size() {
		return length;
	}

	public boolean isEmpty() {
		return length == 0;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("(head) - ");
		Node<E> temp = head;
		while (temp.getNext() != tail) {
			temp = temp.getNext();
			result.append(temp.getEntry().getValue() + " - ");
		}
		result.append("(tail)");
		return result.toString();
	}
}