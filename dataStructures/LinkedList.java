package dataStructures;

import java.io.*;

/**
 * Linked List Includes description of general methods to be implemented by
 * linked lists.
 *
 * Space complexity: O(n), where n is the number of elements (one node per
 * element, with a reference to the element and another to the next node).
 *
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
abstract class LinkedList<E> implements Serializable {
	@Serial
	private static final long serialVersionUID = 0L;
	/**
	 * Node at the head of the list.
	 */
	transient LinkedNode<E> head;
	/**
	 * Node at the tail of the list.
	 */
	transient LinkedNode<E> tail;
	/**
	 * Number of elements in the list.
	 */
	transient int currentSize;

	/**
	 * Constructor of an empty singly linked list. head and tail are initialized as
	 * null. currentSize is initialized as 0.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 */
	public LinkedList() {
		head = null;
		tail = null;
		currentSize = 0;
	}

	/**
	 * Returns true iff the list contains no elements.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return true if the list is empty
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Returns the number of elements in the list.
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @return number of elements in the list
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Returns an iterator of the elements in the list (in a proper sequence).
	 *
	 * Time complexity: best case O(1); worst case O(1) (it only creates the
	 * iterator).
	 *
	 * @return Iterator of the elements in the list
	 */
	public Iterator<E> iterator() {
		return new LinkedIterator<>(head);
	}

	/**
	 * Insert a node on the head of list
	 *
	 * Time complexity: best case O(1); worst case O(1).
	 *
	 * @param newNode
	 */
	void addFirstNode(LinkedNode<E> newNode) {
		if (newNode == null) {
			return;
		}
		if (this.isEmpty()) {
			newNode.setNext(null);
			head = newNode;
			tail = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}

		currentSize++;
	}

	/**
	 * Insert a node on the tail of list
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @param newNode
	 */
	void addLastNode(LinkedNode<E> newNode) {
		if (newNode == null) {
			return;
		}
		newNode.setNext(null);
		if (this.isEmpty()) {
			head = newNode;
			tail = newNode;
		} else {
			tail.setNext(newNode);
			tail = newNode;
		}
		currentSize++;
	}

	/**
	 * Record with two nodes (prev, node)
	 * 
	 * @param prev
	 * @param node
	 */
	record pairNode<E>(LinkedNode<E> prev, LinkedNode<E> node) {
	}

	/**
	 * Insert node (newNode) between pair.previous() and pair.node()
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @pre: pair.previous()!=null && pair.node()!=null
	 * @param newNode
	 */
	void addMiddleNode(pairNode<E> pair, LinkedNode<E> newNode) {
		if (pair == null || pair.prev() == null || pair.node() == null || newNode == null) {
			return;
		}
		pair.prev().setNext(newNode);
		newNode.setNext(pair.node());
		currentSize++;
	}

	/**
	 * Removes the first node in the list.
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @pre: !isEmpty()
	 * @return
	 */
	E removeFirstNode() {
		if (this.isEmpty()) {
			return null;
		}
		E tmp = head.getElement();
		if (currentSize == 1) {
			head = null;
			tail = null;
		} else {
			head = head.getNext();
		}
		currentSize--;
		return tmp;
	}

	/**
	 * remove the last node (pair.node()) of the list
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @return
	 */
	E removeLastNode(pairNode<E> pair) {
		if (this.isEmpty()) {
			return null;
		}
		E tmp = null;
		if (currentSize == 1) {
			tmp = tail.getElement();
			head = null;
			tail = null;
			currentSize--;
		} else if (pair != null && pair.prev() != null && pair.node() != null && pair.node() == tail) {
			tmp = tail.getElement();
			tail = pair.prev();
			tail.setNext(null);
			currentSize--;
		}
		return tmp;
	}

	/**
	 * remove the node pair.node()
	 *
	 * Time complexity: best case O(1); worst case O(1)
	 *
	 * @pre: pair.previous()!=null && pair.node()!=null
	 * @param pair
	 */
	void removeMiddleNode(pairNode<E> pair) {
		if (this.isEmpty() || pair == null || pair.prev() == null || pair.node() == null) {
			return;
		}
		if (pair.node() == tail) {
			tail = pair.prev();
		}
		pair.prev().setNext(pair.node().getNext());
		pair.node().setNext(null);
		currentSize--;
	}

	/**
	 * Returns the pair (previous node, node) of the element, or null.
	 *
	 * Time complexity: best case O(1) (the element is at the head); worst case O(n)
	 * (the element is at the tail or does not exist).
	 *
	 * @param element - element read from the file, to be inserted
	 * @return pair with the previous node and the element node, Or null if no
	 *         element
	 */
	pairNode<E> nodeOf(E element) {
		if (this.isEmpty()) {
			return null;
		}
		LinkedNode<E> prev = null;
		LinkedNode<E> node = head;
		while (node != null) {
			if ((node.getElement() == null && element == null)
					|| (node.getElement() != null && node.getElement().equals(element))) {
				return new pairNode<E>(prev, node);
			}
			prev = node;
			node = node.getNext();
		}
		return null;
	}

	/**
	 * Returns the first node of the list (the tail).
	 *
	 * Time complexity: best case O(1); worst case O(1) (direct access to the head).
	 *
	 * @return the node at the head of the list, or null if the list is empty
	 */
	LinkedNode<E> getFirstNode() {
		return head;
	}

	/**
	 * Returns the last node of the list (the head).
	 *
	 * Time complexity: best case O(1); worst case O(1) (direct access to the head).
	 *
	 * @return the node at the tail of the list, or null if the list is empty
	 */
	LinkedNode<E> getLastNode() {
		return tail;
	}

	/**
	 * Manual serialization of the list. First writes the subclass's attributes
	 * (writeData), then the number of elements and, finally, each element in the
	 * list's order. The nodes are not written (head, tail and currentSize are
	 * transient), only the elements.
	 *
	 * Time complexity: best case O(n); worst case O(n) (every element is written
	 * once).
	 *
	 * @param oos - stream the list is written to
	 * @throws IOException - if a write error occurs
	 */
	@Serial
	private void writeObject(ObjectOutputStream oos) throws IOException {
		this.writeData(oos);
		oos.writeInt(currentSize);
		LinkedNode<E> node = head;
		while (node != null) {
			oos.writeObject(node.getElement());
			node = node.getNext();
		}
	}

	/**
	 * Manual deserialization of the list. First reads the subclass's attributes
	 * (readData), then the number of elements and, finally, each element, which is
	 * inserted into the list through addElem.
	 *
	 * Time complexity: best case O(n); worst case O(n) (n calls to addElem, each
	 * O(1)).
	 *
	 * @param ois - output1 stream
	 * @throws IOException            - if a write error happen
	 * @throws ClassNotFoundException - if the class of the object does not exist
	 */
	@SuppressWarnings("unchecked")
	@Serial
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.readData(ois);
		int size = ois.readInt();
		while (size != 0) {
			this.addElem((E) ois.readObject());
			size--;
		}
	}

	/**
	 * Writes the subclass's own attributes during serialization. In LinkedList it
	 * does nothing; the subclasses override it when they have attributes to save
	 * (e.g. the comparator in SortedLinkedList).
	 *
	 * Time complexity: best case O(1); worst case O(1) (empty method).
	 *
	 * @param out - stream the attributes are written to
	 * @throws IOException - if a write error occurs
	 */
	void writeData(ObjectOutputStream out) throws IOException {
	}

	/**
	 * Reads the subclass's own attributes during deserialization. In LinkedList it
	 * does nothing; the subclasses override it when they have attributes to restore
	 * (e.g. the comparator in SortedLinkedList).
	 *
	 * Time complexity: best case O(1); worst case O(1) (empty method).
	 *
	 * @param in - stream the attributes are read form
	 * @throws IOException            - if a write error happen
	 * @throws ClassNotFoundException - if the class of the object does not exist
	 */
	void readData(ObjectInputStream in) throws IOException, ClassNotFoundException {
	}

	/**
	 *
	 * @param element - element read from the file, to be inserted
	 */
	abstract void addElem(E element);
}
