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
       Node<String ,Integer> nodeA = new Node<>("A",3);
       Node<String ,Integer> nodeB = new Node<>("B",4);
       Node<String ,Integer> nodeC = new Node<>("C",5);
       Node<String ,Integer> nodeD = new Node<>("D",6);
       Node<String ,Integer> nodeE = new Node<>("E",9);
       Node<String ,Integer> nodeF = new Node<>("F",10);
       Node<String ,Integer> nodeG = new Node<>("G",11);
      // nodeA.setParent(new Node<>());
       nodeA.setLeftChild(nodeB);
       nodeA.setRightChild(nodeC);
       nodeB.setParent(nodeA);
       nodeB.setLeftChild(nodeD);
       nodeB.setRightChild(nodeE);
       nodeC.setParent(nodeA);
       nodeC.setLeftChild(nodeF);
       nodeC.setRightChild(nodeG);
       nodeG.setParent(nodeC);
       nodeF.setParent(nodeC);
       nodeE.setParent(nodeB);
       nodeD.setParent(nodeB);
       nodeD.setLeftChild(new Node<>());
       nodeD.setRightChild(new Node<>());
       nodeE.setLeftChild(new Node<>());
       nodeE.setRightChild(new Node<>());
       nodeF.setLeftChild(new Node<>());
       nodeF.setRightChild(new Node<>());
       nodeG.setLeftChild(new Node<>());
       nodeG.setRightChild(new Node<>());
       RedBlackTree<String,Integer> redBlackTree = new RedBlackTree<>(nodeA);
       redBlackTree.rotateRight(redBlackTree.root,nodeB);
       redBlackTree.rotateRight(redBlackTree.root,nodeC);
       redBlackTree.print(redBlackTree.root);
    }
}
