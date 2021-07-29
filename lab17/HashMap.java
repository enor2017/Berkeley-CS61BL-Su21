import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class HashMap<K, V> implements Map61BL<K, V> {

    private LinkedList<Entry>[] items = new LinkedList[16];
    private int size;
    private double LOAD_FACTOR = 0.75;

    /* Creates a new hash map with a default array of size 16 and a maximum load factor of 0.75. */
    public HashMap() {
        for(int i = 0; i < 16; ++i) {
            items[i] = new LinkedList<>();
        }
        size = 0;
    }

    /* Creates a new hash map with an array of size INITIALCAPACITY and a maximum load factor of 0.75. */
    public HashMap(int initialCapacity) {
        items = new LinkedList[initialCapacity];
        for(int i = 0; i < initialCapacity; ++i) {
            items[i] = new LinkedList<>();
        }
        size = 0;
    }

    /* Creates a new hash map with INITIALCAPACITY and LOADFACTOR. */
    public HashMap(int initialCapacity, double loadFactor) {
        items = new LinkedList[initialCapacity];
        for(int i = 0; i < initialCapacity; ++i) {
            items[i] = new LinkedList<>();
        }
        size = 0;
        LOAD_FACTOR = loadFactor;
    }

    /* Returns the number of items contained in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Returns the length of this HashMap's internal array. */
    public int capacity() {
        return items.length;
    }

    /* Returns an Iterator over the keys in this map. */
    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
        // throw new UnsupportedOperationException();
    }

    /* helper function: get the correct array index for key */
    private int idx(K key) {
        return Math.floorMod(key.hashCode(), items.length);
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        // just get a new Hashmap
        HashMap<K, V> emptyMap = new HashMap<>();
        this.items = emptyMap.items;
        this.size = emptyMap.size;
    }

    /* Returns true if the map contains the KEY. */
    @Override
    public boolean containsKey(K key) {
        LinkedList<Entry> entries = items[idx(key)];
        for(Entry entry : entries) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    @Override
    public V get(K key) {
        LinkedList<Entry> entries = items[idx(key)];
        for(Entry entry : entries) {
            if (entry.key.equals(key)) {
                return (V) entry.value;
            }
        }
        return null;
    }

    /* helper function: resize when factor is higher than LOAD_FACTOR */
    private void resize(int newSize) {
        HashMap newMap = new HashMap(newSize);
        // iterate all entries in current map, add it to new map
        for(int i = 0; i < items.length; ++i) {
            LinkedList<Entry> currentEntries = items[i];
            for(Entry entry : currentEntries) {
                newMap.put(entry.key, entry.value);
            }
        }
        // point at new map
        this.items = newMap.items;
        this.size = newMap.size;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       HashMap, replace the current corresponding value with VALUE. */
    @Override
    public void put(K key, V value) {
        if(!containsKey(key)) {
            // check if overload
            if((size + 1) * 1.0 / (items.length) > LOAD_FACTOR) {
                resize(2 * items.length);
            }
            size++;
            // add (KEY, VALUE) pair to linked list
            items[idx(key)].add(new Entry(key, value));
        } else {
            // replace current value
            LinkedList<Entry> entries = items[idx(key)];
            for(Entry entry : entries) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
            }
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    @Override
    public V remove(K key) {
        if(!containsKey(key)) {
            return null;
        } else {
            V toReturn = null;
            LinkedList<Entry> entries = items[idx(key)];
            for(Entry entry : entries) {
                if (entry.key.equals(key)) {
                    toReturn = (V) entry.value;
                    entries.remove(entry);
                    size--;
                    break;
                }
            }
            return toReturn;
        }
    }

    /* Removes a particular key-value pair (KEY, VALUE) and returns true if
       successful. */
    @Override
    public boolean remove(K key, V value) {
        LinkedList<Entry> entries = items[idx(key)];
        for(Entry entry : entries) {
            if (entry.key.equals(key) && entry.value.equals(value)) {
                entries.remove(entry);
                size--;
                return true;
            }
        }
        return false;
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    private class HashMapIterator implements Iterator<K> {
        private int nextIndex = 1;

        @Override
        public boolean hasNext() {
            return nextIndex <= size;
        }

        @Override
        public K next() {
            if(!hasNext()) {
                return null;
            }
            int count = 0;
            for(int i = 0; i < items.length; ++i) {
                LinkedList<Entry> currentEntries = items[i];
                // if count + currentEntries.size() < nextIndex
                // means the item we're looking for is in the rest LinkedLists
                if(count + currentEntries.size() < nextIndex) {
                    count += currentEntries.size();
                    continue;
                }
                // otherwise, the one we want is in the current LinkedList
                int idxInCurrentLL = nextIndex - count;
                K toReturn = (K) currentEntries.get(idxInCurrentLL - 1).key;
                nextIndex++;
                return toReturn;
            }
            // redundant return: our func must have returned at this point.
            return null;
        }
    }
}