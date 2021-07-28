public class SimpleNameMap {

    private Entry[] items;
    private int size;

    public SimpleNameMap() {
        items = new Entry[26];
        size = 0;
    }

    /* helper function: return the simple hashcode for string */
    private static int myHash(String word) {
        if(word == null || word.length() < 1) {
            return 0;
        } else {
            return word.charAt(0) - 'A';
        }
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        return items[myHash(key)] != null;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        if(!containsKey(key)) {
            return null;
        } else {
            return items[myHash(key)].value;
        }
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        if(!containsKey(key)) {
            size++;
        }
        items[myHash(key)] = new Entry(key, value);
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        if(!containsKey(key)) {
            return null;
        } else {
            String value = items[myHash(key)].value;
            items[myHash(key)] = null;
            size--;
            return value;
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