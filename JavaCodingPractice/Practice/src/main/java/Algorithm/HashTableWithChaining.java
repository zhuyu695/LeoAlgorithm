package Algorithm;

import java.util.LinkedList;
import java.util.Random;

public class HashTableWithChaining<K, V> {
	public class Cell<KK, VV> {
		public KK key;
		public VV value;
		
		public Cell(KK ky, VV ve) {
			key = ky;
			value = ve;
		}
	}
	
	LinkedList<Cell<K, V>>[] items;
	public static final int capacity = 100;
	
	@SuppressWarnings("unchecked")
	public HashTableWithChaining() {
		items = (LinkedList<Cell<K, V>>[]) new LinkedList[capacity];
	}
	
	public int hashCodeOfKey(K key) {
		Random rand = new Random();
		return (key.toString().length() * rand.nextInt())  % items.length;
	}
	
	public void put (K key, V value) {
		int hashCode = hashCodeOfKey(key);
		if (items[hashCode] == null) {
			items[hashCode] = new LinkedList<Cell<K,V>>();
		}
		LinkedList<Cell<K, V>> collided = items[hashCode];
		for (Cell<K, V> c : collided) {
			if (c.key.equals(key)) {
				collided.remove(key);
				break;
			}
		}
		items[hashCode].add(new Cell<K, V>(key, value));
	}
	
	public V get(K key) {
		int hashCode = hashCodeOfKey(key);
		if (items[hashCode] == null) {
			return null;
		}
		LinkedList<Cell<K, V>> found = items[hashCode];
		for (Cell<K, V> c : found) {
			if (c.key.equals(key))
				return c.value;
		}
		return null;
	}
}
