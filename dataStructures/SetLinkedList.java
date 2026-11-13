package dataStructures;
/**
 * 
 * A linked list that also acts as a set: there are no repeated elements.
 * 
 * Spatial complexity: O(n), where n is the number of elements (one node per
 * element, with a reference to the element and another to the next node).
 * 
 * @param <E> generic element
 */
public class SetLinkedList<E> extends LinkedList<E> implements Set<E> {
	private static final long serialVersionUID = 1L;

	public SetLinkedList() {
		super();
	}

	@Override
	void addElem(E element) {
		addLastNode(new SinglyListNode<E>(element));
	}

	/**
	 * If there is an element in the set equals to a specified element, returns the
	 * element; otherwise, returns null.
	 *
	 * Temporal complexity: best case: O(1), worst case: O(n)
	 *
	 * @param element
	 * @return an element in the set equals to a specified element, or null
	 *         otherwise
	 */
	@Override
	public E get(E element) {
		if (isEmpty()) {
			return null;
		}

		LinkedNode<E> node = head;
		while (node != null) {
			if ((node.getElement() == null && element == null)
					|| (node.getElement() != null && node.getElement().equals(element))) {
				return node.getElement();
			}
			node = node.getNext();
		}

		return null;

	}

	/**
	 * If there is an element in the set equals to a specified element, returns
	 * true; otherwise, returns false.
	 * 
	 * Temporal complexity: best case: O(1), worst case: O(n)
	 *
	 * @param element
	 * @return true if contains the given element
	 */
	@Override
	public boolean contains(E element) {
		if (isEmpty())
			return false;
		return get(element) != null;
	}

	/**
	 * Inserts the element, if there is not an element in the set equals to the
	 * specified element and return true. otherwise returns false
	 * 
	 * Temporal complexity: best case: O(1), worst case: O(n)
	 * 
	 * @param element to be added
	 * @return true if insert the element, otherwise false
	 */
	public boolean put(E element) {
		if (contains(element)) {
			return false;
		}
		addLastNode(new SinglyListNode<E>(element));
		return true;
	}

	/**
	 * If there is an element in the set equals to the specified element replace the
	 * element and return old element. otherwise returns null
	 *
	 * Temporal complexity: best case: O(1), worst case: O(n)
	 *
	 * @param element to be added
	 * @return old element, otherwise null
	 */
	@Override
	public E replace(E element) {
		pairNode<E> pair = nodeOf(element);
		if (pair == null || pair.node() == null) {
			return null;
		}

		E oldE = pair.node().getElement();
		pair.node().setElement(element);
		return oldE;
	}

	/**
	 * If there is an element in the set equals to the specified element, removes it
	 * from the set and returns the element; otherwise, returns null.
	 *
	 * Temporal complexity: best case: O(1), worst case: O(n)
	 *
	 * @param element to be removed
	 * @return element removed, or null if the set does not an element equals
	 */
	@Override
	public E remove(E element) {
		pairNode<E> pair = nodeOf(element);
		if (pair == null || pair.node() == null) {
			return null;
		}

		if (pair.node() == head) {
			return removeFirstNode();
		}

		if (pair.prev() == null) {
			return null;
		}

		if (pair.node() == tail) {
			return removeLastNode(pair);
		}

		E oldE = pair.node().getElement();
		removeMiddleNode(pair);
		return oldE;
	}

	/**
	 * Tests if every element is different, ensuring the class is in a valid state.
	 */
	private void invariant() {
		assert allDif();
	}

	/**
	 * Scans the linked list and checks if every element is different.
	 * If it finds two elements that are equal, breaks early, and returns false.
	 * Otherwise, it must go through the entire list at least once per element, which means O(n^2) complexity.
	 * 
	 * Temporal complexity: best case: O(1), worst case: O(n^2)
	 * @return true if every element is different
	 */
	private boolean allDif() {
		if (currentSize < 2) {
			return true;
		}

		LinkedNode<E> nodeOne = head;
		while (nodeOne != null) {

			LinkedNode<E> nodeTwo = nodeOne.getNext();
			while (nodeTwo != null) {

				if ((nodeOne.getElement() == null && nodeTwo.getElement() == null)
						|| (nodeOne.getElement() != null && nodeOne.getElement().equals(nodeTwo.getElement()))) {
					return false;
				}

				nodeTwo = nodeTwo.getNext();
			}
			nodeOne = nodeOne.getNext();
		}
		return true;
	}
}
