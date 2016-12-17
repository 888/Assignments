import java.util.NoSuchElementException;

/**
 * Your implementation of a linked queue.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (size == 1) {
            T output = head.getData();
            head = head.getNext();
            size--;
            tail = null;
            return output;
        } else {
            T output = head.getData();
            head = head.getNext();
            size--;
            return output;
        }
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null");
        }
        if (size == 0) {
            LinkedNode<T> input = new LinkedNode<>(data, null);
            head = input;
            tail = input;
            size++;
        } else {
            LinkedNode<T> input = new LinkedNode<>(data, null);
            tail.setNext(input);
            tail = input;
            size++;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
