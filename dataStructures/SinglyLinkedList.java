package dataStructures;

/**
 * Implementation of Singly Linked List
 * 
 * Spatial complexity: O(n), where n is the number of elements (one node per
 * element, with a reference to the element and another to the next node).
 * 
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class SinglyLinkedList<E> extends SequenceLinkedList<E> {
	private static final long serialVersionUID = 1L;

	public SinglyLinkedList() {
		super();
	}

	/**
	 * Inserts the specified element at the first position in the list.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param element to be inserted
	 */
	@Override
	public void addFirst(E element) {
		super.addFirstNode(new SinglyListNode<E>(element));
	}

	/**
	 * Inserts the specified element at the last position in the list.
	 * Time complexity: best case O(1); worst case O(1).
	 * 
	 * @param element to be inserted
	 */
	@Override
	public void addLast(E element) {
		super.addLastNode(new SinglyListNode<E>(element));
	}

	/**
	 * 
	 * Inserts the specified element at the given position in the list.
	 * Time complexity: best case O(1); worst case O(n).
	 * 
	 */
	void addMiddle(int position, E element) {
		super.addMiddleNode(super.getNodes(position), new SinglyListNode<E>(element));
	}
}
