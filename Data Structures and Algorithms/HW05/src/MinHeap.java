import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 *
 * @author Alan Chiang
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial size of {@code STARTING_SIZE} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    /**
     * Private helper method that recurses through backing array
     * and restores order property
     * @param item the data at the index
     * @param index the index to begin at
     */
    private void addHeapify(T item, int index) {
        if (index != 1 && item.compareTo(backingArray[index / 2]) < 0) {
            T temp = backingArray[index / 2];
            backingArray[index / 2] = item;
            backingArray[index] = temp;
            addHeapify(item, index / 2);
        }
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null items to heap");
        }
        if (size >= backingArray.length - 1) {
            T[] tempArray = (T[]) new Comparable[(backingArray.length * 3) / 2];
            for (int i = 1; i < backingArray.length; i++) {
                tempArray[i] = backingArray[i];
            }
            tempArray[size + 1] = item;
            backingArray = tempArray;
            size++;
            addHeapify(item, size);
        } else {
            backingArray[size + 1] = item;
            size++;
            addHeapify(item, size);
        }
    }

    /**
     * Private helper method that iterates through backing array
     * and restores order property
     */
    private void removeHeapify() {
        int i = 1;
        int stop = 0;
        while (i < backingArray.length
                && (backingArray[i] != null || stop != 1)) {
            if (i * 2 + 1 < backingArray.length) {
                if (backingArray[i * 2] != null
                        && backingArray[i * 2 + 1] != null) {
                    T parent = backingArray[i];
                    T leftChild = backingArray[i * 2];
                    T rightChild = backingArray[i * 2 + 1];
                    if (leftChild.compareTo(parent) < 0
                            && rightChild.compareTo(parent) >= 0) {
                        backingArray[i] = leftChild;
                        backingArray[i * 2] = parent;
                    } else if (rightChild.compareTo(parent) < 0
                            && leftChild.compareTo(parent) >= 0) {
                        backingArray[i] = rightChild;
                        backingArray[i * 2 + 1] = parent;
                    } else if (leftChild.compareTo(parent) < 0
                            && rightChild.compareTo(parent) < 0) {
                        if (leftChild.compareTo(rightChild) < 0) {
                            backingArray[i] = leftChild;
                            backingArray[i * 2] = parent;
                        } else {
                            backingArray[i] = rightChild;
                            backingArray[i * 2 + 1] = parent;
                        }
                    } else {
                        stop++;
                    }
                } else if (backingArray[i * 2] != null) {
                    T parent = backingArray[i];
                    T leftChild = backingArray[i * 2];
                    if (leftChild.compareTo(parent) < 0) {
                        backingArray[i] = leftChild;
                        backingArray[i * 2] = parent;
                    } else {
                        stop++;
                    }
                } else {
                    stop++;
                }
            } else {
                stop++;
            }
            i++;
        }
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        T output = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        removeHeapify();
        return output;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        T[] newArray = (T[]) new Comparable[STARTING_SIZE];
        backingArray = newArray;
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

}
