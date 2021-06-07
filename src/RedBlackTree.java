public class RedBlackTree<T extends Comparable<T>,V> implements IRedBlackTree<T,V>{
    INode<T,V> root ;
    RedBlackTree(INode<T,V> root){
        this.root = root;
        root.setParent(new Node<>());
    }
    RedBlackTree(){
        this.root = new Node<>();
    }
    @Override
    public INode<T, V> getRoot() {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        this.root=null;
    }

    @Override
    public V search(T key) {
        return null;
    }

    @Override
    public boolean contains(T key) {
        return false;
    }

    @Override
    public void insert(T key, V value) {
        Node<T,V> newNode = new Node<>(key,value);
        newNode.setRightChild(new Node<>());
        newNode.setLeftChild(new Node<>());
        this.root= insertAsBST(this.root,newNode);

    }

    @Override
    public boolean delete(T key) {
        return false;
    }
    public void rotateRight( INode<T,V> nodeToRotate){
        if(nodeToRotate.getLeftChild().isNull()){
            return;
        }
        INode<T,V> leftChild = nodeToRotate.getLeftChild();
        if(!nodeToRotate.getLeftChild().getRightChild().isNull()){
            nodeToRotate.setLeftChild(nodeToRotate.getLeftChild().getRightChild());
            nodeToRotate.getLeftChild().getRightChild().setParent(nodeToRotate);
        }else{
            nodeToRotate.setLeftChild(new Node<>());
        }
        if(nodeToRotate.getParent().isNull()){
            leftChild.setParent(new Node<>());
            this.root  = leftChild;
        }else if (nodeToRotate == nodeToRotate.getParent().getLeftChild()){
                nodeToRotate.getParent().setLeftChild(leftChild);
                leftChild.setParent(nodeToRotate.getParent());
        }else {
            nodeToRotate.getParent().setRightChild(leftChild);
            leftChild.setParent(nodeToRotate.getParent());
        }
        leftChild.setRightChild(nodeToRotate);
        nodeToRotate.setParent(leftChild);
    }
    public void rotateLeft( INode<T,V> nodeToRotate){
        if (nodeToRotate.getRightChild().isNull()){
            return;
        }
        INode<T,V> rightChild = nodeToRotate.getRightChild();
        if(!rightChild.getLeftChild().isNull()){
            nodeToRotate.setRightChild(rightChild.getLeftChild());
            rightChild.getLeftChild().setParent(nodeToRotate);
        }else{
            nodeToRotate.setRightChild(new Node<>());
        }
        if(nodeToRotate.getParent().isNull()){
            rightChild.setParent(new Node<>());
            this.root = rightChild;
        }else if(nodeToRotate == nodeToRotate.getParent().getLeftChild()){
                nodeToRotate.getParent().setLeftChild(rightChild);
                rightChild.setParent(nodeToRotate.getParent());
        }else {
            nodeToRotate.getParent().setRightChild(rightChild);
            rightChild.setParent(nodeToRotate.getParent());
        }
        rightChild.setLeftChild(nodeToRotate);
        nodeToRotate.setParent(rightChild);
    }
    public INode<T,V> insertAsBST(INode<T,V>root ,INode<T,V> newNode ){
        if (root.isNull()){
            return newNode;
        }
        if(root.getKey().compareTo(newNode.getKey())>0){
            root.setLeftChild(insertAsBST(root.getLeftChild(),newNode));
            root.getLeftChild().setParent(root);
        }else{
            root.setRightChild(insertAsBST(root.getRightChild(),newNode));
            root.getRightChild().setParent(root);
        }
        return root;
    }
    public void print(INode<T,V>root){
        System.out.println(root.getKey());
        if(!root.getLeftChild().isNull()){
            print(root.getLeftChild());
        }
        if(!root.getRightChild().isNull()){
            print(root.getRightChild());
        }
    }

}
