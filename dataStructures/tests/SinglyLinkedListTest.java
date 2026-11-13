package dataStructures.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

import org.junit.jupiter.api.Test;

import dataStructures.Iterator;
import dataStructures.SinglyLinkedList;
import dataStructures.exceptions.NoSuchElementException;
import dataStructures.exceptions.InvalidPositionException;

class SinglyLinkedListTest {

	private SinglyLinkedList<String> makeList() {
		SinglyLinkedList<String> list = new SinglyLinkedList<String>();
		list.addLast("one");
		list.addLast("two");
		list.addLast("three");
		list.addLast("four");
		list.addLast("five");
		return list;
	}

	@Test
	void testAddLastAndGet() {
		SinglyLinkedList<String> empty = new SinglyLinkedList<String>();
		assertTrue(empty.isEmpty());
		assertEquals(0, empty.size());
		assertThrows(NoSuchElementException.class, empty::getFirst);
		assertThrows(NoSuchElementException.class, empty::getLast);

		SinglyLinkedList<String> list = makeList();
		assertFalse(list.isEmpty());
		assertEquals(5, list.size());
		assertEquals("one", list.getFirst());
		assertEquals("five", list.getLast());

		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		assertEquals("four", list.get(3));
		assertEquals("five", list.get(4));
		assertThrows(InvalidPositionException.class, () -> list.get(-1));
		assertThrows(InvalidPositionException.class, () -> list.get(7));

	}

	@Test
	void testAddAndAddFirst() {
		SinglyLinkedList<String> empty = new SinglyLinkedList<String>();
		empty.addFirst("one");
		assertEquals("one", empty.getFirst());
		assertEquals("one", empty.getLast());

		SinglyLinkedList<String> list = makeList();
		list.addFirst("six");
		assertEquals("six", list.getFirst());

		list.add(0, "seven");
		assertEquals("seven", list.getFirst());
		list.add(7, "eight");
		assertEquals("eight", list.getLast());

		list.add(2, "nine");
		assertEquals("nine", list.get(2));
		list.add(4, "ten");
		assertEquals("ten", list.get(4));

		assertEquals("six", list.get(1));
		assertEquals("one", list.get(3));
		assertEquals("two", list.get(5));
		assertEquals("three", list.get(6));
		assertEquals("eight", list.get(9));
		assertFalse(list.isEmpty());
		assertEquals(10, list.size());

		assertThrows(InvalidPositionException.class, () -> list.add(-1, "hello"));
		assertThrows(InvalidPositionException.class, () -> list.add(11, "hello"));

	}

	@Test
	void testIterator() {
		SinglyLinkedList<String> empty = new SinglyLinkedList<String>();
		Iterator<String> emptyIt = empty.iterator();
		assertFalse(emptyIt.hasNext());
		assertThrows(NoSuchElementException.class, emptyIt::next);

		SinglyLinkedList<String> list = makeList();
		Iterator<String> it = list.iterator();
		assertTrue(it.hasNext());
		assertEquals("one", it.next());
		assertEquals("two", it.next());
		assertEquals("three", it.next());
		assertEquals("four", it.next());
		assertEquals("five", it.next());

		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, it::next);
		it.rewind();

		assertTrue(it.hasNext());
		assertEquals("one", it.next());
		assertEquals("two", it.next());
		assertEquals("three", it.next());
		assertEquals("four", it.next());
		assertEquals("five", it.next());
	}

	@Test
	void testIndexOf() {
		SinglyLinkedList<String> list = makeList();
		assertEquals(0, list.indexOf("one"));
		assertEquals(1, list.indexOf("two"));
		assertEquals(2, list.indexOf("three"));
		assertEquals(3, list.indexOf("four"));
		assertEquals(4, list.indexOf("five"));
		assertEquals(-1, list.indexOf("one "));
		assertEquals(-1, list.indexOf(" five"));
		assertEquals(-1, list.indexOf("six"));
		assertEquals(-1, list.indexOf(null));
	}

	@Test
	void testNull() {
		SinglyLinkedList<String> list = makeList();
		list.add(3, null);
		assertEquals(3, list.indexOf(null));
		list.addFirst(null);
		list.addLast(null);
		assertNull(list.get(4));
		assertNull(list.getFirst());
		assertNull(list.getLast());
		assertEquals("one", list.get(1));
		assertEquals("four", list.get(5));
		assertEquals(8, list.size());
		assertEquals(0, list.indexOf(null));
	}

	@Test
	void testRemove() {
		SinglyLinkedList<String> empty = new SinglyLinkedList<String>();
		assertThrows(NoSuchElementException.class, empty::removeFirst);
		assertThrows(NoSuchElementException.class, empty::removeLast);

		SinglyLinkedList<String> list = makeList();
		assertEquals("one", list.removeFirst());
		assertEquals("five", list.removeLast());
		assertEquals("two", list.get(0));
		assertEquals("three", list.get(1));
		assertEquals("four", list.get(2));
		assertEquals(3, list.size());

		assertThrows(InvalidPositionException.class, () -> list.remove(-1));
		assertThrows(InvalidPositionException.class, () -> list.remove(4));
		assertEquals("three", list.remove(1));
		assertEquals("four", list.remove(1));
		assertEquals("two", list.remove(0));

		assertTrue(list.isEmpty());
		assertEquals(0, list.size());
		assertThrows(NoSuchElementException.class, list::getFirst);
		assertThrows(NoSuchElementException.class, list::getLast);
		
		list.addFirst("element");
		assertEquals(1, list.size());
		assertEquals("element", list.removeFirst());
		assertThrows(NoSuchElementException.class, list::getFirst);
		assertEquals(0, list.size());
		
		list.addLast("element");
		assertEquals(1, list.size());
		assertEquals("element", list.removeLast());
		assertThrows(NoSuchElementException.class, list::getLast);
		assertEquals(0, list.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	void testSerialization() throws IOException, ClassNotFoundException {
		SinglyLinkedList<String> list = makeList();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(list);
		}

		SinglyLinkedList<String> deserializedList;
		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
			deserializedList = (SinglyLinkedList<String>) ois.readObject();
		}
		
		assertTrue(list.size() == deserializedList.size() && deserializedList.size() == 5);
		assertEquals(list.getFirst(), deserializedList.getFirst());
		assertEquals(list.getLast(), deserializedList.getLast());
		assertEquals(list.get(0), deserializedList.get(0));
		assertEquals(list.get(1), deserializedList.get(1));
		assertEquals(list.get(2), deserializedList.get(2));
		assertEquals(list.get(3), deserializedList.get(3));
		assertEquals(list.get(4), deserializedList.get(4));
	}
}
