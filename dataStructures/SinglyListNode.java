package dataStructures;

/**
 * Singly List Node Implementation
 * 
 * Spatial complexity: O(1) - only one element per node
 * 
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
@SuppressWarnings("all")
class SinglyListNode<E> implements LinkedNode<E> {
	private static final long serialVersionUID = 1L;

	/**
	 * Element stored in the node.
	 */
	@SuppressWarnings("serial")
	private E element;

	/**
	 * (Pointer to) the next node.
	 */
	private LinkedNode<E> next;

	/**
	 * Creates a node with the given element, pointing to the given next node
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 * 
	 * @param theElement - The element to be contained in the node
	 * @param theNext    - the next node
	 */
	public SinglyListNode(E theElement, SinglyListNode<E> theNext) {
		element = theElement;
		next = theNext;
	}

	/**
	 * Creates a node with the given element and no next node
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @param theElement to be contained in the node
	 */
	public SinglyListNode(E theElement) {
		this(theElement, null);
	}

	/**
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @return the element contained in the node
	 */
	public E getElement() {
		return element;
	}

	/**
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @return the next node
	 */
	public LinkedNode<E> getNext() {
		return next;
	}

	/**
	 * Replace the element stored in the node
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @param newElement - New element to replace the current element
	 */
	public void setElement(E newElement) {
		element = newElement;
	}

	/**
	 * Replace the next element
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @param newNext - node to replace the next node
	 */
	public void setNext(LinkedNode<E> newNext) {
		next = newNext;
	}
}
