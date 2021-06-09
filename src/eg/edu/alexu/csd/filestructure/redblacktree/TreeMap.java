package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.*;

public class TreeMap<T extends Comparable<T>,V> {
    RedBlackTree<T,V> tree=new RedBlackTree<>();
    @Override
    public Map.Entry ceilingEntry(Comparable key) {
        return null;
    }

    @Override
    public Comparable ceilingKey(Comparable key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(Comparable key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Set<Map.Entry> entrySet() {
        return null;
    }

    @Override
    public Map.Entry<T,V> firstEntry() {
        if(tree.isEmpty()){
            return null;
        }
        T key=firstKey();
        Map.Entry<T,V> t=new AbstractMap.SimpleEntry<>(key,tree.search(key));
        return t;
    }

    @Override
    public T firstKey() {
        if(tree.isEmpty()){
            return null;
        }
        INode<T,V> n=tree.root;
        while(n.getLeftChild().getKey()!=null){
            n=n.getLeftChild();
        }
        return n.getKey();
    }

    @Override
    public Map.Entry floorEntry(Comparable key) {
        return null;
    }

    @Override
    public Comparable floorKey(Comparable key) {
        return null;
    }

    @Override
    public Object get(Comparable key) {
        return null;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey) {
        return null;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey, boolean inclusive) {
        return null;
    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Map.Entry<T,V> lastEntry() {
        if(tree.isEmpty()){
            return null;
        }
        T key=lastKey();
        Map.Entry<T,V> entry=new AbstractMap.SimpleEntry<T,V>(key,tree.search(key));
        return entry;

    }

    @Override
    public T lastKey() {
        if(tree.isEmpty()){
            return null;
        }
        INode<T,V> n=tree.root;
        while(n.getRightChild().getKey()!=null){
            n=n.getRightChild();
        }
        return n.getKey();
    }
    @Override
    public Map.Entry<T,V> pollFirstEntry() {
        Map.Entry<T,V> t=firstEntry();
        tree.delete(t.getKey());
        return t;
    }

    @Override
    public Map.Entry<T,V> pollLastEntry() {
        Map.Entry<T,V> t=lastEntry();
        tree.delete(t.getKey());
        return t;
    }

    @Override
    public void put(T key, V value) {
            tree.insert(key,value);
    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public boolean remove(Comparable key) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Collection values() {
        return null;
    }
}
