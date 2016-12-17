/**
 * Your implementation of an ArrayList.
 *
 * @author YOUR NAME HERE
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[ArrayListInterface.INITIAL_CAPACITY];

    }

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into data structure.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index does not exist or is not adjacent to existing data");
        }
        if (this.size >= this.backingArray.length) {
            T[] newArray = (T[]) new Object[this.size * 2];
            for (int i = 0; i < this.size; i++) {
                newArray[i] = this.backingArray[i];
            }
            this.backingArray = newArray;
        }
        if (index == this.size) {
            this.backingArray[index] = data;
            this.size++;
        } else {
            for (int i = this.size - 1; i >= index; i--) {
                this.backingArray[i + 1] = this.backingArray[i];
            }
            this.backingArray[index] = data;
            this.size++;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into data structure.");
        }
        if (this.size >= this.backingArray.length) {
            T[] newArray = (T[]) new Object[this.size * 2];
            for (int i = 0; i < this.size; i++) {
                newArray[i] = this.backingArray[i];
            }
            this.backingArray = newArray;
        }
        for (int i = this.size - 1; i >= 0; i--) {
            this.backingArray[i + 1] = this.backingArray[i];
        }
        this.backingArray[0] = data;
        this.size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into data structure.");
        }
        if (this.size >= this.backingArray.length) {
            T[] newArray = (T[]) new Object[this.size * 2];
            for (int i = 0; i < this.size; i++) {
                newArray[i] = this.backingArray[i];
            }
            this.backingArray = newArray;
        }
        this.backingArray[this.size] = data;
        this.size++;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index does not exist or is not adjacent to existing data");
        } else {
            T element = this.backingArray[index];
            for (int i = index; i < this.size - 1; i++) {
                this.backingArray[i] = this.backingArray[i + 1];
            }
            this.backingArray[this.size - 1] = null;
            this.size--;
            return element;
        }

    }

    @Override
    public T removeFromFront() {
        if (this.size == 0) {
            return null;
        }
        T element = this.backingArray[0];
        for (int i = 0; i < this.size - 1; i++) {
            this.backingArray[i] = this.backingArray[i + 1];
        }
        this.backingArray[this.size - 1] = null;
        this.size--;
        return element;
    }

    @Override
    public T removeFromBack() {
        if (this.size == 0) {
            return null;
        }
        T element = this.backingArray[this.size - 1];
        this.backingArray[this.size - 1] = null;
        this.size--;
        return element;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index does not exist or is not adjacent to existing data");
        }
        return this.backingArray[index];
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.backingArray = (T[]) new
                Object[ArrayListInterface.INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}
