package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

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


    public INode<T,V> searchNode(T key)  {
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
        this.root = insertAsBST(this.root, newNode);
        fixUp(root,newNode);
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
        if(key==null){
            throw new RuntimeErrorException(new Error());
        }
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
            Boolean isRoot=grandParent==null||grandParent.getKey()==null;
            if(isRoot){
                if(this.root.getRightChild().getKey()==null){
                    this.root=this.root.getLeftChild();
                    this.root.setColor(INode.BLACK);
                    this.root.setParent(new Node<>(INode.BLACK));
                }
                else {
                    this.root=this.root.getRightChild();
                    this.root.setColor(INode.BLACK);
                    this.root.setParent(new Node<> (INode.BLACK));
                }
                return true;
            }

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
            child.setParent(grandParent);
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
                            rotateRight(grandParent);
                            sibling = grandParent.getLeftChild();
                        } else {
                            rotateLeft(grandParent);
                            sibling = grandParent.getRightChild();
                        }
                        //TODO Check The sibling again


                    } else if (sibling.getColor() == INode.BLACK) { //TODO CASE 1 CASE 2
                        if(sibling.getLeftChild().getColor()==INode.BLACK && sibling.getRightChild().getColor()==INode.BLACK){//TODO CASE 2
                            sibling.setColor(INode.RED);
                            if(grandParent.getColor()==INode.RED){//END of the deletion
                                grandParent.setColor(INode.BLACK);
                                case1=true;
                            }else{
                                child=grandParent;
                                if(child==this.root){
                                    case1=true;
                                    continue;
                                }
                                grandParent = grandParent.getParent();
                                if(child.getKey().compareTo(grandParent.getKey())>0){
                                    sibling=grandParent.getLeftChild();
                                }
                                else {
                                    sibling=grandParent.getRightChild();
                                }
                            }
                        }  else { //TODO GABAL CASE1
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

            return true;
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


    public static void main(String[] args){


        RedBlackTree<Integer,Integer> obj = new RedBlackTree<>();
        obj.insert(70,1500);
        obj.insert(98,1500);
        obj.insert(40,1500);
        obj.insert(99,1500);
        obj.insert(90,1500);
        obj.insert(94,1500);
        obj.insert(65,1500);
        obj.insert(20,1500);
        obj.insert(50,1500);
        obj.insert(67,1500);
        obj.insert(66,1500);
        obj.insert(69,1500);
        obj.insert(15,1500);
        obj.insert(30,1500);
        obj.insert(25,1500);
        obj.insert(32,1500);
        obj.insert(31,1500);
        obj.insert(35,1500);


        print2D(obj.getRoot());
        System.out.println("\n");
        obj.delete(90);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(90);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(15);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(15);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(50);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(50);
        System.out.println(obj.searchNode(69).getParent().getKey());

        print2D(obj.root);
        System.out.println("\n");
        obj.delete(94);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(94);
        System.out.println(obj.searchNode(69).getParent().getKey());


        System.out.println("\n");
        obj.delete(40);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(40);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(65);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(65);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(35);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(35);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(25);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(25);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(20);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("HERE");
        System.out.println(20);
        System.out.println(obj.searchNode(69).getParent().getKey());

        System.out.println("\n");
        obj.delete(32);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(31);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(30);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(69);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(67);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(99);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(98);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(70);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(66);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

        System.out.println("\n");
        obj.delete(70);
        System.out.println("\n");
        System.out.println("--------------------------------------------------------------");
        print2D(obj.getRoot());

    }
}