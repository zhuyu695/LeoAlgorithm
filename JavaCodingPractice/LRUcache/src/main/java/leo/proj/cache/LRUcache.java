package leo.proj.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LRUcache {
	private HashMap<Integer, Block<String>> myHashMap;
	private DoublyLinkedList<String> myList;
	private KeyValueGenerator vg;
	private Set<Node<String>> cleanUpSet;
	private int myCapacity;

	LRUcache(int capacity) {
		myHashMap = new HashMap<Integer, Block<String>>(capacity);
		myList = new DoublyLinkedList<String>();
		vg = new KeyValueGenerator();
		cleanUpSet = new HashSet<Node<String>>();
		myCapacity = capacity;
	}

	//Get
	public String get(int key) {
		if (myHashMap.containsKey(key)) {
			Block<String> b = myHashMap.get(key);
			offer(b);
			//try to purge some holes
			purge();
			return b.getValue();
		} else {
			//load value for key
			String v = vg.generateValue(key);
			Block<String> b = new Block<String>(v);
			put(b);
			return v;
		}
	}

	public void offer(Block<String> entry) {
		Node<String> tail = myList.getTail();
		if (!tail.getPrev().equals(entry)) {
			Node<String> lastNode = new Node<String>(entry);
			Node<String> oldNode = entry.getNode();
			if (oldNode != null) {
				oldNode.setValue(null);
				cleanUpSet.add(oldNode);
			}
			entry.setNode(lastNode);
			myList.addTail(lastNode);
		}
	}

	public void put(Block<String> value) {
		int key = vg.generateKey(value.getValue());
		if (!myHashMap.containsKey(key)) {
			myHashMap.put(key, value);
			offer(value);
			//if size exceeds, evict some entries
			if (myList.size() > myCapacity) {
				evict(myList.size() - myCapacity);
			}
		}
	}

	private void purge() {
		Node<String> iterNode = myList.getHead();
		while (iterNode.getNext() != null && !iterNode.getNext().equals(myList.getTail())) {
			if (cleanUpSet.contains(iterNode)) {
				myList.deleteNode(iterNode);
			}
			iterNode = iterNode.getNext();
		}
	}

	private void evict(int evictCount) {
		while (evictCount > 0) {
			Node<String> nodeForDelete = myList.getHead().getNext();
			int keyInHash = vg.generateKey(nodeForDelete.getEntry().getValue());
			myHashMap.remove(keyInHash);
			myList.deleteNode(nodeForDelete);
			--evictCount;
		}
	}

	public void printList() {
		System.out.println(myList);
	}
}
