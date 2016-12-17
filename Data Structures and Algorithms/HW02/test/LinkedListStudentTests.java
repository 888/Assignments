import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

/**
 * Student JUnit tests for Homework 1.
 *
 * @version 1.1
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedListStudentTests {

    private DoublyLinkedList<String> list;

    private static final int TIMEOUT = 200;

    private static final String FOO = "foo";
    private static final String BAR = "bar";
    private static final String BAZ = "baz";
    private static final String QUX = "qux";

    @Before
    public void setup() {
        list = new DoublyLinkedList<>();
    }

    /**
     * A helper method for various tests.
     *
     * First, this method tests isEmpty, the nullity of head/tail "pointers," and size.
     * All elements in actual are compared against the elements in expected in a for loop,
     * the traversal of which also tests the validity of the head/tail and previous/next "pointers."
     *
     * @param method - String for assertion message, e.g. "Did addToFront update size?"
     * @param expected - An array of the elements expected
     * @param actual - The actual list to be tested
     */
    private void assertDoublyLinkedListEquals(String method, Object[] expected, DoublyLinkedList<String> actual) {
        if (expected.length == 0) {
            assertTrue(actual.isEmpty());
            assertNull(list.getHead());
            assertNull(list.getTail());
            assertEquals("Did " + method + " update size?", expected.length, actual.size());
        } else {
            assertFalse(actual.isEmpty());
            assertNotNull(list.getHead());
            assertNotNull(list.getTail());
            assertEquals("Did " + method + " update size?", expected.length, actual.size());
            LinkedListNode foo = list.getHead();
            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], foo.getData());
                if (i == 0) {
                    assertEquals(list.getHead(), foo); // tests nothing
                    assertNull(foo.getPrevious());
                } else {
                    assertNotNull(foo.getPrevious());
                    assertEquals(expected[i - 1], foo.getPrevious().getData());
                }
                if (i == expected.length - 1) {
                    assertEquals(list.getTail(), foo);
                    assertNull(foo.getNext());
                } else {
                    assertNotNull(foo.getNext());
                    assertEquals(expected[i + 1], foo.getNext().getData());
                }
                foo = foo.getNext();
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_00_constructor() {
        // Nothing to see here!
    }

    @Test(timeout = TIMEOUT)
    public void test_01_addToFront() {
        // test exception
        try {
            list.addToFront(null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("addToFront must throw java.lang.IllegalArgumentException if data is null.",
                    IllegalArgumentException.class, e.getClass());
        }
        // test function
        // test addToFront(data) for empty list
        list.addToFront(BAZ);
        assertDoublyLinkedListEquals("addToFront", new Object[]{BAZ}, list);
        // test addToFront(data) for non-empty, one element list
        list.addToFront(BAR);
        assertDoublyLinkedListEquals("addToFront", new Object[]{BAR, BAZ}, list);
        // test addToFront(data) for non-empty, two element list
        list.addToFront(FOO);
        assertDoublyLinkedListEquals("addToFront", new Object[]{FOO, BAR, BAZ}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_02_addToBack() {
        // test exception
        try {
            list.addToBack(null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("addToBack must throw java.lang.IllegalArgumentException if data is null.",
                    IllegalArgumentException.class, e.getClass());
        }
        // test function
        // test addToBack(data) for empty list
        list.addToBack(FOO);
        assertDoublyLinkedListEquals("addToBack", new Object[]{FOO}, list);
        // test addToBack(data) for non-empty, one element list
        list.addToBack(BAR);
        assertDoublyLinkedListEquals("addToBack", new Object[]{FOO, BAR}, list);
        // test addToBack(data) for non-empty, two element list
        list.addToBack(BAZ);
        assertDoublyLinkedListEquals("addToBack", new Object[]{FOO, BAR, BAZ}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_03_addAtIndex() {
        // test exceptions
        String message = "addAtIndex must throw java.lang.IndexOutOfBoundsException" +
                " if index is negative or index > size.";
        for (int i: new int[]{-1, 1}) {
            try {
                list.addAtIndex(i, "");
                Assert.fail();
            } catch (Exception e) {
                assertEquals(message, IndexOutOfBoundsException.class, e.getClass());
            }
        }
        message = "addAtIndex must throw java.lang.IllegalArgumentException if data is null.";
        try {
            list.addAtIndex(0, null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals(message, IllegalArgumentException.class, e.getClass());
        }
        // test function
        // test addAtIndex(0, data) for empty list
        list.addAtIndex(0, BAZ);
        assertDoublyLinkedListEquals("addAtIndex", new Object[]{BAZ}, list);
        // test addAtIndex(0, data) for non-empty, one element list
        list.addAtIndex(0, BAR);
        assertDoublyLinkedListEquals("addAtIndex", new Object[]{BAR, BAZ}, list);
        // test addAtIndex(0, data) for non-empty, two element list
        list.addAtIndex(0, FOO);
        assertDoublyLinkedListEquals("addAtIndex", new Object[]{FOO, BAR, BAZ}, list);
        list = new DoublyLinkedList<>(); // reset
        // test addAtIndex(size, data) for empty list // tests nothing
        list.addAtIndex(0, FOO);
        // assertDoublyLinkedListEquals("addAtIndex", new Object[]{FOO}, list);
        // test addAtIndex(size, data) for non-empty, one element list
        list.addAtIndex(1, BAZ);
        assertDoublyLinkedListEquals("addAtIndex", new Object[]{FOO, BAZ}, list);
        // test addAtIndex(size, data) for non-empty, two element list
        list.addAtIndex(2, QUX);
        assertDoublyLinkedListEquals("addAtIndex", new Object[]{FOO, BAZ, QUX}, list);
        // test addAtIndex(size / 2, data) for non-empty list
        list.addAtIndex(1, BAR);
        assertDoublyLinkedListEquals("addAtIndex", new Object[]{FOO, BAR, BAZ, QUX}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_04_removeFromFront() {
        // test no exceptions
        // test function
        // test removeFromFront() for empty list
        assertNull(list.removeFromFront());
        String dat = FOO;
        list.addToFront(dat);
        // test removeFromFront() for non-empty, one element list
        assertEquals(dat, list.removeFromFront());
        assertDoublyLinkedListEquals("removeFromFront", new String[]{}, list);
        list.addToFront(FOO);
        list.addToFront(BAR);
        // test removeFromFront() for non-empty, two element list
        assertEquals(BAR, list.removeFromFront());
        assertDoublyLinkedListEquals("removeFromFront", new String[]{FOO}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_05_removeFromBack() {
        // test no exceptions
        // test function
        // test removeFromBack() for empty list
        assertNull(list.removeFromBack());
        list.addToFront(FOO);
        // test removeFromBack() for non-empty, one element list
        assertEquals(FOO, list.removeFromBack());
        assertDoublyLinkedListEquals("removeFromBack", new String[]{}, list);
        list.addToFront(BAR);
        list.addToFront(FOO);
        // test removeFromBack() for non-empty, two element list
        assertEquals(BAR, list.removeFromBack());
        assertDoublyLinkedListEquals("removeFromBack", new String[]{FOO}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_06_removeAtIndex() {
        // test exceptions
        String message = "removeAtIndex must throw java.lang.IndexOutOfBoundsException" +
                " if index is negative or index >= size.";
        for (int i: new int[]{-1, 0, 1}) {
            try {
                list.removeAtIndex(i);
                Assert.fail();
            } catch (Exception e) {
                assertEquals(message, IndexOutOfBoundsException.class, e.getClass());
            }
        }
        // test function
        // test removeAtIndex(0) for non-empty, one element list
        list.addToFront(FOO);
        assertEquals(FOO, list.removeAtIndex(0));
        assertDoublyLinkedListEquals("removeAtIndex", new String[]{}, list);
        // test removeAtIndex(0) for non-empty, two element list
        list.addToFront(BAR);
        list.addToFront(FOO);
        assertEquals(FOO, list.removeAtIndex(0));
        assertDoublyLinkedListEquals("removeAtIndex", new String[]{BAR}, list);
        // test removeAtIndex(size - 1) for non-empty, one element list // tests nothing
        assertEquals(BAR, list.removeAtIndex(0));
        // test removeAtIndex(size - 1) for non-empty, two element list
        list.addToFront(BAR);
        list.addToFront(FOO);
        assertEquals(BAR, list.removeAtIndex(1));
        assertDoublyLinkedListEquals("removeAtIndex", new String[]{FOO}, list);
        list = new DoublyLinkedList<>(); // reset
        // test removeAtIndex(size / 2) for non-empty list
        list.addToFront(BAZ);
        list.addToFront(BAR);
        list.addToFront(FOO);
        assertEquals(BAR, list.removeAtIndex(1));
        assertDoublyLinkedListEquals("removeAtIndex", new String[]{FOO, BAZ}, list);
    }

    /**
     * TODO - This test is incomplete
     */
    @Test(timeout = TIMEOUT)
    public void test_07_removeAllOccurrences() {
        // test exception
        try {
            list.removeAllOccurrences(null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("removeAllOccurrences must throw java.lang.IllegalArgumentException if data is null.",
                    IllegalArgumentException.class, e.getClass());
        }
        list.addToFront(FOO);
        list.addToFront(new String("foo"));
        assertFalse(list.removeAllOccurrences(BAR));
        assertTrue(list.removeAllOccurrences(FOO));
        assertDoublyLinkedListEquals("removeAllOccurrences", new String[]{}, list);
    }

    @Test(timeout = TIMEOUT)
    public void test_08_get() {
        // test exceptions
        String message = "get must throw java.lang.IndexOutOfBoundsException" +
                " if index is negative or index >= size.";
        for (int i: new int[]{-1, 0, 1}) {
            try {
                list.get(i);
                Assert.fail();
            } catch (Exception e) {
                assertEquals(message, IndexOutOfBoundsException.class, e.getClass());
            }
        }
        // test function
        list.addToFront("0");
        assertEquals("0", list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void test_09_toArray() {
        // test no exceptions
        // test function
        list.addToFront(BAR);
        list.addToFront(FOO);
        assertArrayEquals(new String[]{FOO, BAR}, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_10_clear() {
        // test no exceptions
        // test function
        list.addToFront(FOO);
        list.clear();
        assertDoublyLinkedListEquals("clear", new String[]{}, list);
    }

}