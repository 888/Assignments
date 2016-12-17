import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

/**
 * Student JUnit tests for Homework 2.
 *
 * @author Timothy J. Aveni
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DoublyLinkedListTests {

    private LinkedListInterface<String> list;

    private static final int TIMEOUT = 200;

    private <T> int getListSize(LinkedListInterface<T> l) {
        // Compute the list size with a O(n) algorithm.
        // This isn't acceptable in the submitted code,
        //     but it's used here to avoid relying on the .size() implementation.

        int size = 0;
        LinkedListNode<T> node = l.getHead();
        while (node != null) {
            size++;
            node = node.getNext();
        }

        return size;
    }

    private <T> void assertLinkedListEquals(LinkedListInterface<T> l, T[] expected) {
        // this method also tests for consistency in next/previous references.
        assertEquals("List size is not consistent with .size()", l.size(), getListSize(l));
        assertEquals(l.size(), expected.length);

        LinkedListNode<T> node = l.getHead();
        if (l.size() == 0) {
            assertNull(node);
            assertNull(l.getTail());
            return;
        }

        assertNull(node.getPrevious());
        int i = 0;
        boolean continueLoop = node != null;
        while (continueLoop) {
            assertEquals(node.getData(), expected[i]);
            i++;
            if (node.getNext() == null) {
                continueLoop = false;
                assertEquals(l.getTail(), node);
            } else {
                node = node.getNext();
            }
        }

        // do it AGAIN, but BACKWARDS this time.
        LinkedListNode<T> node2 = l.getTail();
        assertNull(l.getTail().getNext());
        int i2 = l.size() - 1;
        boolean continueLoop2 = node2 != null;
        while (continueLoop2) {
            assertEquals(node2.getData(), expected[i2]);
            i2--;
            if (node2.getPrevious() == null) {
                continueLoop2 = false;
                assertEquals(l.getHead(), node2);
            } else {
                node2 = node2.getPrevious();
            }
        }
    }

    private void assertException(String message, Class<? extends Exception> exceptionClass, Runnable code) {
        try {
            code.run();
            Assert.fail(message);
        } catch (Exception e) {
            assertEquals(message, exceptionClass, e.getClass());
            assertNotNull("Exception messages must not be empty", e.getMessage());
            assertNotEquals("Exception messages must not be empty", e.getMessage(), "");
        }
    }

    @Before
    public void setup() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void test_00_constructor() {
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void test_01_addAtIndex() {
        assertException(
                "Adding a negative data element should throw an IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.addAtIndex(0,  null));

        assertException(
                "Adding at a negative index should throw an IndexOutOfBounds exception",
                IndexOutOfBoundsException.class,
                () -> list.addAtIndex(-1,  "fail"));

        assertException(
                "Adding at an index > size should throw an IndexOutOfBounds exception",
                IndexOutOfBoundsException.class,
                () -> list.addAtIndex(1, "fail"));

        list.addAtIndex(0,  "01-a");

        String[] expected = {"01-a"};
        assertLinkedListEquals(list, expected);

        list.addAtIndex(0,  "01-b");
        list.addAtIndex(0,  "01-c");

        assertException("Adding at an index > size should throw an IndexOutOfBounds exception",
                IndexOutOfBoundsException.class,
                () -> list.addAtIndex(4, "fail"));

        String[] expected2 = {"01-c", "01-b", "01-a"};
        assertLinkedListEquals(list, expected2);

        list.addAtIndex(1, "01-d");
        String[] expected3 = {"01-c", "01-d", "01-b", "01-a"};
        assertLinkedListEquals(list, expected3);

        list.addAtIndex(4, "01-e");
        String[] expected4 = {"01-c", "01-d", "01-b", "01-a", "01-e"};
        assertLinkedListEquals(list, expected4);
    }

    @Test(timeout = TIMEOUT)
    public void test_02_addToFront() {
        assertException(
                "Adding a negative data element should throw an IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.addToFront(null));

        list.addToFront("02-a");
        String[] expected = {"02-a"};
        assertLinkedListEquals(list, expected);

        list.addToFront("02-b");
        String[] expected2 = {"02-b", "02-a"};
        assertLinkedListEquals(list, expected2);
    }

    @Test(timeout = TIMEOUT)
    public void test_03_addToBack() {
        assertException(
                "Adding a negative data element should throw an IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.addToBack(null));

        list.addToBack("03-a");
        String[] expected = {"03-a"};
        assertLinkedListEquals(list, expected);

        list.addToBack("03-b");
        String[] expected2 = {"03-a", "03-b"};
        assertLinkedListEquals(list, expected2);
    }

    @Test(timeout = TIMEOUT)
    public void test_04_removeAtIndex() {
        // depends on addToBack
        assertException(
                "Removing at a negative index should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(-1));

        assertException(
                "Removing at an index >= size should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(0));


        list.addToBack("04-a");
        list.addToBack("04-b");
        list.addToBack("04-c");
        list.addToBack("04-d");
        list.addToBack("04-e");

        assertException(
                "Removing at an index >= size should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(5));

        list.removeAtIndex(0);
        String[] expected = {"04-b", "04-c", "04-d", "04-e"};
        assertLinkedListEquals(list, expected);

        list.removeAtIndex(3);
        String[] expected2 = {"04-b", "04-c", "04-d"};
        assertLinkedListEquals(list, expected2);

        list.removeAtIndex(1);
        String[] expected3 = {"04-b", "04-d"};
        assertLinkedListEquals(list, expected3);

        list.removeAtIndex(0);
        list.removeAtIndex(0);

        assertException(
                "Removing at an index >= size should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(0));
    }

    @Test(timeout = TIMEOUT)
    public void test_05_removeFromFront() {
        // depends on addToBack
        assertEquals(list.removeFromFront(), null);
        assertEquals(list.size(), 0);

        list.addToBack("05-a");
        list.addToBack("05-b");

        String result = list.removeFromFront();
        assertEquals(result, "05-a");

        String[] expected = {"05-b"};
        assertLinkedListEquals(list, expected);

        String result2 = list.removeFromFront();
        assertEquals(result2, "05-b");

        String[] expected2 = {};
        assertLinkedListEquals(list, expected2);

        assertEquals(list.removeFromFront(), null);
        assertEquals(list.size(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void test_06_removeFromBack() {
        // depends on addToBack
        assertEquals(list.removeFromBack(), null);
        assertEquals(list.size(), 0);

        list.addToBack("06-a");
        list.addToBack("06-b");

        String result = list.removeFromBack();
        assertEquals(result, "06-b");

        String[] expected = {"06-a"};
        assertLinkedListEquals(list, expected);

        String result2 = list.removeFromBack();
        assertEquals(result2, "06-a");

        String[] expected2 = {};
        assertLinkedListEquals(list, expected2);

        assertEquals(list.removeFromBack(), null);
        assertEquals(list.size(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void test_07_removeAllOccurrences() {
        // depends on addToBack
        assertException(
                "Passing null to removeAllOccurrences should throw an IllegalArgumentException",
                IllegalArgumentException.class,
                () -> list.removeAllOccurrences(null));

        assertFalse(list.removeAllOccurrences(""));

        list.addToBack("07-a");
        list.addToBack("07-b");
        list.addToBack("07-c");

        assertFalse(list.removeAllOccurrences("07-d"));
        String[] expected = {"07-a", "07-b", "07-c"};
        assertLinkedListEquals(list, expected);

        assertTrue(list.removeAllOccurrences("07-b"));
        String[] expected2 = {"07-a", "07-c"};
        assertLinkedListEquals(list, expected2);

        assertTrue(list.removeAllOccurrences("07-a"));
        String[] expected3 = {"07-c"};
        assertLinkedListEquals(list, expected3);

        assertTrue(list.removeAllOccurrences("07-c"));
        String[] expected4 = {};
        assertLinkedListEquals(list, expected4);

        list.addToBack("07-d");
        list.addToBack("07-d");
        list.addToBack("07-e");

        assertTrue(list.removeAllOccurrences("07-d"));
        String[] expected5 = {"07-e"};
        assertLinkedListEquals(list, expected5);

        list.addToBack("07-e");
        assertFalse(list.removeAllOccurrences("07-d"));
        String[] expected6 = {"07-e", "07-e"};
        assertLinkedListEquals(list, expected6);

        assertTrue(list.removeAllOccurrences("07-e"));
        String[] expected7 = {};
        assertLinkedListEquals(list, expected7);

        // thanks to Tyler Flynn for the idea to check equality correctly
        // I use a special object to make it more obvious what's happening here.
        LinkedListInterface<EqualObject> newList = new DoublyLinkedList<EqualObject>();
        newList.addToBack(new EqualObject());
        newList.addToBack(new EqualObject());

        assertTrue(newList.removeAllOccurrences(new EqualObject()));
        EqualObject[] expected8 = {};
        assertLinkedListEquals(newList, expected8);
    }

    @Test(timeout = TIMEOUT)
    public void test_08_get() {
        // depends on addToBack
        assertException(
                "Retrieving at a negative index should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.get(-1));

        assertException(
                "Retrieving at an index >= size should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.get(0));

        list.addToBack("08-a");
        list.addToBack("08-b");
        list.addToBack("08-c");

        assertException(
                "Retrieving at an index >= size should throw an IndexOutOfBoundsException",
                IndexOutOfBoundsException.class,
                () -> list.get(3));

        assertEquals(list.get(0), "08-a");
        assertEquals(list.get(1), "08-b");
        assertEquals(list.get(2), "08-c");

        String[] expected = {"08-a", "08-b", "08-c"};
        assertLinkedListEquals(list, expected);
    }

    @Test(timeout = TIMEOUT)
    public void test_09_toArray() {
        // depends on addToBack
        String[] expected = {};
        assertArrayEquals(list.toArray(), expected);

        list.addToBack("09-a");
        String[] expected2 = {"09-a"};
        assertArrayEquals(list.toArray(), expected2);

        list.addToBack("09-b");
        String[] expected3 = {"09-a", "09-b"};
        assertArrayEquals(list.toArray(), expected3);

        list.removeFromFront();
        String[] expected4 = {"09-b"};
        assertArrayEquals(list.toArray(), expected4);
    }

    @Test(timeout = TIMEOUT)
    public void test_10_isEmpty() {
        // depends on addToBack
        assertTrue(list.isEmpty());

        list.addToBack("10-a");
        assertFalse(list.isEmpty());

        list.removeFromFront();
        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void test_11_size() {
        // depends on addToBack, addToFront, removeFromBack, removeFromFront
        // .size() is mostly tested in the assertLinkedListEquals method,
        //     so this is a bigger test that tests .size() behaviour with some random operations.
        assertEquals(list.size(), 0);

        list.addToBack("10-a");
        assertEquals(list.size(), 1);

        list.removeFromFront();
        assertEquals(list.size(), 0);

        int expectedSize = 0;
        // the fun part
        for (int i = 0; i < 100; i++) {
            if (Math.random() > .5) {
                list.addToBack("" + Math.random());
            } else {
                list.addToFront("" + Math.random());
            }

            expectedSize++;
        }

        for (int i = 0; i < 1000; i++) {
            if (Math.random() > .5) {
                if (Math.random() > .5) {
                    list.addToBack("" + Math.random());
                } else {
                    list.addToFront("" + Math.random());
                }

                expectedSize++;
            } else {
                if (Math.random() > .5) {
                    list.removeFromBack();
                } else {
                    list.removeFromFront();
                }

                expectedSize--;
                if (expectedSize < 0) {
                    expectedSize = 0;
                }
            }
        }

        assertEquals(list.size(), expectedSize);
    }

    @Test(timeout = TIMEOUT)
    public void test_12_clear() {
        // depends on addToBack
        list.clear();
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
        assertTrue(list.size() == 0);

        list.addToBack("12-a");
        list.clear();
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
        assertTrue(list.size() == 0);

        list.addToBack("12-b");
        list.addToBack("12-c2");
        list.clear();
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
        assertTrue(list.size() == 0);
    }

    private class EqualObject {
        @Override
        public boolean equals(Object other) {
           return true;
        }
    }
}