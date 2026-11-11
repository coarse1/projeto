package dataStructures;

/**
 * Implementation of Singly Linked List
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
	 * @param element to be inserted
	 */
	@Override
	public void addFirst(E element) {
		super.addFirstNode(new SinglyListNode<E>(element));
	}

	/**
	 * Inserts the specified element at the last position in the list.
	 *
	 * @param element to be inserted
	 */
	@Override
	public void addLast(E element) {
		super.addLastNode(new SinglyListNode<E>(element));
	}

	void addMiddle(int position, E element) {
		super.addMiddleNode(super.getNodes(position), new SinglyListNode<E>(element));
	}
}
