package dataStructures.tests;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.Test;

import dataStructures.Iterator;
import dataStructures.SetLinkedList;
import dataStructures.exceptions.NoSuchElementException;

class SetLinkedListTest {

	private SetLinkedList<String> makeSet() {
		SetLinkedList<String> set = new SetLinkedList<String>();
		set.put("one");
		set.put("two");
		set.put("three");
		set.put("four");
		set.put("five");
		return set;
	}

	@Test
	void testPutAndGet() {
		SetLinkedList<String> empty = new SetLinkedList<String>();
		assertTrue(empty.isEmpty());
		assertEquals(0, empty.size());
		
		SetLinkedList<String> set = makeSet();
		assertFalse(set.isEmpty());
		assertEquals(5, set.size());
		
		assertEquals("one", set.get("one"));
		assertEquals("two", set.get("two"));
		assertEquals("three", set.get("three"));
		assertEquals("four", set.get("four"));
		assertEquals("five", set.get("five"));
		assertNull(set.get("six"));
		assertNull(set.get(" one"));
		assertNull(set.get("five "));
		assertNull(set.get(null));
		
		assertFalse(set.put("one"));
		assertTrue(set.put("six"));
		assertEquals("six", set.get("six"));
		assertFalse(set.isEmpty());
		assertEquals(6, set.size());
		
		assertTrue(set.put(null));
		assertNull(set.get(null));
	}
	
	@Test
	void testContains() {
		SetLinkedList<String> empty = new SetLinkedList<String>();
		assertFalse(empty.contains("one"));
		assertFalse(empty.contains(null));
		
		SetLinkedList<String> set = makeSet();
		assertTrue(set.contains("one"));
		assertTrue(set.contains("two"));
		assertTrue(set.contains("three"));
		assertTrue(set.contains("four"));
		assertTrue(set.contains("five"));
		assertFalse(set.contains("six"));
		assertFalse(set.contains(null));
		
		set.put("six");
		set.put(null);
		
		assertTrue(set.contains("six"));
		assertFalse(set.contains(null));
	}
	
	@Test
	void testReplace() {
		SetLinkedList<String> empty = new SetLinkedList<String>();
		assertNull(empty.replace("one"));
		assertNull(empty.replace(null));
		
		SetLinkedList<String> set = makeSet();
		assertEquals("one", set.replace("one"));
		assertNull(set.replace("six"));
		assertNull(set.replace(null));
		assertEquals(5, set.size());
		assertTrue(set.contains("one"));
	}
	
	@Test
	void testRemove() {
		SetLinkedList<String> empty = new SetLinkedList<String>();
		assertNull(empty.remove(null));
		assertNull(empty.remove("one"));
		
		SetLinkedList<String> set = makeSet();
		assertEquals("one", set.remove("one"));
		assertNull(set.remove("six"));
		assertNull(set.remove(null));
		assertEquals(4, set.size());
		assertFalse(set.contains("one"));
		
		assertEquals("two", set.remove("two"));
		assertEquals("four", set.remove("four"));
		assertEquals("five", set.remove("five"));
		assertEquals(1, set.size());
		assertFalse(set.isEmpty());
		assertFalse(set.contains("two"));
		assertFalse(set.contains("five"));
		assertFalse(set.contains("four"));
		assertTrue(set.contains("three"));
		
		assertEquals("three", set.remove("three"));
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
		assertFalse(set.contains("three"));
	}
	
	@Test
	void testIterator() {
		Iterator<String> empty = (new SetLinkedList<String>()).iterator();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);
		
		Iterator<String> it = makeSet().iterator();
		assertTrue(it.hasNext());
		assertEquals("one", it.next());
		assertTrue(it.hasNext());
		assertEquals("two", it.next());
		assertTrue(it.hasNext());
		assertEquals("three", it.next());
		assertTrue(it.hasNext());
		assertEquals("four", it.next());
		assertTrue(it.hasNext());
		assertEquals("five", it.next());
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
		
		it.rewind();
		assertTrue(it.hasNext());
		assertEquals("one", it.next());
		assertTrue(it.hasNext());
		assertEquals("two", it.next());
		assertTrue(it.hasNext());
		assertEquals("three", it.next());
		assertTrue(it.hasNext());
		assertEquals("four", it.next());
		assertTrue(it.hasNext());
		assertEquals("five", it.next());
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
	}
}
