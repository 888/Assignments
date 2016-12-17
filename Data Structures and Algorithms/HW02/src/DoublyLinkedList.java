/**
 * Your implementation of a DoublyLinkedList
 *
 * @author YOUR NAME HERE
 * @version 1.0
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Node's data cannot be null");
        }
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(
                    "Index does not exist or is not adjacent to existing data");
        }
        if (this.size == 0) {
            LinkedListNode<T> insert = new LinkedListNode<>(data, null, null);
            this.head = insert;
            this.tail = insert;
        } else if (index == 0 && this.size == 1) {
            LinkedListNode<T> insert =
                    new LinkedListNode<>(data, null, this.head);
            this.head = insert;
            LinkedListNode<T> successor = this.tail;
            successor.setPrevious(insert);
            insert.setNext(successor);
        } else if (index == 1 && this.size == 1) {
            LinkedListNode<T> insert =
                    new LinkedListNode<>(data, this.head, null);
            this.tail = insert;
            LinkedListNode<T> predecessor = this.head;
            predecessor.setNext(insert);
            insert.setPrevious(predecessor);
        } else if (index == 0 && this.size > 1) {
            LinkedListNode<T> insert = new LinkedListNode<>(
                    data, null, this.head);
            LinkedListNode<T> successor = this.head;
            successor.setPrevious(insert);
            this.head = insert;
        } else if (index == this.size && this.size > 1) {
            LinkedListNode<T> insert = new LinkedListNode<>(
                    data, this.tail, null);
            LinkedListNode<T> predecessor = this.tail;
            predecessor.setNext(insert);
            this.tail = insert;
        } else {
            int i = 0;
            LinkedListNode<T> point = this.head;
            while (i < index) {
                point = point.getNext();
                i++;
            }
            LinkedListNode<T> insert = new LinkedListNode<>(
                    data, point.getPrevious(), point);
            LinkedListNode<T> successor = insert.getNext();
            successor.setPrevious(insert);
            LinkedListNode<T> predecessor = insert.getPrevious();
            predecessor.setNext(insert);
        }
        this.size++;
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(this.size, data);
    }

    @Override
    public T removeAtIndex(int index) {
        LinkedListNode<T> point;
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                    "Index does not exist or is not adjacent to existing data");
        }
        if (this.size == 1) {
            point = this.head;
            this.head = null;
            this.tail = null;
        } else if (index == 0 && this.size > 1) {
            point = this.head;
            this.head = this.head.getNext();
            this.head.setPrevious(null);
        } else if (index == this.size - 1 && this.size > 1) {
            point = this.tail;
            this.tail = this.tail.getPrevious();
            this.tail.setNext(null);
        } else {
            int i = 0;
            point = this.head;
            while (i < index) {
                point = point.getNext();
                i++;
            }
            LinkedListNode<T> successor = point.getNext();
            LinkedListNode<T> predecessor = point.getPrevious();
            successor.setPrevious(predecessor);
            predecessor.setNext(successor);
        }
        this.size--;
        return point.getData();
    }

    @Override
    public T removeFromFront() {
        if (this.size == 0) {
            return null;
        }
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        if (this.size == 0) {
            return null;
        }
        return removeAtIndex(this.size - 1);
    }

    @Override
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Node's data cannot be null");
        }
        if (this.size == 0) {
            return false;
        }
        int i = this.size;
        int startSize = this.size;
        LinkedListNode<T> point = this.tail;
        while (point != null) {
            if (point.getData().equals(data)) {
                if (this.size == 1) {
                    point = this.head;
                    this.head = null;
                    this.tail = null;
                } else if (point.getPrevious() == null && this.size > 1) {
                    point = this.head;
                    this.head = this.head.getNext();
                    this.head.setPrevious(null);
                } else if (point.getNext() == null && this.size > 1) {
                    point = this.tail;
                    this.tail = this.tail.getPrevious();
                    this.tail.setNext(null);
                } else {
                    LinkedListNode<T> successor = point.getNext();
                    LinkedListNode<T> predecessor = point.getPrevious();
                    successor.setPrevious(predecessor);
                    predecessor.setNext(successor);
                }
                this.size--;
            }
            point = point.getPrevious();
            i--;
        }
        return startSize != this.size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                    "Index does not exist or is not adjacent to existing data");
        }
        if (index == 0) {
            return this.head.getData();
        } else if (index == this.size - 1) {
            return this.tail.getData();
        } else {
            int i = 0;
            LinkedListNode<T> point = this.head;
            while (i < index) {
                point = point.getNext();
                i++;
            }
            return point.getData();
        }
    }

    @Override
    public Object[] toArray() {
        Object[] asArray = new Object[this.size];
        LinkedListNode<T> point = this.head;
        for (int i = 0; i < this.size; i++) {
            asArray[i] = point.getData();
            point = point.getNext();
        }
        return asArray;
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
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
