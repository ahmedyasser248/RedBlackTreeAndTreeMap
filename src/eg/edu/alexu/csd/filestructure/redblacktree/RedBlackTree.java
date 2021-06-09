package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>,V> implements IRedBlackTree<T,V>{
    INode<T,V> root ;
    boolean duplicate =false;
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


    private INode<T,V> searchNode(T key)  {
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
        if(key==null){
            throw new RuntimeErrorException(new Error());
        }
        INode<T,V> result=searchNode(key);
        if(result == null)
            return null;
        return result.getValue();
    }

    @Override
    public boolean contains(T key) {
        if(key==null){
            throw new RuntimeErrorException(new Error());
        }
        INode<T,V> result=searchNode(key);
        if(result.getKey()==null){
            return false;
        }
        return true;
    }

    @Override
    public void insert(T key, V value) {
        if(key==null||value==null){
            throw new RuntimeErrorException(new Error());
        }
        Node<T,V> newNode = new Node<>(key,value);
        newNode.setRightChild(new Node<>(INode.BLACK));
        newNode.setLeftChild(new Node<>(INode.BLACK));
        newNode.setParent(new Node<>(INode.BLACK));
        if(root.isNull()){
            this.root = newNode;
        }else {
            insertAsBST(newNode);
        }
        if(!duplicate) {
            fixUp(root, newNode);
        }
        duplicate = false;

    }
    public void insertAsBST(INode<T,V> newNode ){
        INode<T,V> temp = this.root;
        INode<T,V> temp2 = null;
        while(!temp.isNull()){
            temp2 = temp;
            if(newNode.getKey().compareTo(temp.getKey())<0){
                temp=temp.getLeftChild();
            }else if(newNode.getKey().compareTo(temp.getKey())>0) {
                temp = temp.getRightChild();
            }else {
                duplicate = true;
                temp.setValue(newNode.getValue());
                return;
            }
        }
        if(temp2.getKey().compareTo(newNode.getKey())>0){
            temp2.setLeftChild(newNode);

        }else {
            temp2.setRightChild(newNode);
        }
        newNode.setParent(temp2);

    }

    public void fixUp(INode<T,V> root,INode<T,V> newNode){

        while((newNode != root) && (newNode.getColor()!= INode.BLACK) && (newNode.getParent().getColor() == INode.RED)){
            Node<T,V> parent = (Node<T, V>) newNode.getParent();
            Node<T,V> grandParent = (Node<T, V>) parent.getParent();
            //if parent is a left child
            if(parent == grandParent.getLeftChild()){
                Node<T,V> uncle = (Node<T,V>) grandParent.getRightChild();
                // case 1: uncle is RED
                if((!uncle.isNull()) && (uncle.getColor() == INode.RED)){
                    grandParent.setColor(INode.RED);
                    parent.setColor(INode.BLACK);
                    uncle.setColor(INode.BLACK);
                    newNode = grandParent;
                } else {
                    // case 2: uncle is black and newNode is a right child
                    if(newNode == parent.getRightChild()){
                        rotateLeft(parent);
                        newNode = parent;
                        parent = (Node<T, V>) newNode.getParent();
                    }
                    // case 3: uncle is black and newNode is a left child
                    boolean color = parent.getColor();
                    parent.setColor(grandParent.getColor());
                    grandParent.setColor(color);

                    rotateRight(grandParent);

                    newNode = parent;
                }
            } else { //if parent is a right child
                Node<T,V> uncle = (Node<T,V>) grandParent.getLeftChild();
                // case 1: uncle is RED
                if((!uncle.isNull()) && (uncle.getColor() == INode.RED)){
                    grandParent.setColor(INode.RED);
                    parent.setColor(INode.BLACK);
                    uncle.setColor(INode.BLACK);
                    newNode = grandParent;
                } else {
                    // case 2: uncle is black and newNode is a left child
                    if(newNode == parent.getLeftChild()){
                        rotateRight(parent);
                        newNode = parent;
                        parent = (Node<T, V>) newNode.getParent();
                    }
                    // case 3: uncle is black and newNode is a left child
                    boolean color = parent.getColor();
                    parent.setColor(grandParent.getColor());
                    grandParent.setColor(color);

                    rotateLeft(grandParent);

                    newNode = parent;
                }
            }
        }
        this.root.setColor(INode.BLACK);
    }

    private INode<T,V> getSuccessor(INode node){
        INode<T,V> n=node.getRightChild();
        while(n.getLeftChild().getKey()!=null){
            n=n.getLeftChild();
        }
        return n;
    }

    @Override
    public boolean delete(T key){
        return newdelete(this,key);
    }
    public boolean newdelete(RedBlackTree<T,V> tree,T key){
        if(key==null){
            throw new RuntimeErrorException(new Error());
        }
        INode<T,V>nodeToBeDeleted =searchNode(key);
        if(nodeToBeDeleted.getKey()==null){
            return false;
        }
        INode<T,V> temp=nodeToBeDeleted;
        boolean originalColor=temp.getColor();
        INode<T,V> child;
        if(nodeToBeDeleted.getLeftChild().getKey()==null){
            child=nodeToBeDeleted.getRightChild();
            RB_Transplant(tree,nodeToBeDeleted,nodeToBeDeleted.getRightChild());//replace nodeToBeDeleted with its rightChild
        }
        else if (nodeToBeDeleted.getRightChild().getKey()==null){
            child=nodeToBeDeleted.getLeftChild();
            RB_Transplant(tree,nodeToBeDeleted,nodeToBeDeleted.getLeftChild());//replace nodeToBeDeleted with its leftChild
        }
        else {//Node to be deleted is internal
            temp=getSuccessor(nodeToBeDeleted);
            originalColor=temp.getColor();
            child=temp.getRightChild();
            if(temp.getParent()==nodeToBeDeleted){
                child.setParent(temp);
            }
            else {//replace successor with its right child
                RB_Transplant(tree,temp,temp.getRightChild());
                temp.setRightChild(nodeToBeDeleted.getRightChild());
                temp.getRightChild().setParent(temp);
            }
            RB_Transplant(tree,nodeToBeDeleted,temp);//replace the node to be deleted with its successor
            temp.setLeftChild(nodeToBeDeleted.getLeftChild());
            temp.getLeftChild().setParent(temp);
            temp.setColor(nodeToBeDeleted.getColor());
        }
        if(originalColor==INode.BLACK){
            deleteFixup(tree,child);
        }
        return true;
    }
    private void RB_Transplant(RedBlackTree<T,V> tree,INode<T,V> parent,INode<T,V> child){
        if (parent.getParent().getKey()==null){//deletion from root
            tree.root=child;
        }
        else if (parent==parent.getParent().getLeftChild()){
            parent.getParent().setLeftChild(child);
        }
        else {
            parent.getParent().setRightChild(child);
        }
        child.setParent(parent.getParent());
    }
    public void deleteFixup(RedBlackTree<T,V> tree,INode<T,V> child){
        while(child!=tree.root && child.getColor()==INode.BLACK){
            if(child==child.getParent().getLeftChild()){
                INode<T,V> sibling=child.getParent().getRightChild();
                if(sibling.getColor()==INode.RED){//Case 3 sibling is red
                    sibling.setColor(INode.BLACK);
                    child.getParent().setColor(INode.RED);
                    rotateLeft(child.getParent());
                    sibling=child.getParent().getRightChild();
                }
                if(sibling.getLeftChild().getColor()==INode.BLACK && sibling.getRightChild().getColor()==INode.BLACK){//Case 2 sibling with both its children are black
                    sibling.setColor(INode.RED);
                    child=child.getParent();
                }
                else if(sibling.getRightChild().getColor()==INode.BLACK){//Case 1 Right left
                    sibling.getLeftChild().setColor(INode.BLACK);
                    sibling.setColor(INode.RED);
                    rotateRight(sibling);
                    sibling=child.getParent().getRightChild();
                }
                if(sibling.getRightChild().getColor()==INode.RED) {//Case 1 Right Right
                    sibling.setColor(child.getParent().getColor());
                    child.getParent().setColor(INode.BLACK);
                    sibling.getRightChild().setColor(INode.BLACK);
                    rotateLeft(child.getParent());
                    child = tree.root;
                }
            }
            else {
                INode<T,V> sibling=child.getParent().getLeftChild();
                if(sibling.getColor()==INode.RED){//Case 3
                    sibling.setColor(INode.BLACK);
                    child.getParent().setColor(INode.RED);
                    rotateRight(child.getParent());
                    sibling=child.getParent().getLeftChild();
                }
                if(sibling.getRightChild().getColor()==INode.BLACK && sibling.getLeftChild().getColor()==INode.BLACK){//Case 2
                    sibling.setColor(INode.RED);
                    child=child.getParent();
                }
                else if(sibling.getLeftChild().getColor()==INode.BLACK){//Left right case
                    sibling.getRightChild().setColor(INode.BLACK);
                    sibling.setColor(INode.RED);
                    rotateLeft(sibling);
                    sibling=child.getParent().getLeftChild();
                }
                if(sibling.getLeftChild().getColor()==INode.RED) {//left left case
                    sibling.setColor(child.getParent().getColor());
                    child.getParent().setColor(INode.BLACK);
                    sibling.getLeftChild().setColor(INode.BLACK);
                    rotateRight(child.getParent());
                    child = tree.root;//To exit the loop
                }
            }
        }
        child.setColor(INode.BLACK);//root could be red because of case 2
    }

    public void rotateRight( INode<T,V> nodeToRotate){
        if(nodeToRotate.getLeftChild().isNull()){
            return;
        }
        INode<T,V> leftChild = nodeToRotate.getLeftChild();
        if(!nodeToRotate.getLeftChild().getRightChild().isNull()){
            nodeToRotate.setLeftChild(nodeToRotate.getLeftChild().getRightChild());
            nodeToRotate.getLeftChild().setParent(nodeToRotate);
        }else{
            nodeToRotate.setLeftChild(new Node<>(INode.BLACK));
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
            nodeToRotate.setRightChild(new Node<>(INode.BLACK));
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

    public void print(INode<T,V>root){
        System.out.println(root.getKey());
        if(!root.getLeftChild().isNull()){
            print(root.getLeftChild());
        }
        if(!root.getRightChild().isNull()){
            print(root.getRightChild());
        }
    }


    static void print2DUtil(INode root, int space)
    {
        // Base case
        if (root == null ||root.isNull() ){
            //root.setColor(INode.BLACK);
            return;
        }
        // Increase distance between levels
        space += 5;
        // Process right child first
        print2DUtil(root.getRightChild(), space);
        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = 5; i < space; i++)
            System.out.print(" ");
        System.out.print(root.getKey()+ "\n") ;
        for (int i = 5; i < space; i++)
            System.out.print(" ");
        System.out.print(root.getColor()) ;
        // Process left child
        print2DUtil(root.getLeftChild(), space);
    }

    public void setLeaf(INode root){
        if(root == null){
            return;
        }
        if (root.getKey()==null){
            root.setColor(INode.BLACK);
        }
        setLeaf(root.getRightChild());
        setLeaf(root.getLeftChild());
    }
    // Wrapper over print2DUtil()
    static void print2D(INode root)
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }




}