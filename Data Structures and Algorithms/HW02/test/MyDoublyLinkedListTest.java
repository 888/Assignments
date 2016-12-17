import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

/**
 * My JUnits test for Homework 2
 *
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyDoublyLinkedListTest {
    private LinkedListInterface<String> list;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() throws Exception {
        list = new DoublyLinkedList<String>();
    }

    @Test(timeout = TIMEOUT)
    public void test01_testAddException() {
        list.addToFront("Foo");
        //Testing for addAtIndex IndexOutOfBoundsException
        for (int i: new int[]{-2, -1, 2, 3}) {
            try {
                list.addAtIndex(i, "");
                Assert.fail();
            } catch (Exception e) {
                assertEquals(IndexOutOfBoundsException.class, e.getClass());
            }
        }
        //Testing for addAtIndex IllegalArgumentException
        for (int i: new int[]{0, 1}) {
            try {
                list.addAtIndex(i, null);
                Assert.fail();
            } catch (Exception e) {
                assertEquals(IllegalArgumentException.class, e.getClass());
            }
        }
        //Testing for addToFront IllegalArgumentException
        try {
            list.addToFront(null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        //Testing for addToBack IllegalArgumentException
        try {
            list.addToBack(null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test(timeout = TIMEOUT)
    public void test02_addAtIndex() {
        assertNull(list.getHead());
        assertNull(list.getTail());

        assertTrue(list.isEmpty());

        assertEquals(0, list.size());

        list.addAtIndex(0, "Aa");

        assertNotNull(list.getHead());
        assertNotNull(list.getTail());

        list.addAtIndex(1, "Bb");
        list.addAtIndex(2, "Cc");

        assertEquals(3, list.size());

        assertFalse(list.isEmpty());

        assertEquals(list.get(0), "Aa");
        assertEquals(list.get(1), "Bb");
        assertEquals(list.get(2), "Cc");

        assertArrayEquals(list.toArray(), new String[]{"Aa", "Bb", "Cc"});
    }

    @Test(timeout = TIMEOUT)
    public void test03_addToFront() {
        list.addToFront("Aa");  //Aa

        assertNotNull(list.getHead());
        assertNotNull(list.getTail());

        assertEquals(1, list.size());

        assertFalse(list.isEmpty());

        list.addToFront("Bb");  //Bb Aa
        list.addAtIndex(1, "Cc");   //Bb Cc Aa
        list.addAtIndex(0, "Dd");   //Dd Bb Cc Aa

        assertEquals(4, list.size());

        assertEquals(list.get(0), "Dd");
        assertEquals(list.get(1), "Bb");
        assertEquals(list.get(2), "Cc");
        assertEquals(list.get(3), "Aa");

        assertArrayEquals(list.toArray(), new String[]{"Dd", "Bb", "Cc", "Aa"});
    }

    @Test(timeout = TIMEOUT)
    public void test04_addToBack() {
        list.addToBack("Aa");   //Aa

        assertNotNull(list.getHead().getData());
        assertNotNull(list.getTail().getData());

        assertEquals(1, list.size());

        assertFalse(list.isEmpty());

        list.addToBack("Bb");   //Aa Bb
        list.addToBack("Cc");   //Aa Bb Cc
        list.addAtIndex(1, "Dd");   //Aa Dd Bb Cc

        assertArrayEquals(list.toArray(), new String[]{"Aa", "Dd", "Bb", "Cc"});

        list.addToBack("Ee");   //Aa Dd Bb Cc Ee

        assertEquals(5, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test05_removeAtIndex() {
        try {
            list.removeAtIndex(0);
            Assert.fail();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        list.addAtIndex(0, "Aa");   //Aa
        list.addToFront("Bb");  //Bb Aa
        list.addToBack("Cc");   //Bb Aa Cc

        //IndexOutOfBoundsException Check
        for (int i: new int[]{-1, 3, 4}) {
            try {
                list.removeAtIndex(i);
                Assert.fail();
            } catch (Exception e) {
                assertEquals(IndexOutOfBoundsException.class, e.getClass());
            }
        }

        assertArrayEquals(list.toArray(), new String[]{"Bb", "Aa", "Cc"});

        assertEquals(list.removeAtIndex(1), "Aa");
        assertArrayEquals(list.toArray(), new String[]{"Bb", "Cc"});

        assertEquals(2, list.size());
        assertEquals(list.removeAtIndex(1), "Cc");

        assertNotNull(list.getHead());
        assertNotNull(list.getTail());

        assertEquals(list.removeAtIndex(0), "Bb");
        assertArrayEquals(list.toArray(), new String[]{});

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertTrue(list.isEmpty());

        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test06_removeFromFront() {
        assertNull(list.removeFromFront());

        list.addAtIndex(0, "Aa");   //Aa
        list.addToFront("Bb");  //Bb Aa
        list.addToBack("Cc");   //Bb Aa Cc

        assertEquals(list.removeFromFront(), "Bb");
        assertEquals(2, list.size());
        assertEquals(list.getHead().getData(), "Aa");
        assertNull(list.getHead().getPrevious());

        assertEquals(list.removeFromFront(), "Aa");
        assertArrayEquals(list.toArray(), new String[]{"Cc"});

        assertEquals(list.removeFromFront(), "Cc");
        assertArrayEquals(list.toArray(), new String[]{});

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertTrue(list.isEmpty());

        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test07_removeFromBack() {
        assertNull(list.removeFromBack());

        list.addAtIndex(0, "Aa");   //Aa
        list.addToFront("Bb");  //Bb Aa
        list.addToBack("Cc");   //Bb Aa Cc

        assertEquals(list.removeFromBack(), "Cc");  //Bb Aa
        assertEquals(2, list.size());

        assertEquals(list.removeFromFront(), "Bb");     //Aa
        assertArrayEquals(list.toArray(), new String[]{"Aa"});

        assertEquals(list.removeFromBack(), "Aa");
        assertArrayEquals(list.toArray(), new String[]{});

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertTrue(list.isEmpty());

        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test08_removeAllOccurrences() {
        try {
            list.removeAllOccurrences(null);
            Assert.fail();
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        list.addToFront("1");   //1
        list.addToBack("3");    //1 3
        list.addToBack("3");    //1 3 3
        list.addAtIndex(3, "4");    //1 3 3 4
        list.addToBack("1");       //1 3 3 4 1

        assertEquals(5, list.size());

        //remove 3
        assertTrue(list.removeAllOccurrences("3")); //1 4 1
        assertArrayEquals(list.toArray(), new String[]{"1", "4", "1"});
        assertEquals(3, list.size());

        //remove 3
        assertFalse(list.removeAllOccurrences("3")); //1 4 1

        //remove 1
        assertTrue(list.removeAllOccurrences("1")); //4
        assertArrayEquals(list.toArray(), new String[]{"4"});

        //check for head, tail, and isEmpty
        assertNotNull(list.getHead());
        assertNotNull(list.getTail());
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());

        //remove 4
        assertTrue(list.removeAllOccurrences("4"));

        //check to see if empty
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertArrayEquals(list.toArray(), new String[]{});
    }

    @Test(timeout = TIMEOUT)
    public void test09_clear() {
        list.addToFront("Aa");

        assertNotNull(list.getHead());
        assertNotNull(list.getTail());

        assertEquals(1, list.size());

        assertFalse(list.isEmpty());

        assertArrayEquals(list.toArray(), new String[]{"Aa"});

        assertEquals(list.get(0), "Aa");

        list.clear();

        assertNull(list.getHead());
        assertNull(list.getTail());

        assertEquals(0, list.size());

        assertTrue(list.isEmpty());

        assertArrayEquals(list.toArray(), new String[]{});
    }

    @Test(timeout = TIMEOUT)
    public void test10_getException() {
        list.addToFront("1");   //1
        list.addToBack("3");    //1 3

        assertEquals(list.get(0), "1");
        assertEquals(list.get(1), "3");
        for (int i: new int[]{-1, 2, 3}) {
            try {
                list.get(i);
                Assert.fail();
            } catch (Exception e) {
                assertEquals(IndexOutOfBoundsException.class, e.getClass());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test11_testNode() {
        list.addToFront("A");   //A

        assertEquals(list.getHead().getData(), "A");
        assertEquals(list.getTail().getData(), "A");

        list.addToBack("B");    //A B
        list.addAtIndex(0, "C");    //C A B

        assertEquals(list.getHead().getData(), "C");
        assertEquals(list.getHead().getNext().getData(), "A");
        assertEquals(list.getHead().getNext().getNext().getData(), "B");

        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());

        assertEquals(list.getTail().getPrevious().getData(), "A");
        assertEquals(list.getTail().getPrevious().getPrevious().getData(), "C");
    }
}