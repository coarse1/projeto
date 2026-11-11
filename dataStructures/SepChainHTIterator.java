package dataStructures;

import dataStructures.Map.Entry;
import dataStructures.exceptions.NoSuchElementException;

/**
 * SepChain Hash Table Iterator
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

	public SepChainHTIterator(Set<Entry<K, V>>[] table) {
		this.table = table;
		count = 0;
		it = table[0].iterator();
		advance();
	}

	@Override
	public boolean hasNext() {
		advance();
		return it.hasNext();
	}

	@Override
	public Entry<K, V> next() throws NoSuchElementException {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return it.next();
	}

	@Override
	public void rewind() {
		count = 0;
		it = table[0].iterator();
		advance();
	}

	private void advance() {
		while (!it.hasNext() && count + 1 < table.length) {
			it = table[++count].iterator();
		}
	}

}
