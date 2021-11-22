package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;
    private Set<K> keyset = new HashSet<>();

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key, int numBuckets) {
        if (key == null) {
            return 0;
        }

//        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key, buckets.length);
        return buckets[index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF){
            ArrayMap[] bucket = new ArrayMap[buckets.length*2];
            //搞半天忘记这步 一定要好好看code
            for (int i = 0; i < bucket.length; i += 1) {
                bucket[i] = new ArrayMap<>();
            }
            for (K Key : keyset){
                V Value = this.get(Key);
                int index = hash(Key, bucket.length);
                bucket[index].put(Key, Value);
            }
            buckets = bucket;
        }

        int index = hash(key, buckets.length);
        // 注意是否可能这个值已经存在，以及对size的影响
        if (! buckets[index].containsKey(key)){
            size += 1;
            keyset.add(key);
        }
        buckets[index].put(key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keyset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab.If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if(size == 0){
            return null;
        }
        if (get(key) != null){
        int index = hash(key, buckets.length);
        buckets[index].remove(key);
        if (!buckets[index].containsKey(key)){
            size -= 1;
            keyset.remove(key);
        }
        return buckets[index].get(key);}
        else{
            return null;
        }

    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            remove(key);
        }
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
