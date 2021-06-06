public class RedBlackTree<T extends Comparable<T>,V> implements IRedBlackTree<T,V>{
    INode<T,V> root ;
    RedBlackTree(INode<T,V> root){
        this.root = root;
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

    }

    @Override
    public boolean delete(T key) {
        return false;
    }
    public void rotateRight(INode<T,V> root, INode<T,V> nodeToRotate){
        if(nodeToRotate.getLeftChild().isNull()){
            return;
        }
        INode<T,V> leftChild = nodeToRotate.getLeftChild();
        if(!nodeToRotate.getLeftChild().getRightChild().isNull()){
            nodeToRotate.setLeftChild(nodeToRotate.getLeftChild().getRightChild());
            nodeToRotate.getLeftChild().getRightChild().setParent(nodeToRotate);
        }else{
            nodeToRotate.setLeftChild(null);
        }
        if(nodeToRotate.getParent().isNull()){
            leftChild.setParent(null);
            root  = leftChild;
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
    public void rotateLeft(INode<T,V> root , INode<T,V> nodeToRotate){
        if (nodeToRotate.getRightChild().isNull()){
            return;
        }
        INode<T,V> rightChild = nodeToRotate.getRightChild();
        if(!rightChild.getLeftChild().isNull()){
            nodeToRotate.setRightChild(rightChild.getLeftChild());
            rightChild.getLeftChild().setParent(nodeToRotate);
        }else{
            nodeToRotate.setRightChild(null);
        }
        if(nodeToRotate.getParent().isNull()){
            rightChild.setParent(null);
            root = rightChild;
        }else if(nodeToRotate == nodeToRotate.getParent().getLeftChild()){
                nodeToRotate.getParent().setLeftChild(rightChild);
                rightChild.setParent(nodeToRotate.getParent());
        }else {
            nodeToRotate.getParent().setRightChild(rightChild);
            rightChild.setParent(nodeToRotate.getParent());
        }
        rightChild.setRightChild(nodeToRotate);
        nodeToRotate.setParent(rightChild);
    }
}
