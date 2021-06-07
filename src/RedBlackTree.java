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
        return root.isNull();
    }

    @Override
    public void clear() {
        this.root=new Node<>();
    }


    private INode<T,V> searchNode(T key){
        INode<T,V> n=this.root;
        while(n.getKey()!=null){//Search for the node to be deleted
            if(key.compareTo(n.getKey())==0){//The node to be deleted was found
                return n;
            }
            else if(key.compareTo(n.getKey())<0){
                n=n.getLeftChild();
            }
            else if(key.compareTo(n.getKey())>0){
                n=n.getRightChild();
            }
        }
        return n;
    }

    @Override
    public V search(T key) {
        INode<T,V> result=searchNode(key);
        return result.getValue();
    }

    @Override
    public boolean contains(T key) {
        INode<T,V> result=searchNode(key);
        if(result.getKey()==null){
            return false;
        }
        return true;
    }

    @Override
    public void insert(T key, V value) {
        Node<T,V> newNode = new Node<>(key,value);
        newNode.setRightChild(new Node<>());
        newNode.setLeftChild(new Node<>());
        newNode.setParent(new Node<>());
        this.root = insertAsBST(this.root, newNode);

    }

    private INode<T,V> getSuccessor(INode node){
        INode<T,V> n=node.getRightChild();
        while(n.getLeftChild().getKey()!=null){
            n=n.getLeftChild();
        }
        return n;
    }

    private  void rightRightDel(INode parent,INode sibling){
        sibling.getRightChild().setColor(INode.BLACK);
        sibling.setColor(parent.getColor());
        parent.setColor(INode.BLACK);

        rotateLeft(parent);
    }

    private  void rightLeftyDel(INode parent,INode sibling){
        sibling.getLeftChild().setColor(INode.BLACK);
        sibling.setColor(INode.RED);
        INode temp = sibling.getLeftChild();
        rotateRight(sibling);
        sibling = temp;
        rightRightDel(parent,sibling);
    }
    private  void leftLeftDel(INode parent,INode sibling){
        sibling.getLeftChild().setColor(INode.BLACK);
        sibling.setColor(parent.getColor());
        parent.setColor(INode.BLACK);

        rotateRight(parent);
    }

    private  void leftRightDel(INode parent,INode sibling){
        sibling.getRightChild().setColor(INode.BLACK);
        sibling.setColor(INode.RED);
        INode temp = sibling.getRightChild();
        rotateLeft(sibling);
        sibling = temp;
        leftLeftDel(parent,sibling);
    }

    @Override
    public boolean delete(T key) {
        INode<T,V> result=searchNode(key);//result contains the node to be deleted
        
        boolean rightSibling; //edit for case 1
        
        if(result.getKey()!=null){//if the node to be deleted is in the tree

            INode<T,V> child;
            if(result.getLeftChild().getKey()==null){//The left child of the node is null
                child=result.getRightChild();
            }

            else if (result.getRightChild().getKey()==null){//the right child of the node is null
                child=result.getLeftChild();
            }
            else {//Node is not a leaf node
                INode<T,V> successor=getSuccessor(result);
                result.setValue(successor.getValue());
                result.setKey(successor.getKey()); // edit key must be changed
                result=successor;
                child=successor.getRightChild();
            }
            INode<T,V> grandParent=result.getParent();
            INode<T,V> sibling;
            Boolean parentColor=result.getColor();
            Boolean childColor=child.getColor();

            if(result.getKey().compareTo(grandParent.getKey())>0){
                grandParent.setRightChild(child);
                sibling=grandParent.getLeftChild();
                rightSibling = false;//edit for case 1
            }
            else {
                grandParent.setLeftChild(child);
                sibling=grandParent.getRightChild();
                rightSibling = true;  //edit for case 1
            }
            if(parentColor==INode.RED||childColor==INode.RED){
                child.setColor(INode.BLACK);
            }
            else {
                boolean case1 = false;
                while(!case1){//TODO set case1 to true at the end of case1
                    if (sibling.getColor() == INode.RED) {//CASE 3
                        grandParent.setColor(INode.RED);
                        sibling.setColor(INode.BLACK);
                        if (grandParent.getLeftChild() == sibling) {
                            rotateRight(sibling);
                            sibling = grandParent.getLeftChild();
                        } else {
                            rotateLeft(sibling);
                            sibling = grandParent.getRightChild();
                        }
                        //TODO Check The sibling again


                    } else if (sibling.getColor() == INode.BLACK) {//TODO CASE 1 CASE 2
                        if(sibling.getLeftChild().getColor()==INode.BLACK && sibling.getRightChild().getColor()==INode.BLACK){//TODO CASE 2
                            sibling.setColor(INode.RED);
                            if(grandParent.getColor()==INode.RED){//END of the deletion
                                grandParent.setColor(INode.BLACK);
                                case1=true;
                            }else{
                                child=grandParent;
                                grandParent = grandParent.getParent();
                                if(child.getKey().compareTo(grandParent.getKey())>0){
                                    sibling=grandParent.getLeftChild();
                                }
                                else {
                                    sibling=grandParent.getRightChild();
                                }
                            }
                        }  else if (true){ //TODO GABAL CASE1
                            if (sibling.getRightChild().getColor() == INode.RED && rightSibling) {
                                case1 = true;
                                rightRightDel(grandParent, sibling);
                            } else if (sibling.getLeftChild().getColor() == INode.RED && rightSibling) {
                                case1 = true;
                                rightLeftyDel(grandParent, sibling);
                            }
                            else if (sibling.getLeftChild().getColor() == INode.RED && !rightSibling) {
                                case1 = true;
                                leftLeftDel(grandParent, sibling);
                            } else if (sibling.getRightChild().getColor() == INode.RED && !rightSibling) {
                                case1 = true;
                                leftRightDel(grandParent, sibling);
                            }
                        }
                    }
                }
            }


        }
        return false;//TODO return value
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
