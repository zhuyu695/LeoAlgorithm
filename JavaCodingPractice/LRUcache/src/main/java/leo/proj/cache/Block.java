package leo.proj.cache;

public class Block<V> {
	private V myValue;
	private Node<V> myNode;

	public Block(V value, Node<V> node) {
		myValue = value;
		myNode = node;
	}

	public Block(V value) {
		myValue = value;
	}

	public V getValue() {
		return myValue;
	}

	public Node<V> getNode() {
		return myNode;
	}

	public void setNode(Node<V> node) {
		myNode = node;
	}
}
