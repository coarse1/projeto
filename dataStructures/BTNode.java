package dataStructures;

import java.io.Serializable;

/**
 * Binary Tree Node
 * 
 * Spatial complexity O(1), just stores pointers to others nodes and one
 * element.
 * 
 * @author AED Team
 * @version 1.0
 * @param <E> Generic Element
 */
class BTNode<E> implements InfoNode<E>, Serializable {
	private static final long serialVersionUID = 1L;

	// Element stored in the node.
	@SuppressWarnings("serial")
	private E element;

	// (Pointer to) the father.
	private InfoNode<E> parent;
	// (Pointer to) the left child.
	private InfoNode<E> leftChild;
	// (Pointer to) the right child.
	private InfoNode<E> rightChild;

	/**
	 * Constructor
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param elem
	 */
	BTNode(E elem) {
		this(elem, null, null, null);
	}

	/**
	 * Constructor
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * 
	 * @param elem
	 * @param parent
	 */
	BTNode(E elem, BTNode<E> parent) {
		this(elem, parent, null, null);
	}

	/**
	 * Constructor
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param elem
	 * @param parent
	 * @param leftChild
	 * @param rightChild
	 */
	BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild) {
		element = elem;
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**
	 * Returns the element of the node
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @return element
	 */
	public E getElement() {
		return element;
	}

	/**
	 * Returns the left child of node
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @return leftChild
	 */
	InfoNode<E> getLeftChild() {
		return leftChild;
	}

	/**
	 * Returns the right child of node
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @return rightChild
	 */
	InfoNode<E> getRightChild() {
		return rightChild;
	}

	/**
	 * Returns the parent of node
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @return parent
	 */
	InfoNode<E> getParent() {
		return parent;
	}

	/**
	 * Update the element
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param elem
	 */
	public void setElement(E elem) {
		element = elem;
	}

	/**
	 * Update the left child
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param node
	 */
	void setLeftChild(InfoNode<E> node) {
		leftChild = node;
	}

	/**
	 * Update the right child
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param node
	 */
	void setRightChild(InfoNode<E> node) {
		rightChild = node;
	}

	/**
	 * Update the parent
	 * 
	 * Temporal complexity: O(1)
	 * 
	 * @param node
	 */
	void setParent(InfoNode<E> node) {
		parent = node;
	}

	/**
	 * Temporal complexity: best case O(1), worst case O(n)
	 * 
	 * O(1) if this is a leaf node. Worst case, O(n) where n is the total number of child nodes
	 * 
	 * Returns the height of the subtree rooted at this node.
	 */
	int getHeight() {
		if (leftChild == null && rightChild == null)
			return 0;
		BTNode<E> left = (BTNode<E>) getLeftChild();
		BTNode<E> right = (BTNode<E>) getRightChild();
		if (left == null)
			return 1 + right.getHeight();
		if (right == null)
			return 1 + left.getHeight();
		return 1 + Math.max(left.getHeight(), right.getHeight());
	}

	/**
	 * Temporal complexity: best case O(1), worst case O(n), average case O(log n)
	 * 
	 * O(1) if this is the leftmost node, O(n) if the tree is completely skewed to the left.
	 * 
	 * 
	 * @return the leftmost element
	 */
	BTNode<E> furtherLeftElement() {
		BTNode<E> node = this;
		while (node.getLeftChild() != null)
			node = (BTNode<E>) node.getLeftChild();
		return node;
	}

	/**
	 * Temporal complexity: best case O(1), worst case O(n), average case O(log n)
	 * 
	 * O(1) if this is the rightmost node, O(n) if the tree is completely skewed to the right
	 * 
	 * @return the rightmost element
	 */
	BTNode<E> furtherRightElement() {
		BTNode<E> node = this;
		while (node.getRightChild() != null)
			node = (BTNode<E>) node.getRightChild();
		return node;
	}

}
