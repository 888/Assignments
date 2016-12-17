import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed queue.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you <b>must not</b>
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (front >= backingArray.length) {
            front = 0;
        }
        T output = backingArray[front];
        backingArray[front] = null;
        front++;
        size--;
        return output;
    }

    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to 1.5 times the current backing array length, rounding down
     * if necessary. If a regrow is necessary, you should copy elements to the
     * front of the new array and reset front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null");
        }
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[(int) (backingArray.length * 1.5)];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[(front + i) % backingArray.length];
                System.out.println(temp[i]);
            }
            backingArray = temp;
            back = size;
            backingArray[back] = data;
            front = 0;
            size++;
        } else if (back < (backingArray.length)) {
            backingArray[back] = data;
            back++;
            size++;
        } else if (back >= (backingArray.length)) {
            backingArray[0] = data;
            back = 1;
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
     * Returns the backing array of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY!
        return backingArray;
    }
}
