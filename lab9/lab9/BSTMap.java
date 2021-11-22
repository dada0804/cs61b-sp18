package lab9;

import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;



        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private Set<K> keyset = new HashSet<>();

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null){
            return null;
        }
        if (key.compareTo(p.key) < 0){
            return getHelper(key, p.left);
        }
        else if (key.compareTo(p.key) > 0){
            return getHelper(key, p.right);
        }
        else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null){
            return new Node(key, value);
        }
        if (key.compareTo(p.key) < 0 ){
            p.left = putHelper(key, value, p.left);
        }
        else if (key.compareTo(p.key) > 0){
            p.right = putHelper(key, value, p.left);
        }
        else{
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        // 一直忘记写下面这个，导致root一直是空的，到不了p.left这一步
        if (root == null){
            root = new Node (key, value);
        }
        putHelper(key, value, root);
        size += 1;
        keyset.add(key);
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

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal
     */
    @Override
    public V remove(K key){
        // 有必要管size吗？
//        if (size == 0){
//            return null;
//        }
        if (get(key) == null ) {
            return null;}
        //要注意root的情况
        else if (root.key == key ){
            V value = root.value;
            root = root.left;
            size -= 1;
            keyset.remove(key);
            return value;
        }else{
            Node deleted = removeHelper(key, root);
            size -= 1;
            keyset.remove(key);
            return deleted.value;}
        }


    private Node removeHelper(K key, Node p) {
        if (p.key == key){
            return p.left;
        }
        if (key.compareTo(p.key) < 0){
            p.left = removeHelper(key, p.left);
        }
        else{
            p.right = removeHelper(key, p.right);
        }
        return p;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value){
            return remove(key);
        }
        else{
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.remove("hello");
        System.out.println(bstmap.size);
        System.out.println(bstmap.get("cat"));
        bstmap.put("zebra", 90);
        bstmap.remove("cat");
        bstmap.remove("fish");
        bstmap.remove("zebra");
        bstmap.remove("dog");
    }
}
