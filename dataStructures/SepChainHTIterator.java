package dataStructures;

import dataStructures.Map.Entry;
import dataStructures.exceptions.NoSuchElementException;

/**
 * SepChain Hash Table Iterator
 * 
 * Spatial complexity: O(1)
 * 
 * 
 * @author AED Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */

class SepChainHTIterator<K, V> implements Iterator<Map.Entry<K, V>> {

	private Set<Entry<K, V>>[] table;
	private int count;
	private Iterator<Entry<K, V>> it;

	/**
	 * Temporal complexity:
	 * 
	 * O(1) in the average and best case
	 * O(n) in the worst case - if it needs to traverse the whole table in search of a valid set of entries
	 * 
	 * @param table
	 */
	public SepChainHTIterator(Set<Entry<K, V>>[] table) {
		this.table = table;
		count = 0;
		it = table[0].iterator();
		advance();
	}

	/**
	 * Temporal complexity: 
	 * 
	 * O(1) in the average and best case
	 * O(n) in the worst case - if it needs to traverse the whole table in search of a valid set of entries
	 */
	@Override
	public boolean hasNext() {
		advance();
		return it.hasNext();
	}

	/**
	 *  Temporal complexity: 
	 *  
	 * O(1) in the average and best case
	 * O(n) in the worst case - if it needs to traverse the whole table in search of a valid set of entries
	 */
	@Override
	public Entry<K, V> next() throws NoSuchElementException {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return it.next();
	}

	/**
	 *  Temporal complexity: 
	 *  
	 * O(1) in the average and best case
	 * O(n) in the worst case - if it needs to traverse the whole table in search of a valid set of entries
	 */
	@Override
	public void rewind() {
		count = 0;
		it = table[0].iterator();
		advance();
	}

	/**
	 * Temporal complexity:
	 * 
	 * O(1) in the average and best case
	 * O(n) in the worst case - if it needs to traverse the whole table in search of a valid set of entries
	 */
	private void advance() {
		while (!it.hasNext() && count + 1 < table.length) {
			it = table[++count].iterator();
		}
	}

}
