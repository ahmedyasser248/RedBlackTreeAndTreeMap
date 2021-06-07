public class Node<T extends Comparable<T>, V> implements INode<T ,V>  {

    T key  ;
    V value ;
    INode<T,V> parent  ;
    INode<T,V> leftChild ;
    INode<T,V> rightChild ;
    Boolean color = RED;
    Node(){
    }
    Node(T key,V value ){
        this.key = key;
        this.value = value;
    }
    Node(T key,V value,Node<T,V> parent ,Node<T,V> leftChild,Node<T,V> rightChild ){
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    @Override
    public void setParent(INode<T, V> parent) {
        this.parent=parent;
    }

    @Override
    public INode<T, V> getParent() {
      return this.parent;
    }

    @Override
    public void setLeftChild(INode<T, V> leftChild) {
        this.leftChild = leftChild;
    }

    @Override
    public INode<T, V> getLeftChild() {
        return this.leftChild;
    }

    @Override
    public void setRightChild(INode<T, V> rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public INode<T, V> getRightChild() {
        return this.rightChild;
    }

    @Override
    public T getKey() {
        return this.key;
    }

    @Override
    public void setKey(T key) {
         this.key = key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean getColor() {
        return this.color;//return true which is red by default
    }

    @Override
    public void setColor(boolean color) {
        this.color = color;
    }

    @Override
    public boolean isNull() {
        return key==null;
    }
    public static void main (String []args){
        RedBlackTree<Integer,Integer> obj = new RedBlackTree<>();
        obj.insert(30,1500);
        obj.insert(20,1500);
        obj.insert(40,1500);
        obj.insert(35,1500);
        obj.insert(50,1500);
        obj.rotateLeft(obj.getRoot());
        obj.print(obj.root);
    }
}
