package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable <K>, V> implements Map61B<K, V> {
    private int size;
    private Node root;

    private class Node {
        private Node left;
        private Node right;
        private K key;
        private V value;

        private Node(K keys, V values) {
            key = keys;
            value = values;
        }
    }

    public BSTMap() {
        clear();
    }

    @Override
    public void clear () {
        root = null;
        size = 0;
    }
    @Override
    public boolean containsKey (K keys){
        return getHelper(root, keys) != null;
    }
    /* Got help in Lab implementing containsKey correctly

    */
    @Override
    public V get (K keys){
        Node newNode = getHelper(root, keys);
        if (newNode == null) {
            return null;
        }
        return getHelper(root, keys).value;
    }

    private Node getHelper(Node currentNode, K keys){
        if (currentNode == null) {
            return null;
        }
        int cmp = keys.compareTo(currentNode.key);
        if(cmp < 0) {
            return getHelper(currentNode.left, keys);
        } else if (cmp > 0) {
            return getHelper(currentNode.right, keys);
        } else {
            return currentNode;
        }

    }

    /* Returns the number of key-value mappings in this map. */

    @Override
    public int size () {
        return size;
    }
        /* Associates the specified value with the specified key in this map. */

    @Override
    public void put (K keys, V values){
        root = putHelper(root, keys, values);
    }

    private Node putHelper (Node currentNode, K keys, V values) {
        if (currentNode == null)  {
            size += 1;
            return new Node(keys, values);
        }
        int cmp = keys.compareTo(currentNode.key);
        if (cmp < 0) {
            currentNode.left = putHelper(currentNode.left,  keys, values);
        } else if (cmp > 0) {
            currentNode.right = putHelper(currentNode.right, keys, values);
        } else {
            currentNode.value = values;
        }
        return currentNode;
    }

    public void printInOrder() {
        Node smallNode = root.left;
        Node bigNode = root.right;
        while (size != 0) {
            System.out.println(smallNode.value);
            size -= 1;
            System.out.println(root.value);
            size -= 1;
            bigNode = root;
            smallNode = root.left;
            bigNode = root.right;
        }
    }

    public V remove (K key, V value){
        throw new UnsupportedOperationException();
    }
    public Set<K> keySet () {
        throw new UnsupportedOperationException();
    }

    public V remove (K key){
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator () {
        throw new UnsupportedOperationException();
    }
}
