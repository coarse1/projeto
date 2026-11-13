package dataStructures;
import java.io.*;

/**
 * Linked Tree
 * 
 * Spatial complexity O(n), where n is the number of elements
 * 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
abstract class LinkedTree<E> implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Root
     */
    transient InfoNode<E> root;

    /**
     * Number of elements
     */
    transient int currentSize;

    /** Constructor
     *  
     *  Temporal complexity O(1)
     */
    public LinkedTree(){
        root=null;
        currentSize=0;
    }

    /**
     * Returns true iff the tree contains no entries.
     *
     * Temporal complexity: O(1)
     *
     * @return true if the tree is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of entries in the tree.
     *
     * Temporal complexity: O(1)
     *
     * @return number of elements in the tree
     */
    public int size() {
        return currentSize;
    }


    /**
     * Return the root of the tree
     * 
     * Temporal complexity: O(1)
     * 
     * @return root
     */
    InfoNode<E> root(){ return root;}

    
    
    abstract Iterator<E> iteratorLevel();

    // MANUAL SERIALIZATION
    /**
     * 
     * Serializes the linked tree.
     * Temporal complexity: assumed O(n) - depends on the implementation of writeData
     * 
     * @param oos
     * @throws IOException
     */
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        writeData(oos);
        oos.writeInt(currentSize);       // write the size
        Iterator<E> it = iteratorLevel();
        while (it.hasNext()) {
            E elem = it.next();
            oos.writeObject(elem); // write each element
        }
    }

    // MANUAL DESERIALIZATION
    /**
     * Deserializes the linked tree.
     * Temporal complexity: assumed O(n) - depends on the implementation of addElem
     * 
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        readData(ois);
        int size = ois.readInt();  // read the size
        for (int i = 0; i < size; i++) { // add each element on the list
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addElem(element);
        }
    }
    
    
    abstract void addElem(E element);

    void writeData(ObjectOutputStream oos)
            throws IOException{
    }

    void readData(ObjectInputStream ois)
            throws IOException, ClassNotFoundException{
    }
}
