/**
 * @author mclayton34
 * @version 0.1
 * @param <E> the type of Object in this list
 */
public interface SimpleList<E> {
    /**
     * @return number of elements in this list
     */
    int size();

    /**
     * @return true if the list contains no elements
     */
    boolean isEmpty();

    /**
     * Appends the specified element at the end of the list.
     *
     * @param e element to be inserted
     * @return true if the list has been modified, false otherwise
     */
    boolean add(E e);

    /**
     * Removes all of the elements from this list
     * The list will be empty after this call returns.
     */
    void clear();

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index of the element to return
     * @return element at the specified position in the list
     */
    E get(int index);
}
