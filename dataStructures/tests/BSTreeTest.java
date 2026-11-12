package dataStructures.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import dataStructures.BSTree;
import dataStructures.Comparator;
import dataStructures.Iterator;
import dataStructures.Map.Entry;
import dataStructures.exceptions.EmptyMapException;
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
		BSTree<String, String> empty = new BSTree<String, String>();
		assertNull(empty.remove("one"));
		
		BSTree<String, String> tree = makeBSTree();
		assertEquals(5, tree.size());
		assertEquals("c", tree.remove("three"));
		assertEquals("d", tree.remove("four"));
		assertEquals("a", tree.remove("one"));
		assertNull(tree.remove("six"));
		
		assertFalse(tree.isEmpty());
		assertEquals(2, tree.size());
		
		assertNull(tree.get("one"));
		assertEquals("b", tree.get("two"));
		assertNull(tree.get("three"));
		assertNull(tree.get("four"));
		assertEquals("e", tree.get("five"));
		
		assertNull(tree.put("six", "f"));
		assertEquals(3, tree.size());
		
		assertEquals("e", tree.remove("five"));
		assertEquals("f", tree.remove("six"));
		assertEquals("b", tree.remove("two"));
		
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());
	}

	@Test
	void testIterator() {
		Iterator<Entry<String, String>> empty = (new BSTree<String, String>()).iterator();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);

		Iterator<Entry<String, String>> it = makeBSTree().iterator();

		assertTrue(it.hasNext());
		Entry<String, String> entry = it.next();
		assertEquals("five", entry.key());
		assertEquals("e", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("four", entry.key());
		assertEquals("d", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("one", entry.key());
		assertEquals("a", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("three", entry.key());
		assertEquals("c", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("two", entry.key());
		assertEquals("b", entry.value());

		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
		it.rewind();

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("five", entry.key());
		assertEquals("e", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("four", entry.key());
		assertEquals("d", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("one", entry.key());
		assertEquals("a", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("three", entry.key());
		assertEquals("c", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("two", entry.key());
		assertEquals("b", entry.value());
	}

	@Test
	void testMinEntryAndMaxEntry() {
		BSTree<String, String> empty = new BSTree<String, String>();
		assertThrows(EmptyMapException.class, empty::minEntry);
		assertThrows(EmptyMapException.class, empty::maxEntry);
		
		BSTree<String, String> tree = makeBSTree();
		Entry<String, String> minEntry = tree.minEntry();
		Entry<String, String> maxEntry = tree.maxEntry();
		
		assertEquals("five", minEntry.key());
		assertEquals("e", minEntry.value());
		assertEquals("two", maxEntry.key());
		assertEquals("b", maxEntry.value());
		
		assertNull(tree.put("a", "newMin"));
		assertNull(tree.put("z", "newMax"));
		
		minEntry = tree.minEntry();
		maxEntry = tree.maxEntry();
		
		assertEquals("a", minEntry.key());
		assertEquals("newMin", minEntry.value());
		assertEquals("z", maxEntry.key());
		assertEquals("newMax", maxEntry.value());
	}

	@Test
	void testKeys() {
		Iterator<String> empty = (new BSTree<String, String>()).keys();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);

		Iterator<String> it = makeBSTree().keys();

		assertTrue(it.hasNext());
		String key = it.next();
		assertEquals("five", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("four", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("one", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("three", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("two", key);

		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
		it.rewind();

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("five", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("four", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("one", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("three", key);

		assertTrue(it.hasNext());
		key = it.next();
		assertEquals("two", key);
	}

	@Test
	void testValues() {
		Iterator<String> empty = (new BSTree<String, String>()).values();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);

		Iterator<String> it = makeBSTree().values();

		assertTrue(it.hasNext());
		String value = it.next();
		assertEquals("e", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("d", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("a", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("c", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("b", value);

		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
		it.rewind();

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("e", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("d", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("a", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("c", value);

		assertTrue(it.hasNext());
		value = it.next();
		assertEquals("b", value);
	}

	@Test
	void testGetHeight() {
		BSTree<String, String> empty = new BSTree<String, String>();
		assertEquals(-1, empty.getHeight());
		assertNull(empty.put("one", "a"));
		assertEquals(0, empty.getHeight());
		
		BSTree<String, String> tree = makeBSTree();
		assertEquals(2, tree.getHeight());
		assertNull(tree.put("six", "leftThree"));
		assertEquals(3, tree.getHeight());
		assertNull(tree.put("h", "rightFour"));
		assertEquals(3, tree.getHeight());
		assertNull(tree.put("fix", "rightFive"));
		assertEquals(3, tree.getHeight());
		assertNull(tree.put("fixed", "rightFix"));
		assertEquals(4, tree.getHeight());
		assertEquals(9, tree.size());
		
		assertEquals("c", tree.remove("three"));
		assertEquals(4, tree.getHeight());
		assertEquals(8, tree.size());
		
		assertEquals("rightFive", tree.remove("fix"));
		assertEquals(3, tree.getHeight());
		assertEquals(7, tree.size());
		
		assertEquals("rightFour", tree.remove("h"));
		assertEquals(3, tree.getHeight());
		assertEquals(6, tree.size());
		
		assertEquals("a", tree.remove("one"));
		assertEquals(3, tree.getHeight());
		assertEquals(5, tree.size());
		
		assertEquals("rightFix", tree.remove("fixed"));
		assertEquals(2, tree.getHeight());
		assertEquals(4, tree.size());
		
		assertEquals("e", tree.remove("five"));
		assertEquals("b", tree.remove("two"));
		assertEquals(1, tree.getHeight());
		assertEquals(2, tree.size());
		
		assertEquals("d", tree.remove("four"));
		assertEquals(0, tree.getHeight());
		assertEquals(1, tree.size());
		
		assertEquals("leftThree", tree.remove("six"));
		assertEquals(-1, tree.getHeight());
		assertEquals(0, tree.size());
	}
	
	private class ReverseComparator implements Comparator<String>{

		private static final long serialVersionUID = 1L;

		@Override
		public int compare(String x, String y) {
			return y.compareTo(x);
		}
		
	}
	@Test
	void testSorting() {
		BSTree<String, String> reverseTree = new BSTree<String, String>(new ReverseComparator());
		reverseTree.put("one", "a");
		reverseTree.put("two", "b");
		reverseTree.put("three", "c");
		reverseTree.put("four", "d");
		reverseTree.put("five", "e");
		assertEquals("two", reverseTree.minEntry().key());
		assertEquals("b", reverseTree.minEntry().value());
		assertEquals("five", reverseTree.maxEntry().key());
		assertEquals("e", reverseTree.maxEntry().value());
		
		Iterator<Entry<String, String>> it = reverseTree.iterator();

		assertTrue(it.hasNext());
		Entry<String, String> entry = it.next();
		assertEquals("two", entry.key());
		assertEquals("b", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("three", entry.key());
		assertEquals("c", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("one", entry.key());
		assertEquals("a", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("four", entry.key());
		assertEquals("d", entry.value());

		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("five", entry.key());
		assertEquals("e", entry.value());

		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
	}

}
