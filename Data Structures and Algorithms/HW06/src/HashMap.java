import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Alan Chiang
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException(
                    "Cannot add null keys or values to hashmap");
        }
        double loadSize = (double) size + 1;
        if ((loadSize / table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int index = Math.abs(key.hashCode()) % table.length;
        if (table[index] != null) {
            MapEntry<K, V> current = table[index];
            while (current != null) {
                if (key.equals(current.getKey())) {
                    V output = current.getValue();
                    current.setValue(value);
                    return output;
                } else {
                    current = current.getNext();
                }
            }
        }
        MapEntry<K, V> input = new MapEntry<>(key, value, table[index]);
        table[index] = input;
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Hashmap does not contain null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        if (table[index] != null) {
            if (key.equals(table[index].getKey())) {
                V output = table[index].getValue();
                table[index] = table[index].getNext();
                size--;
                return output;
            }
            MapEntry<K, V> current = table[index];
            while (current.getNext() != null) {
                if (key.equals(current.getNext().getKey())) {
                    V output = current.getNext().getValue();
                    current.setNext(current.getNext().getNext());
                    size--;
                    return output;
                } else {
                    current = current.getNext();
                }
            }
            throw new NoSuchElementException("Key not found in hashmap");
        }
        throw new NoSuchElementException("Key not found in hashmap");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Hashmap does not contain null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        if (table[index] != null) {
            MapEntry<K, V> current = table[index];
            while (current != null) {
                if (key.equals(current.getKey())) {
                    return current.getValue();
                } else {
                    current = current.getNext();
                }
            }
            throw new NoSuchElementException("Key not found in hashmap");
        }
        throw new NoSuchElementException("Key not found in hashmap");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Hashmap does not contain null");
        }
        int index = Math.abs(key.hashCode()) % table.length;
        if (table[index] != null) {
            MapEntry<K, V> current = table[index];
            while (current != null) {
                if (key.equals(current.getKey())) {
                    return true;
                } else {
                    current = current.getNext();
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                keys.add(table[i].getKey());
                if (table[i].getNext() != null) {
                    MapEntry<K, V> current = table[i];
                    while (current.getNext() != null) {
                        keys.add(current.getNext().getKey());
                        current = current.getNext();
                    }
                }
            }
        }
        return keys;
    }

    @Override
    public List<V> values() {
        List<V> vals = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                vals.add(table[i].getValue());
                if (table[i].getNext() != null) {
                    MapEntry<K, V> current = table[i];
                    while (current.getNext() != null) {
                        vals.add(current.getNext().getValue());
                        current = current.getNext();
                    }
                }
            }
        }
        return vals;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException(
                    "Length is too small to properly resize");
        }
        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                K key = table[i].getKey();
                int index = Math.abs(key.hashCode()) % temp.length;
                if (temp[index] == null) {
                    temp[index] = new MapEntry<>(
                            table[i].getKey(), table[i].getValue());
                } else {
                    MapEntry<K, V> fix = new MapEntry<>(table[i].getKey(),
                            table[i].getValue(), temp[index]);
                    temp[index] = fix;
                }
                if (table[i].getNext() != null) {
                    MapEntry<K, V> chain = table[i].getNext();
                    while (chain != null) {
                        K chainKey = chain.getKey();
                        int chainIndex =
                                Math.abs(chainKey.hashCode()) % temp.length;
                        if (temp[chainIndex] == null) {
                            temp[chainIndex] = new MapEntry<>(
                                    chain.getKey(), chain.getValue());
                        } else {
                            MapEntry<K, V> fix = new MapEntry<>(chain.getKey(),
                                    chain.getValue(), temp[chainIndex]);
                            temp[chainIndex] = fix;
                        }
                        chain = chain.getNext();
                    }
                }
            }
        }
        table = temp;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
