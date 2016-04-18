import java.util.Iterator;
import java.util.Set;
import java.util.Collection;

/**
 * MySet, which is-a Set
 * @author achiang31
 * @param <T> The type of objects contained in this Set
 */
@SuppressWarnings("unchecked")
public class MySet<T> implements Set<T> {

    // The following statements have been provided to you.
    // Do not modify these statements.
    private T[] backingArray;
    private int numElements;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
    *MySet's constructor
    */
    public MySet() {
        this.backingArray = (T[]) new Object[DEFAULT_INITIAL_CAPACITY];
        this.numElements = 0;
    }

    // The methods below have been provided to you. You should not modify them.

    /**
     * Returns an iterator over the elements in this set.
     * You will need to know Iterable/Iterator for your exams, so take a look at
     * this implementation and the Javadocs
     * @return an iterator instance
     */
    @Override
    public Iterator<T> iterator() {
        return new MySetIterator();
    }
    private class MySetIterator implements Iterator<T> {
        private int index = 0;

        public boolean hasNext() {
            return index < numElements;
        }
        public T next() {
            return backingArray[index++];
        }
        public void remove() {
            MySet.this.remove(backingArray[index - 1]);
            index--;
        }
    }
    /**
    *appends the specified element to the list, if it is not already there
    *@param e   an object of type T
    *@return true if e if not already in list
    *@throws UnsupportedOperationException if the set doesn't support adding
    *@throws ClassCastException if the specified element's class prevents add()
    *@throws NullPointerException if the specified element is null
    *@throws IllegalArgumentException if adding the specified element is illegal
    */
    @Override
    public boolean add(T e) throws UnsupportedOperationException,
        ClassCastException, NullPointerException, IllegalArgumentException {
        if (e == null) {
            throw new NullPointerException("Cannot add null to Set");
        }
        if (this.contains(e)) {
            return false;
        }
        if (this.backingArray[this.numElements] == null) {
            this.backingArray[this.numElements] = e;
            this.numElements++;
            return true;
        }
        T[] tempArray = (T[]) new Object[this.backingArray.length * 2];
        tempArray[this.backingArray.length] = e;
        this.backingArray = tempArray;
        this.numElements++;
        return true;
    }

    /**
    *adds all of the non-duplicate elements in the given collection to this set
    *@param c   a collection containing elements
    *@return true if any elements are added
    *@throws UnsupportedOperationException if the set doesn't support addAll()
    *@throws ClassCastException if the specified element's class prevents adding
    *@throws NullPointerException if the specified element is null
    *@throws IllegalArgumentException if adding the specified element is illegal
    */
    public boolean addAll(Collection<? extends T> c) throws
        UnsupportedOperationException, ClassCastException,
        NullPointerException, IllegalArgumentException {
        if (c == null) {
            throw new NullPointerException("Cannot add null to Set");
        }
        int check0 = 0;
        for (Object element : c.toArray()) {
            if (!(this.contains(element)) && element != null) {
                this.add((T) element);
                check0++;
            }
        }
        if (check0 != 0) {
            return true;
        }
        return false;
    }

    /**
    *deletes all elements in the set, leaving an empty set
    *@throws UnsupportedOperationException if the set doesn't support clear()
    */
    public void clear() throws UnsupportedOperationException {
        for (int i = 0; i < this.numElements; i++) {
            this.backingArray[i] = null;
        }
        this.numElements = 0;
    }

    /**
    *checks if the specified element is in the set
    *@param o   the Object that is checked
    *@return true if the Object is in the set
    *@throws ClassCastException if the specified Object's class is incompatible
    *@throws NullPointerException if the specified element is null
    */
    public boolean contains(Object o) throws ClassCastException,
        NullPointerException {
        for (int i = 0; i < this.numElements; i++) {
            if (o.equals(this.backingArray[i]) && o != null) {
                return true;
            }
        }
        return false;
    }

    /**
    *checks if the set contains all elements of the specified collection
    *@param c   the collection that is checked
    *@return true if all elements of the specified collection are in the set
    *@throws ClassCastException if any elements are incompatible
    *@throws NullPointerException if the specified collection has null values
    */
    public boolean containsAll(Collection<?> c) throws ClassCastException,
        NullPointerException {
        for (Object element : c.toArray()) {
            if (!(this.contains(element))) {
                return false;
            }
        }
        return true;
    }

    /**
    *checks if the specified Object is equal to this set
    *@param o   the Object that is checked
    *@return true if the Object is a same-sized set with all elements equal
    */
    @Override
    public boolean equals(Object o) {
        if (null == o && this != null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof MySet)) {
            return false;
        }
        MySet<T> that = (MySet<T>) o;
        if (!(this.size() == that.size())) {
            return false;
        }
        for (Object element : that.toArray()) {
            if (!(this.contains(element))) {
                return false;
            }
        }
        return true;
    }

    /**
    *checks if the set is empty
    *@return true if the set has no elements
    */
    public boolean isEmpty() {
        for (int i = 0; i < this.backingArray.length; i++) {
            if (!(this.backingArray[i] == null)) {
                return false;
            }
        }
        return true;
    }

    /**
    *removes the specified element from the set
    *@param o   the Object to be removed
    *@return true if the specifed Object is in the set
    *@throws ClassCastException if the object type is incompatible with the set
    *@throws NullPointerException if the object is null
    *@throws UnsupportedOperationException if this set doesn't support remove()
    */
    public boolean remove(Object o) throws ClassCastException,
        NullPointerException, UnsupportedOperationException {
        for (int i = 0; i < this.numElements; i++) {
            if (this.backingArray[i].equals(o)) {
                this.backingArray[i] = this.backingArray[numElements - 1];
                this.backingArray[numElements - 1] = null;
                this.numElements--;
                return true;
            }
        }
        return false;
    }

    /**
    *removes all elements in specified collection from the set
    *@param c   the specified collection
    *@return true if the set is changed after the method is executed
    *@throws UnsupportedOperationException if set doesn't support removeAll()
    *@throws ClassCastException if a set element is of incompatible type
    *@throws NullPointerException if the set, collection or elements are null
    */
    public boolean removeAll(Collection<?> c) throws
        UnsupportedOperationException, ClassCastException, NullPointerException
    {
        int check1 = 0;
        for (Object element : c) {
            this.remove(element);
            check1++;
        }
        if (check1 != 0) {
            return true;
        }
        return false;
    }

    /**
    *keeps only the set elements also contained in the specified collection
    *@param c   the specified collection
    *@return true if the set is altered after the method executes
    *@throws UnsupportedOperationException if retainAll() isn't supported
    *@throws ClassCastException if a set element is of incompatible type
    *@throws NullPointerException if the set, collection or elements are null
    */
    public boolean retainAll(Collection<?> c) throws
        UnsupportedOperationException, ClassCastException, NullPointerException
    {
        int check2 = 0;
        for (Object element : this) {
            if (!(c.contains(element))) {
                this.remove(element);
                check2++;
            }
        }
        if (check2 != 0) {
            return true;
        }
        return false;
    }

    /**
    *gives the number of elements in the set
    *@return the cardinality of the set as an integer
    */
    public int size() {
        return this.numElements;
    }

    /**
    *places all the elements of the set into an array
    *@return an array containing all the set's elements
    */
    public Object[] toArray() {
        T[] copyArray = (T[]) new Object[this.numElements];
        for (int i = 0; i < this.numElements; i++) {
            copyArray[i] = this.backingArray[i];
        }
        return copyArray;
    }

    /**
     * Returns a hash representation of the object, based on the hashCode of the
     * backing array
     * @return an integer hash of the set
     */
    public int hashCode() {
        return java.util.Arrays.hashCode(backingArray);
    }

    /**
     * Not implemented in this Set
     * @param a an array which specifies the runtime return type for toArray
     * @param <S> the return type of the array
     * @return null
     */
    @Override
    public <S> S[] toArray(S[] a) {
        return null;
    }


    /**
     * Returns the backing array directly.
     * This violates the set's encapsulation principle, but is necessary for
     * grading.
     * @return the backing array
     */
    public T[] getBackingArray() {
        return this.backingArray;
    }
}
