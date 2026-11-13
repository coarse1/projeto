package dataStructures;

import dataStructures.exceptions.EmptyMapException;
import dataStructures.Map.Entry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Binary Search Tree Sorted Map
 * 
 * Spatial complexity: O(n) where n is the number of elements
 * 
 * 
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTree<K extends Comparable<K>,V> extends BTree<Entry<K,V>> implements SortedMap<K,V> {
	private static final long serialVersionUID = 1L;
    /**
     * Comparator
     */
    private Comparator<K> comparator;
    /**
     * Default comparator
     * 
     * Spatial complexity O(1)
     * 
     * @param <K> Generic Key
     */
    static class DefaultComparator<K extends Comparable<K>> implements Comparator<K> {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * Temporal complexity O(1) 
    	 */
        public int compare(K a, K b)  {
            return a.compareTo(b);
        }
    }
    /**
     * Constructor
     * 
     * Temporal complexity O(1)
     * 
     */
    public BSTree(){
        super();
        comparator= new DefaultComparator<>();
    }

    /**
     * Constructor
     * 
     * Temporal complexity O(1)
     * 
     * @param compare - comparator
     */
    public BSTree(Comparator<K> compare){
        super();
        comparator = compare;
    }
    /**
     * Returns the entry with the smallest key in the map.
     *
     * Temporal complexity: best case O(1), worst case O(n), average case O(log n)
     * 
     * O(1) if empty or if the root is the leftmost element
     * O(n) if the tree is completely skewed to the left
     *
     *
     * @return entry with the smallest key 
     * @throws EmptyMapException
     */
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the map.
     *
     * Temporal complexity: best case O(1), worst case O(n), average case O(log n)
     * 
     * O(1) if empty or if the root is the rightmost element
     * O(n) if the tree is completely skewed to the right
     *
     * @return entry with the largest key
     * @throws EmptyMapException
     */
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * Temporal complexity: 
     * 
     * best case: O(1)
     * average case: O(log n)
     * worst case: O(n)
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        BTNode<Entry<K, V>> node = (BTNode<Entry<K,V>>) root;
        while (node != null){
            int cmp = comparator.compare(key, node.getElement().key());
            if(cmp == 0)
                return node.getElement().value();
            node = (BTNode<Entry<K,V>>) (cmp < 0 ? node.getLeftChild() : node.getRightChild());
        }
        return  null;
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * Temporal complexity: 
     * 
     * best case: O(1)
     * average case: O(log n)
     * worst case: O(n)
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        Entry<K, V> element = new Entry<>(key, value);
        if(isEmpty()){
            root = new BTNode<>(element);
            currentSize++;
            return null;
        }
        BTNode<Entry<K, V>> parent = null;
        BTNode<Entry<K, V>> node = (BTNode<Entry<K,V>>) root;
        int cmp = 0;
        while(node != null){
            cmp = comparator.compare(key, node.getElement().key());
            if(cmp == 0){
                V old = node.getElement().value();
                node.setElement(element);
                return old;
            }
            parent =node;
            node= (BTNode<Entry<K,V>>) (cmp < 0 ? node.getLeftChild() : node.getRightChild());
        }
        BTNode<Entry<K, V>> newNode = new BTNode<>(element, parent);
        if(cmp < 0)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);
        currentSize++;
        return null;
    }

    /**
     * If there is an entry in the map whose key is the specified key,
     * removes it from the map and returns its value;
     * otherwise, returns null.
     *
     * Temporal complexity: 
     * 
     * best case: O(1)
     * average case: O(log n)
     * worst case: O(n)
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        BTNode<Entry<K, V>> node = (BTNode<Entry<K,V>>) root;
        while(node != null){
            int cmp = comparator.compare(key, node.getElement().key());
            if(cmp == 0)
                break;
            node = (BTNode<Entry<K,V>>) (cmp < 0 ? node.getLeftChild() : node.getRightChild());
        }
        if(node == null)
            return null;
        V old = node .getElement().value();
        if(node.getLeftChild() != null && node.getRightChild() != null){
            BTNode<Entry<K, V>> min = ((BTNode<Entry<K,V>>) node.getRightChild()).furtherLeftElement();
            node.setElement(min.getElement());
            node = min;
        }
        BTNode<Entry<K, V>> child = (BTNode<Entry<K,V>>)
                (node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild());
        BTNode<Entry<K, V>> parent = (BTNode<Entry<K,V>>)  node.getParent();
        if(child != null)
            child.setParent(parent);
        if(parent == null)
            root = child;
        else if (parent.getLeftChild() == node)
            parent.setLeftChild(child);
        else
            parent.setRightChild(child);
        currentSize--;
        return old;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * Temporal complexity: O(1)
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    
    /**
     * Returns a breadth iterator.
     * 
     * Temporal complexity: O(1)
     */
    Iterator<Entry<K, V>> iteratorLevel() {
        return new BreadthIterator<>((BTNode<Entry<K,V>>)root);
    }

    /**
     * Used for deserialization.
     * 
     * Temporal complexity: 
     * 
     * best case: O(1)
     * average case: O(log n)
     * worst case: O(n)
     * 
     * As readObject in LinkedTree has complexity of O(n), using addElem in its interior yields
     * a temporal complexity for readObject of:
     * 
     * O(n) in the best case,
     * O(n log n) in the average case
     * O(n^2) in the worst case
     * 
     * Used inside readObject
     * 
     */
    void addElem(Entry<K, V> element) {
        put(element.key(),element.value());
    }

    /**
     * Used for serialization.
     * 
     * Temporal complexity: O(1)
     * 
     * As writeObject in LinkedTree has complexity of O(n), using writeData in its interior yields
     * a temporal complexity for writeObject of O(n).
     * 
     * Used inside writeObject.
     */
    void writeData(ObjectOutputStream oos) throws IOException {
        oos.writeObject(comparator);
    }

    /**
     * Used for deserialization.
     *      
     * Temporal complexity: O(1)
     * 
     * Used inside readObject.
     */
    @SuppressWarnings("unchecked")
	void readData(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        comparator = (Comparator<K>) ois.readObject();
    }

}
