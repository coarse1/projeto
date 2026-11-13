package dataStructures;
/**
 * Binary Tree
 * 
 * Spatial complexity O(n), where n is the number of elements
 * 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends LinkedTree<E> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * Temporal complexity: O(1)
	 */
    public BTree(){
      super();
    }

    /**
     * Returns the height of the tree.
     * Temporal complexity: best case O(1), worst case O(n)
     * 
     * O(1) if empty or if the root is also a leaf node (there is only one element in the tree)
     * O(n) in any other situation, as it must visit every child node. 
     */
    public int getHeight() {
        if(isEmpty())
            return -1;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     * 
     * Temporal complexity: best case O(1), worst case O(n), average case O(log n)
     * 
     * O(1) if empty or if the root is the leftmost element
     * O(n) if the tree is completely skewed to the left
     * 
     * @return leftmost element
     */
    BTNode<E> furtherLeftElement() {
        if(root == null)
            return null;
        return ((BTNode<E>) root).furtherLeftElement();
    };

    /**
     * Return the further right node of the tree
     * 
     * Temporal complexity: best case O(1), worst case O(n), average case O(log n)
     * 
     * O(1) if empty or if the root is the rightmost element
     * O(n) if the tree is completely skewed to the right
     * 
     * 
     * @return rightmost element
     */
    BTNode<E> furtherRightElement() {
        if(root == null)
            return null;
        return ((BTNode<E>) root).furtherRightElement();
    }

}
