package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

public class ArrayIterator<T> implements TwoWayIterator<T> {
	private static final int DIFF = 2;

	private final T[] elems;
	private final int counter;
	private int currentN;
	private int currentP;

	public ArrayIterator(T[] elems, int counter) {
		this.elems = elems;
		this.counter = counter;
		currentN = 0;
		currentP = currentN - DIFF;
	}

	@Override
	public boolean hasNext() {
		return currentN < counter;
	}

	@Override
	public T next() throws NoSuchElementException {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		currentP++;
		return elems[currentN++];
	}

	@Override
	public void rewind() {
		currentN = 0;
		currentP = currentN - DIFF;
	}

	@Override
	public boolean hasPrevious() {
		return currentP >= 0;
	}

	@Override
	public T previous() throws NoSuchElementException {
		if (!this.hasPrevious()) {
			throw new NoSuchElementException();
		}
		currentN--;
		return elems[currentP--];
	}

	@Override
	public void fullForward() {
		currentP = counter - 1;
		currentN = currentP + DIFF;

	}
}
