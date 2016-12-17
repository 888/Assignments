import java.util.NoSuchElementException;

/**
 * Your implementation of a linked stack.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        T output = head.getData();
        head = head.getNext();
        size--;
        return output;
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null");
        }
        head = new LinkedNode<>(data, head);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this stack.
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
}
