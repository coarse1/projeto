package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Breadth First Search Binary Tree iterator
 * 
 * Spatial complexity: O(n) where n is the number of elements in the tree
 * 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */

class BreadthIterator<E> implements Iterator<E> {

	private final BTNode<E> root;
	private List<BTNode<E>> queue;

	/**
	 * Constructor
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param root
	 */
	BreadthIterator(BTNode<E> root){
		this.root = root;
		rewind();
	}

	/**
	 * Temporal complexity: O(1)
	 * 
	 */
	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	/**
	 * Temporal Complexity: O(1)
	 * 
	 */
	@Override
	public E next() throws NoSuchElementException {
		if(!hasNext())
			throw new NoSuchElementException();
		BTNode<E> node = queue.removeFirst();
		if(node.getLeftChild() != null)
			queue.addLast((BTNode<E>) node.getLeftChild());
		if(node.getRightChild() != null)
			queue.addLast((BTNode<E>) node.getRightChild());
		return node.getElement();
	}

	/**
	 * Temporal complexity: O(1) - addLast is O(1) for a linked list
	 * 
	 */
	@Override
	public void rewind() {
		queue = new SinglyLinkedList<>();
		if(root != null)
			queue.addLast(root);
	}
}
