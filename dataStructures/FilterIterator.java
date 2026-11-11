package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter Includes description of general
 * methods for one way iterator.
 * 
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {
	/**
	 * Iterator of elements to filter.
	 */
	private final Iterator<E> iterator;
	/**
	 * Filter.
	 */
	private final Predicate<E> filter;
	/**
	 * Node with the next element in the iteration.
	 */
	private E nextToReturn;

	/**
	 *
	 * @param it        to be iterated
	 * @param criterion filter
	 */
	public FilterIterator(Iterator<E> it, Predicate<E> criterion) {
		iterator = it;
		filter = criterion;
		this.advanceNext();
	}

	/**
	 * Returns true if next would return an element
	 *
	 * @return true iff the iteration has more elements
	 */
	public boolean hasNext() {
		return nextToReturn != null;
	}

	/**
	 * Returns the next element in the iteration.
	 *
	 * @return the next element in the iteration
	 * @throws NoSuchElementException - if call is made without verifying
	 *                                pre-condition
	 */
	public E next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		E tmp = nextToReturn;
		this.advanceNext();
		return tmp;
	}

	/**
	 * Restarts the iteration. After rewind, if the iteration is not empty, next
	 * will return the first element.
	 */
	public void rewind() {
		iterator.rewind();
		this.advanceNext();
	}

	private void advanceNext() {
		E next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (filter.check(next)) {
				nextToReturn = next;
				return;
			}
		}

		nextToReturn = null;
	}
}
