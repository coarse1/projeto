package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Breadth First Search Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */

class BreadthIterator<E> implements Iterator<E> {

	private final BTNode<E> root;
	private List<BTNode<E>> queue;

	BreadthIterator(BTNode<E> root){
		this.root = root;
		rewind();
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

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

	@Override
	public void rewind() {
		queue = new SinglyLinkedList<>();
		if(root != null)
			queue.addLast(root);
	}
}
