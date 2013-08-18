package leo.proj.cache;

public class Node<T> {
	private Node<T> prev;
	private Node<T> next;
	private Block<T> value;

	//constructor
	Node(Block<T> value) {
		this.value = value;
	}

	Node(Block<T> value, Node<T> prev, Node<T> next) {
		this.value = value;
		setPrev(prev);
		setNext(next);
	}

	//Setter
	void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	void setNext(Node<T> next) {
		this.next = next;
	}

	//Getter
	Node<T> getPrev() {
		return prev;
	}

	Node<T> getNext() {
		return next;
	}

	Block<T> getEntry() {
		return value;
	}

	public void setValue(Block<T> v) {
		value = v;
	}
}
