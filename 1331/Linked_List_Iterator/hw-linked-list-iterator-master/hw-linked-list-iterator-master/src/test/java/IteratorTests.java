import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Mark.
 * @version 0.1
 */
public class IteratorTests {
    private MyLinkedList<String> stringlist;
    private MyLinkedList<Integer> integerlist;
    private final String[] data3 = {"Keith", "Chris", "Mark", "Aniruddha",
        "Farhan", "David", "Alex", "Xiaoxi", "James", "Keagan", "Ryan",
        "Susanna", "Harry", "Tejas", "Heather", "Raghav", "Peter"};

    @Before
    public void setup() {
        stringlist = new MyLinkedList<>();
        integerlist = new MyLinkedList<>();
    }

    // Worth 5
    @Test(timeout = 200)
    public void testHasNextEmpty() {
        stringlist.clear();
        try {
            assertFalse("-5 hasNext() does not return false for an empty list.", integerlist.iterator().hasNext());
        } catch (Exception e) {
            fail("-5 an exception was thrown while testing hasNext() with an empty list: " + e);
        }
    }

    // Worth 5
    @Test(timeout = 200)
    public void testHasNextOneElement() {
        stringlist.clear();
        stringlist.add(data3[0]);
        try {
            assertTrue("-5 hasNext() does not return true for a list of one element.", stringlist.iterator().hasNext());
        } catch (Exception e) {
            fail("-5 an exception was thrown while testing hasNext() with a list of one element: " + e);
        } finally {
            stringlist.clear();
        }
    }

    // Worth 5
    @Test(timeout = 200)
    public void testHasNextFullList() {
        stringlist.clear();
        for(String ta : data3) {
            stringlist.add(ta);
        }
        Iterator<String> iterator = stringlist.iterator();
        for (int i = 0; i < data3.length; i++) {
            try {
                assertTrue("-5 hasNext() does not return true for a list of multiple elements.", iterator.hasNext());
                iterator.next();
            } catch (Exception e) {

                fail("-5 an exception was thrown while testing hasNext() and next() with a list of multiple elements: " + e);
            }
        }
    }

    // Worth 5
    @Test(timeout = 200)
    public void testHasNextEndOfList() {
        stringlist.clear();
        for(String ta : data3) {
            stringlist.add(ta);
        }
        Iterator<String> iterator = stringlist.iterator();
        for (int i = 0; i < data3.length; i++) {
            try {
                iterator.next();
            } catch (Exception e) {
                fail("-5 an exception was thrown while testing hasNext() and next() at the end of a list [caused by next()]: " + e);
            }
        }
        boolean result = true;
        try {
            result = iterator.hasNext();
        } catch (Exception e) {
            fail("-5 an exception was thrown while testing hasNext() and next() at the end of a list [caused by hasNext()]: " + e);
        }
        assertFalse("-5 hasNext() does not return false at the end of a list.", result);
    }

    // Worth 10
    @Test(timeout = 200)
    public void testGetNextWhileHasNext() {
        stringlist.clear();
        for(String ta : data3) {
            stringlist.add(ta);
        }
        Iterator<String> iterator = stringlist.iterator();
        for (int i = 0; i < data3.length; i++) {
            try {
                assertTrue("-10 next() does not return the correct element.", iterator.hasNext() && iterator.next().equals(data3[i]));
            } catch (Exception e) {
                fail("-10 an exception was thrown while testing next() while hasNext() is true: " + e);
            }
        }
    }

    // Worth 10
    @Test(timeout = 200)
    public void testGetNextWhileEmpty() {
        stringlist.clear();
        try {
            stringlist.iterator().hasNext();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("-10 an exception occured while calling next() on an empty list that was not a NoSuchElementException: " + e);
        }
    }

    // Worth 10
    @Test(timeout = 200)
    public void testGetNextWhileEndOfList() {
        stringlist.clear();
        for(String ta : data3) {
            stringlist.add(ta);
        }
        Iterator<String> iterator = stringlist.iterator();
        for (int i = 0; i < data3.length; i++) {
            try {
                iterator.next();
            } catch (Exception e) {
                fail("-10 an exception occured while calling next() in the middle of a non-empty list: " + e);
            }
        }
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("-10 an exception occured while calling next() on a non-empty list that was not a NoSuchElementException: " + e);
        }
    }

    // Worth 10
    @Test(timeout = 200)
    public void testRemove() {
        stringlist.clear();
        for(String ta : data3) {
            stringlist.add(ta);
        }
        Iterator<String> iterator = stringlist.iterator();

        MyLinkedList.Node probe = stringlist.getHead();
        for (int i = 0; i < 7; i++) {
            try {
                iterator.next();
            } catch (Exception e) {
                fail("-10 an exception occured while calling next() when testing remove(): " + e);
            }
            probe = probe.getNext();
        }
        try {
            iterator.remove();
        } catch (UnsupportedOperationException e) {
            fail("-10 remove() not implemented.");
        } catch (IllegalStateException e) {
            fail("-10 remove() incorrectly throws IllegalStateException.");
        } catch (Exception e) {
            fail("-10 remove() throws an exception that is not UnsupportedOperationException or IllegalOperationException: " + e);
        }
        for (int i = 0; i < 6; i++) {
            if (!stringlist.get(i).equals(data3[i])) {
                fail("-10 remove() alters elements located before the removed element.");
            }
        }
        String next = iterator.next();
        if (!stringlist.get(6).equals(next) && data3[7].equals(next)) {
            fail("-10 calling next() after remove() does not return the correct element.");
        }
        if (stringlist.size() != (data3.length - 1)) {
            fail("-10 remove() does not decrease the size correctly.");
        }
        assertTrue(true);
    }

    // Worth 5
    @Test(timeout = 200)
    public void testRemoveIllegalOperation() {
        stringlist.clear();
        for(String ta : data3) {
            stringlist.add(ta);
        }
        Iterator<String> iterator = stringlist.iterator();

        try {
            iterator.remove();
            fail("-5 remove() method does not throw IllegalStateException or UnsupportedOperationException when calling remove() without calling next() first.");
        } catch (IllegalStateException e) {
            assertTrue(true);
        } catch (UnsupportedOperationException e) {
            fail("-5 remove() not implemented.");
        } catch (Exception e) {
            fail("-5 an exception occurred while testing remove() without calling next() first. Neither IllegalStateException or UnsupportedOperationException were thrown: " + e);
        }

        try {
            iterator.next();
            iterator.remove();
            iterator.remove();
            fail("-5 remove() method does not throw IllegalStateException or UnsupportedOperationException when calling remove() twice in a row without calling next() in between.");
        } catch (IllegalStateException e) {
            assertTrue(true);
        } catch (UnsupportedOperationException e) {
            fail("-5 remove() not implemented.");
        } catch (Exception e) {
            fail("-5 an exception occurred while testing remove() when callin it twice without calling next() in between. Neither IllegalStateException or UnsupportedOperationException were thrown: " + e);
        }
    }
}
