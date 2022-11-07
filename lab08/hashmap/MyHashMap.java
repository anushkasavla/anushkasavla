package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }
    private int size;
    private Collection<Node>[] buckets;
    private double loadFactor;

    public static final int DEFAULT_INT_SIZE = 16;
    public static final double DEFAULT_LOAD_FACTOR = 0.75;


    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        size = 0;
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private int findBucket(K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private int findBucket(K key) {
        return findBucket(key, buckets.length);
    }

    // TODO: Implement the methods of the Map61B Interface below
    @Override
    public void clear() {
        size = 0;
        buckets = createTable(DEFAULT_INT_SIZE);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    private Node getNode(K key) {
        int ind = findBucket(key);
        Collection<Node> bucketList = buckets[ind];
            if (!(bucketList == null)) {
                for (Node i : bucketList) {
                    if (i.key.equals(key)) {
                        return i;
                    }
                }
            }
            return null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        Node n = getNode(key);
        if (n != null) {
            n.value = value;
        } else {
            int bucketIndex = findBucket(key);
            Collection<Node> c = buckets[bucketIndex];
            if (c == null) {
                buckets[bucketIndex] = createBucket();
            }
            buckets[bucketIndex].add(createNode(key,value));
            size += 1;
            if ((double)size/buckets.length >= loadFactor) {
                rebucket(buckets.length * 2);
            }
        }
    }

    private void rebucket (int targetSize) {
        Collection<Node>[] newBuckets = createTable(targetSize);
        for (Collection<Node> c: this.buckets) {
            if (c == null) {
                continue;
            }
            for (Node n: c) {
                K key = n.key;
                int bucketIndex = findBucket(key, newBuckets.length);
                if (newBuckets[bucketIndex] == null) {
                    newBuckets[bucketIndex] = createBucket();
                }
                newBuckets[bucketIndex].add(n);
            }
        }
        buckets = newBuckets;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
