package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of an Iterator for Linked List
 * 
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 *            Spatial complexity: O(1) (it only keeps two references to nodes; it
 *            does not copy the list).
 */

class LinkedIterator<E> implements Iterator<E> {
	/**
	 * First node of the list.
	 */
	private final LinkedNode<E> first;
	/**
	 * Node with the next element in the iteration.
	 */
	LinkedNode<E> nextToReturn;

	/**
	 * SinglyIterator constructor
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @param first - Node with the first element of the iteration
	 */
	public LinkedIterator(LinkedNode<E> first) {
		this.first = first;
		nextToReturn = first;
	}

	/**
	 * Checks whether there are still elements to visit in the iteration.
	 *
	 * Time complexity: best case O(1); worst case O(1) (only one comparison).
	 *
	 * @return true if there is a next element, false otherwise
	 */
	@Override
	public boolean hasNext() {
		return nextToReturn != null;
	}

	/**
	 * Returns the next element of the iteration and advances to the next node.
	 *
	 * Time complexity: best case O(1); worst case O(1) (moves forward only one
	 * node).
	 *
	 * @return the next element of the iterator
	 * @throws NoSuchElementException - if there are no more elements
	 */
	@Override
	public E next() throws NoSuchElementException {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		E tmp = nextToReturn.getElement();
		nextToReturn = nextToReturn.getNext();
		return tmp;
	}

	/**
	 * Restarts the iteration: the next call to next returns the first element again
	 * (if the list is not empty).
	 *
	 * Time complexity: best case O(1); worst case O(1) (only one assignment).
	 */
	@Override
	public void rewind() {
		nextToReturn = first;

	}

}
