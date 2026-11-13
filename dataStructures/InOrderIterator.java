package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 * 
 * Spatial complexity: O(1)
 * 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;
    /**
     * Root Node
     */
    private final BTNode<E> root;
    /**
     *	Constructor
     *
     *	Temporal complexity:
     *	O(1) in the best case - root is null or root is the leftmost node
     *	O(log n) in the average case
     *	O(n) in the worst case - the tree is completely skewed left
     *
     * @param root
     */
    public  InOrderIterator(BTNode<E> root) {
        this.root=root;
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * 
     * Temporal complexity: O(1)
     *
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return next!=null;
    }

    /**
     *  Returns the next element in the iteration.
     *  
     * 	It calls advance() in its interior, so it has its time complexity:
     *
     *	O(1) in the best case - next has no right child and is a left child of its parent.
     *	O(log n) in the average case
     *	O(n) in the worst case - the tree is skewed
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem=next.getElement();
        advance();
        return elem;
    }

    /**
     *  Advances "next" to the next valid node.
     * 
     *	Temporal complexity:
     *
     *	O(1) in the best case - next has no right child and is a left child of its parent.
     *	O(log n) in the average case
     *	O(n) in the worst case - the tree is skewed
     */
    private void advance() {
        if(next.getRightChild() != null){
            next = ((BTNode<E>) next.getRightChild()).furtherLeftElement();
        }else{
            BTNode<E> child = next;
            BTNode<E> parent = (BTNode<E>) next.getParent();
            while (parent != null && parent.getRightChild() == child){
                child = parent;
                parent = (BTNode<E>) parent.getParent();
            }
            next = parent;
        }
    }


    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * 
     *	Temporal complexity:
     *
     *	O(1) in the best case - root is null or root is the leftmost node
     *	O(log n) in the average case
     *	O(n) in the worst case - the tree is completely skewed left
     * 
     */
    public void rewind() {
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }
}
