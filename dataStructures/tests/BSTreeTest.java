package dataStructures.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import dataStructures.BSTree;
import dataStructures.Iterator;
import dataStructures.Map.Entry;
import dataStructures.exceptions.NoSuchElementException;

class BSTreeTest {

	private BSTree<String, String> makeBSTree() {
		BSTree<String, String> tree = new BSTree<String, String>();
		tree.put("one", "a");
		tree.put("two", "b");
		tree.put("three", "c");
		tree.put("four", "d");
		tree.put("five", "e");
		return tree;
	}

	@Test
	void testPutAndGet() {
		BSTree<String, String> empty = new BSTree<String, String>();
		assertTrue(empty.isEmpty());
		assertEquals(0, empty.size());
		assertNull(empty.get("one"));
		
		BSTree<String, String> tree = makeBSTree();
		assertFalse(tree.isEmpty());
		assertEquals(5, tree.size());
		assertEquals("a", tree.get("one"));
		assertEquals("b", tree.get("two"));
		assertEquals("c", tree.get("three"));
		assertEquals("d", tree.get("four"));
		assertEquals("e", tree.get("five"));
		assertNull(tree.get("six"));
		
		assertEquals("a", tree.put("one", "A"));
		assertEquals("c", tree.put("three", "C"));
		assertEquals("e", tree.put("five", "E"));
		assertNull(tree.put("six", "f"));
		assertNull(tree.put("seven", "g"));
		assertEquals("g", tree.put("seven", "G"));
		assertEquals(7, tree.size());
		
		assertEquals("A", tree.get("one"));
		assertEquals("b", tree.get("two"));
		assertEquals("C", tree.get("three"));
		assertEquals("d", tree.get("four"));
		assertEquals("E", tree.get("five"));
		assertEquals("f", tree.get("six"));
		assertEquals("G", tree.get("seven"));
	}
	
	@Test
	void testRemove() {

	}
	
	@Test
	void testIterator() {
		Iterator<Entry<String, String>> empty = (new BSTree<String, String>()).iterator();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);
		
		Iterator<Entry<String, String>> it = makeBSTree().iterator();
		assertTrue(it.hasNext());
		
	}
	
	@Test
	void testMinEntryAndMaxEntry() {

	}
	
	@Test
	void testKeys() {

	}
	
	@Test
	void testValues() {

	}
	
	@Test
	void testGetHeight() {
		BSTree<String, String> empty = new BSTree<String, String>();
		assertTrue(empty.isEmpty());
		assertEquals(0, empty.size());
		assertEquals(-1, empty.getHeight());
	}

}
