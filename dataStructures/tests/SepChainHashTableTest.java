package dataStructures.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import dataStructures.*;
import dataStructures.exceptions.NoSuchElementException;
import dataStructures.Map.Entry;

class SepChainHashTableTest {

	private SepChainHashTable<String, String> makeTable() {
		SepChainHashTable<String, String> ht = new SepChainHashTable<String, String>(1);
		ht.put("one", "a");
		ht.put("two", "b");
		ht.put("three", "c");
		ht.put("four", "d");
		ht.put("five", "e");
		ht.put("one", "f");
		return ht;
	}

	@Test
	void testPutAndGet() {
		SepChainHashTable<String, String> ht = makeTable();
		assertEquals("c", ht.get("three"));
		assertEquals("e", ht.get("five"));
		assertEquals("b", ht.get("two"));
		assertEquals("f", ht.get("one"));
		assertEquals("d", ht.get("four"));
	}

	@Test
	void testIterator() {
		SepChainHashTable<String, String> ht = makeTable();
		Iterator<Entry<String, String>> it = ht.iterator();
		Set<Entry<String, String>> set = new SetLinkedList<>();
		assertTrue(it.hasNext());
		set.put(it.next());
		assertTrue(it.hasNext());
		set.put(it.next());
		assertTrue(it.hasNext());
		set.put(it.next());
		assertTrue(it.hasNext());
		set.put(it.next());
		assertTrue(it.hasNext());
		set.put(it.next());
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
		it.rewind();
		assertTrue(it.hasNext());
		set.put(it.next());
	}

	@Test
	void testRemove() {SepChainHashTable<String, String> ht = makeTable();}

	@Test
	void testFull() {
		SepChainHashTable<String, String> ht = makeTable();
	}

}
