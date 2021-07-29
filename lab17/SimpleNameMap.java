import java.util.LinkedList;

public class SimpleNameMap {

    private LinkedList<Entry>[] items = new LinkedList[10];
    private int size;
    private final double LOAD_FACTOR = 0.75;

    public SimpleNameMap() {
        for(int i = 0; i < 10; ++i) {
            items[i] = new LinkedList<>();
        }
        size = 0;
    }

    public SimpleNameMap(int N) {
        items = new LinkedList[N];
        for(int i = 0; i < N; ++i) {
            items[i] = new LinkedList<>();
        }
        size = 0;
    }

//    /* helper function: return the simple hashcode for string */
//    private static int myHash(String word) {
//        if(word == null || word.length() < 1) {
//            return 0;
//        } else {
//            return word.charAt(0) - 'A';
//        }
//    }

    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    /* helper function: get the correct array index for key */
    private int idx(String key) {
        return Math.floorMod(key.hashCode(), items.length);
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
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
    public String get(String key) {
        LinkedList<Entry> entries = items[idx(key)];
        for(Entry entry : entries) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /* helper function: resize when factor is higher than LOAD_FACTOR */
    private void resize(int newSize) {
        SimpleNameMap newMap = new SimpleNameMap(newSize);
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

    /* TEST function: Only for test, return array size of current map */
    public int arraySize() {
        return items.length;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
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
    public String remove(String key) {
        if(!containsKey(key)) {
            return null;
        } else {
            String toReturn = "";
            LinkedList<Entry> entries = items[idx(key)];
            for(Entry entry : entries) {
                if (entry.key.equals(key)) {
                    toReturn = entry.value;
                    entries.remove(entry);
                    size--;
                    break;
                }
            }
            return toReturn;
        }
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
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
}