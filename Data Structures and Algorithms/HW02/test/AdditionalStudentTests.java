/**
 * ALL THE UNIT TESTS
 * I don't mind lines >80 characters long, as you can tell.
 *
 * @author ahosein3
 * @version 0.1.10
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AdditionalStudentTests {
    private DoublyLinkedList<String> list;
    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testHeadAndTail() {
        assertNull("Head pointer is not null after init.", list.getHead());
        assertNull("Tail pointer is not null after init.", list.getTail());
        list.addToFront("poop");
        assertNotNull("Head pointer is null after adding item to front.", list.getHead());
        assertNotNull("Tail pointer is null after adding item to front.", list.getTail());
        list.removeFromFront();
        assertNull("Head pointer is not null after removing only item (from front).", list.getHead());
        assertNull("Tail pointer is not null after removing only item (from front)..", list.getTail());
        list.addToBack("poop");
        assertNotNull("Head pointer is null after adding item to back.", list.getHead());
        assertNotNull("Tail pointer is null after adding item to back.", list.getTail());
        list.removeFromBack();
        assertNull("Head pointer is not null after removing only item (from back).", list.getHead());
        assertNull("Tail pointer is not null after removing only item (from back).", list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("poop");
        assertEquals("Size did not update correctly after addToFront", 1, list.size());
        assertEquals("Head did not update correctly after addToFront", "poop", list.getHead().getData());
        assertEquals("Tail did not update correctly after addToFront", "poop", list.getTail().getData());

        list.addToFront("lol");
        assertEquals("New first element's next pointer is set correctly after addToFront.", "poop", list.getHead().getNext().getData());
        assertEquals("Previous first element's previous pointer is not updated after addToFront.", "lol", list.getHead().getNext().getPrevious().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("lol");
        assertEquals("Size did not update correctly after addToBack", 1, list.size());
        assertEquals("Head did not update correctly after addToBack", "lol", list.getHead().getData());
        assertEquals("Tail did not update correctly after addToBack", "lol", list.getTail().getData());

        list.addToBack("poop");
        assertEquals("New last element's previous pointer is set correctly after addToBack.", "lol", list.getTail().getPrevious().getData());
        assertEquals("Previous last element's next pointer is not updated after addToBack.", "poop", list.getTail().getPrevious().getNext().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addToFront("poop");
        list.addToBack("lol");
        list.addAtIndex(1, "kappa");
        assertEquals("Size did not update correctly after addAtIndex.", 3, list.size());
        assertEquals("Head's next pointer does not point to the right item.", "kappa", list.getHead().getNext().getData());
        assertEquals("Tails's previous pointer does not point to the right item.", "kappa", list.getTail().getPrevious().getData());
        list.addAtIndex(0, "poop1");
        assertEquals("Head pointer does not point to the right item after adding to index 0.", "poop1", list.getHead().getData());
        list.addAtIndex(4, "poop5");
        assertEquals("Tail pointer does not point to the right item after adding to index {@code size}.", "poop5", list.getTail().getData());
        assertEquals("Size did not update correctly after addAtIndex.", 5, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testClearAndIsEmpty() {
        assertTrue("List is not empty after init.", list.isEmpty());
        list.addToFront("poop");
        assertTrue("List is empty after adding elements.", !(list.isEmpty()));
        list.clear();
        assertTrue("List is not empty after clear.", list.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionAddToFront() {
        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionAddToBack() {
        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionAddAtIndex() {
        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void indexTooSmallAddAtIndex() {
        list.addAtIndex(-1, "poop");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void indexTooLargeAddAtIndex() {
        list.addToFront("poop");
        list.addToFront("poop");
        list.addToFront("poop");
        list.addAtIndex(27, "poop");
    }

    @Test(timeout = TIMEOUT)
    public void testToArray() {
        String[] expectedArray = new String[5];
        for (int i = 0; i < 5; i++) {
            expectedArray[i] = "poop" + i;
            list.addToBack("poop" + i);
        }
        assertArrayEquals("Array is not as expected.", expectedArray, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(i, "poop" + i);
        }

        for (int i = 0; i < 5; i++) {
            assertEquals("Element at index " + i + " was not as expected.", "poop" + i, list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(i, "poop" + i);
        }

        assertEquals("Tail pointer does not point at the right element.", "poop4", list.getTail().getData());
        assertEquals("Size did not update correctly after addAtIndex", 5, list.size());

        list.removeFromBack();

        assertEquals("Tail pointer does not point at the right element after removeFromBack.", "poop3", list.getTail().getData());
        assertEquals("Size did not update correctly after removeFromBack.", 4, list.size());
        assertNull("Next pointer of new last element not updated after removeFromBack.", list.getTail().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(i, "poop" + i);
        }

        assertEquals("Tail pointer does not point at the right element.", "poop0", list.getHead().getData());
        assertEquals("Size did not update correctly after addAtIndex", 5, list.size());

        list.removeFromFront();

        assertEquals("Head pointer does not point at the right element after removeFromFront.", "poop1", list.getHead().getData());
        assertEquals("Size did not update correctly after removeFromFront.", 4, list.size());
        assertNull("Previous pointer of new first element not updated after removeFromFront.", list.getHead().getPrevious());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(i, "poop" + i);
        }

        assertEquals("Size did not update correctly after addAtIndex.", 5, list.size());

        list.removeAtIndex(2);
        assertEquals("Elements not in expected order after removeAtIndex.", "poop3", list.get(2));
        assertEquals("Size did not update correctly after removeAtIndex.", 4, list.size());

        list.removeAtIndex(0);
        assertEquals("Head pointer not updated correctly after removeAtIndex at index 0.", "poop1", list.getHead().getData());
        assertEquals("Size did not update correctly after removeAtIndex.", 3, list.size());

        list.removeAtIndex(2);
        assertEquals("Head pointer not updated correctly after removeAtIndex at index 0.", "poop3", list.getTail().getData());
        assertEquals("Size did not update correctly after removeAtIndex.", 2, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAllOccurrences() {

        assertFalse("removeAllOccurrences returned true when the list is empty.", list.removeAllOccurrences("poop"));

        for (int i = 0; i < 5; i++) {
            list.addAtIndex(i, "poop");
        }

        assertEquals("Size did not update correctly after addAtIndex.", 5, list.size());

        list.addToBack("lol2");
        list.addToFront("lol");

        assertEquals("Size did not update correctly after adding.", 7, list.size());

        assertTrue("removeAllOccurrences returned false when elements should have been removed.", list.removeAllOccurrences("poop"));
        assertFalse("removeAllOccurrences returned true when elements should not have been removed.", list.removeAllOccurrences("kappa"));
        assertEquals("Size did not update correctly after removeAllOccurrences.", 2, list.size());

        assertTrue("removeAllOccurrences returned false when elements should have been removed.", list.removeAllOccurrences("lol"));
        assertTrue("removeAllOccurrences returned false when elements should have been removed.", list.removeAllOccurrences("lol2"));

        assertNull("Head pointer is not null after removeAllOccurrences.", list.getHead());
        assertNull("Tail pointer is not null after removeAllOccurrences.", list.getTail());
        assertEquals("Size did not update correctly after removeAllOccurrences.", 0, list.size());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void indexTooSmallRemoveAtIndex() {
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void indexTooLargeRemoveAtIndex() {
        list.addToFront("poop");
        list.addToFront("poop");
        list.addToFront("poop");
        list.removeAtIndex(27);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void indexIsZeroButListIsEmpty() {
        list.removeAtIndex(0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionRemoveAllOccurrences() {
        list.removeAllOccurrences(null);
    }
}