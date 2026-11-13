package dataStructures.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.Test;
import dataStructures.SepChainHashTable;
import dataStructures.exceptions.NoSuchElementException;
import dataStructures.Map.Entry;
import dataStructures.Iterator;
import dataStructures.SetLinkedList;

class SepChainHashTableTest {

	private SepChainHashTable<String, String> makeTable() {
		SepChainHashTable<String, String> ht = new SepChainHashTable<String, String>();
		ht.put("one", "a");
		ht.put("two", "b");
		ht.put("three", "c");
		ht.put("four", "d");
		ht.put("five", "e");
		return ht;
	}

	@Test
	void testPutAndGet() {
		SepChainHashTable<String, String> empty = new SepChainHashTable<String, String>();
		assertTrue(empty.isEmpty());
		assertEquals(0, empty.size());
		assertNull(empty.get("one"));

		SepChainHashTable<String, String> ht = makeTable();
		assertFalse(ht.isEmpty());
		assertEquals(5, ht.size());
		assertEquals("a", ht.get("one"));
		assertEquals("b", ht.get("two"));
		assertEquals("c", ht.get("three"));
		assertEquals("d", ht.get("four"));
		assertEquals("e", ht.get("five"));
		assertNull(ht.get("six"));

		assertEquals("a", ht.put("one", "A"));
		assertEquals("c", ht.put("three", "C"));
		assertEquals("e", ht.put("five", "E"));
		assertEquals(5, ht.size());
		assertNull(ht.put("six", "f"));
		assertNull(ht.put("seven", "g"));
		assertEquals(7, ht.size());
		assertEquals("f", ht.put("six", "F"));

		assertEquals("A", ht.get("one"));
		assertEquals("b", ht.get("two"));
		assertEquals("C", ht.get("three"));
		assertEquals("d", ht.get("four"));
		assertEquals("E", ht.get("five"));
		assertEquals("F", ht.get("six"));
		assertEquals("g", ht.get("seven"));
		assertEquals(7, ht.size());

		SepChainHashTable<Integer, String> smallTable = new SepChainHashTable<Integer, String>(1);
		assertNull(smallTable.put(0, "a"));
		assertNull(smallTable.put(11, "b"));
		assertNull(smallTable.put(22, "c"));
		assertNull(smallTable.put(33, "d"));
		assertNull(smallTable.put(44, "e"));
		assertEquals(5, smallTable.size());

		assertEquals("a", smallTable.get(0));
		assertEquals("b", smallTable.get(11));
		assertEquals("c", smallTable.get(22));
		assertEquals("d", smallTable.get(33));
		assertEquals("e", smallTable.get(44));
	}

	@Test
	void testKeys() {
		Iterator<String> empty = (new SepChainHashTable<String, String>()).keys();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);

		Iterator<String> it = makeTable().keys();
		SetLinkedList<String> set = new SetLinkedList<String>();

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
		assertEquals(5, set.size());
		it.rewind();

		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));

		assertTrue(set.contains("one"));
		assertTrue(set.contains("two"));
		assertTrue(set.contains("three"));
		assertTrue(set.contains("four"));
		assertTrue(set.contains("five"));
	}

	@Test
	void testValues() {
		Iterator<String> empty = (new SepChainHashTable<String, String>()).values();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);

		Iterator<String> it = makeTable().values();
		SetLinkedList<String> set = new SetLinkedList<String>();

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
		assertEquals(5, set.size());
		it.rewind();

		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));

		assertTrue(set.contains("a"));
		assertTrue(set.contains("b"));
		assertTrue(set.contains("c"));
		assertTrue(set.contains("d"));
		assertTrue(set.contains("e"));
	}

	@Test
	void testIterator() {
		Iterator<Entry<String, String>> empty = (new SepChainHashTable<String, String>()).iterator();
		assertFalse(empty.hasNext());
		assertThrows(NoSuchElementException.class, empty::next);

		Iterator<Entry<String, String>> it = makeTable().iterator();
		SetLinkedList<Entry<String, String>> set = new SetLinkedList<Entry<String, String>>();

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
		assertEquals(5, set.size());
		it.rewind();

		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));
		assertTrue(set.contains(it.next()));

		assertEquals("a", set.get(new Entry<String, String>("one", null)).value());
		assertEquals("b", set.get(new Entry<String, String>("two", null)).value());
		assertEquals("c", set.get(new Entry<String, String>("three", null)).value());
		assertEquals("d", set.get(new Entry<String, String>("four", null)).value());
		assertEquals("e", set.get(new Entry<String, String>("five", null)).value());
	}

	@Test
	void testRemove() {
		SepChainHashTable<String, String> empty = new SepChainHashTable<String, String>();
		assertNull(empty.remove("one"));
		assertEquals(0, empty.size());

		SepChainHashTable<String, String> ht = makeTable();
		assertFalse(ht.isEmpty());
		assertEquals(5, ht.size());
		assertNull(ht.remove("six"));

		assertEquals("a", ht.remove("one"));
		assertEquals("c", ht.remove("three"));
		assertEquals("e", ht.remove("five"));
		assertFalse(ht.isEmpty());
		assertEquals(2, ht.size());

		assertNull(ht.get("one"));
		assertEquals("b", ht.get("two"));
		assertNull(ht.get("three"));
		assertEquals("d", ht.get("four"));
		assertNull(ht.get("five"));

		assertNull(ht.put("six", "f"));
		assertEquals("b", ht.put("two", "B"));
		assertEquals(3, ht.size());

		assertEquals("B", ht.remove("two"));
		assertEquals("d", ht.remove("four"));
		assertEquals("f", ht.remove("six"));
		assertTrue(ht.isEmpty());
		assertEquals(0, ht.size());

	}

	@Test
	@SuppressWarnings("unchecked")
	void testSerialization() throws IOException, ClassNotFoundException {
		SepChainHashTable<String, String> table = makeTable();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(table);
		}

		SepChainHashTable<String, String> deserializedTable;
		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
			deserializedTable = (SepChainHashTable<String, String>) ois.readObject();
		}

		assertTrue(table.size() == deserializedTable.size() && deserializedTable.size() == 5);
		assertEquals(table.get("one"), deserializedTable.get("one"));
		assertEquals(table.get("two"), deserializedTable.get("two"));
		assertEquals(table.get("three"), deserializedTable.get("three"));
		assertEquals(table.get("four"), deserializedTable.get("four"));
		assertEquals(table.get("five"), deserializedTable.get("five"));
	}

}
