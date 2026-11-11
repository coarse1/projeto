package dataStructures;

import java.io.*;

/**
 * SepChain Hash Table
 * 
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K, V> extends HashTable<K, V> {
	private static final long serialVersionUID = 1L;

	// Load factors
	static final float IDEAL_LOAD_FACTOR = 0.75f;
	static final float MAX_LOAD_FACTOR = 0.9f;
	private static final int NUMCOLLISION = 3;

	// The array of set.
	private transient Set<Entry<K, V>>[] table;

	public SepChainHashTable() {
		this(DEFAULT_CAPACITY);
	}

	public SepChainHashTable(int capacity) {
		super();
		table = createTable(capacity);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private Set<Entry<K, V>>[] createTable(int capacity) {
		Set<Entry<K, V>>[] aux;
		int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
		maxSize = (int) (arraySize * MAX_LOAD_FACTOR);
		// Compiler gives a warning.
		aux = (Set<Entry<K, V>>[]) new Set[arraySize];
		for (int i = 0; i < arraySize; i++)
			aux[i] = new SetLinkedList<>();
		return aux;
	}

	private void rehash() {
		Set<Entry<K, V>>[] aux = createTable(maxSize * FACTOR);
		Iterator<Entry<K, V>> it = this.iterator();
		while (it.hasNext()) {
			Entry<K, V> entry = it.next();
			int rehash = Math.abs(entry.key().hashCode()) % aux.length;
			aux[rehash].put(entry);
		}
		table = aux;
	}

	// Returns the hash value of the specified key.
	int hash(K key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 * If there is an entry in the dictionary whose key is the specified key,
	 * returns its value; otherwise, returns null.
	 *
	 * @param key whose associated value is to be returned
	 * @return value of entry in the dictionary whose key is the specified key, or
	 *         null if the dictionary does not have an entry with that key
	 */
	@Override
	public V get(K key) {
		Set<Entry<K, V>> set = table[hash(key)];
		Entry<K, V> entryToFind = new Entry<K, V>(key, null);
		Entry<K, V> entry = set.get(entryToFind);

		if (entry == null) {
			return null;
		} else {
			return entry.value();
		}
	}

	/**
	 * If there is an entry in the dictionary whose key is the specified key,
	 * replaces its value by the specified value and returns the old value;
	 * otherwise, inserts the entry (key, value) and returns null.
	 *
	 * @param key   with which the specified value is to be associated
	 * @param value to be associated with the specified key
	 * @return previous value associated with the key, or null if the dictionary
	 *         does not have an entry with that key
	 */
	@Override
	public V put(K key, V value) {
		if (isFull())
			rehash();

		Set<Entry<K, V>> set = table[hash(key)];
		Entry<K, V> entry = new Entry<K, V>(key, value);

		if (set.size() >= NUMCOLLISION && !set.contains(entry)) {
			rehash();
			set = table[hash(key)];
		}

		if (set.put(entry)) {
			currentSize++;
			return null;
		} else {
			return set.replace(entry).value();
		}
	}

	/**
	 * If there is an entry in the dictionary whose key is the specified key,
	 * removes it from the dictionary and returns its value; otherwise, returns
	 * null.
	 *
	 * @param key whose entry is to be removed from the map
	 * @return previous value associated with the key, or null if the dictionary
	 *         does not an entry with that key
	 */
	@Override
	public V remove(K key) {
		Set<Entry<K, V>> set = table[hash(key)];
		Entry<K, V> entryToFind = new Entry<K, V>(key, null);
		Entry<K, V> entry = set.remove(entryToFind);

		if (entry == null) {
			return null;
		} else {
			currentSize--;
			return entry.value();
		}
	}

	/**
	 * Returns an iterator of the entries in the dictionary.
	 *
	 * @return iterator of the entries in the dictionary
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new SepChainHTIterator<>(table);
	}

	void writeData(ObjectOutputStream oos) throws IOException {
		oos.writeInt(table.length);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	void readData(ObjectInputStream ios) throws IOException, ClassNotFoundException {
		int length = ios.readInt();
		table = (Set<Entry<K,V>>[]) new Set[length];
		for(int i = 0; i < length; i++)
			table[i] = new SetLinkedList<>();
	}

}
