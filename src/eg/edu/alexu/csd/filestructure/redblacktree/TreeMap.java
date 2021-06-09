package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TreeMap<T extends Comparable<T>,V> implements ITreeMap<T,V> {
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
    public Set<Entry<T, V>> entrySet() {
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

//    @Override
//    public void put(Comparable key, Object value) {
//        tree.insert((T)key,(V)value);
//    }

    @Override
    public void put(T key, V value) {
            tree.insert(key,value);
    }

    @Override
    public void putAll(Map<T,V> map) {
        for (Entry<T, V> current : map.entrySet()) {
            tree.insert(current.getKey(), current.getValue());
        }
    }

    @Override
    public boolean remove(T key) {
        return tree.delete(key);
    }

    @Override
    public int size() {
        int size = 0;
        size = values().size();
        return size;
    }

    @Override
    public Collection<V> values() {
        Queue<Node<T,V>> nodeQueue = new LinkedList<>();
        Collection<V> vCollection = new ConcurrentLinkedQueue<>();
        nodeQueue.add((Node<T, V>) tree.root);
        while (!nodeQueue.isEmpty()){
            Node<T,V> parent = nodeQueue.poll();
            vCollection.add(parent.getValue());
            if(!parent.getLeftChild().isNull()){
                nodeQueue.add((Node<T, V>) parent.getLeftChild());
            }
            if(!parent.getRightChild().isNull()){
                nodeQueue.add((Node<T, V>) parent.getRightChild());
            }
        }
        return vCollection;
    }


}
