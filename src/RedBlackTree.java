package com.paradigms;

import static com.paradigms.INode.*;

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
        newNode.setRightChild(new Node<>(BLACK));
        newNode.setLeftChild(new Node<>(BLACK));
        newNode.setParent(new Node<>(BLACK));
        this.root = insertAsBST(this.root, newNode);
        fixUp(root,newNode);
    }

    public void fixUp(INode<T,V> root,INode<T,V> newNode){

        while((newNode != root) && (newNode.getColor()!= BLACK) && (newNode.getParent().getColor() == RED)){
            Node<T,V> parent = (Node<T, V>) newNode.getParent();
            Node<T,V> grandParent = (Node<T, V>) parent.getParent();

            //if parent is a left child
            if(parent == grandParent.getLeftChild()){
                Node<T,V> uncle = (Node<T,V>) grandParent.getRightChild();
                // case 1: uncle is RED
                if((!uncle.isNull()) && (uncle.getColor() == RED)){
                    grandParent.setColor(RED);
                    parent.setColor(BLACK);
                    uncle.setColor(BLACK);
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
                if((!uncle.isNull()) && (uncle.getColor() == RED)){
                    grandParent.setColor(RED);
                    parent.setColor(BLACK);
                    uncle.setColor(BLACK);
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
        this.root.setColor(BLACK);
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
            nodeToRotate.setLeftChild(new Node<>(BLACK));
        }
        if(nodeToRotate.getParent().isNull()){
            leftChild.setParent(new Node<>(BLACK));
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
            nodeToRotate.setRightChild(new Node<>(BLACK));
        }
        if(nodeToRotate.getParent().isNull()){
            rightChild.setParent(new Node<>(BLACK));
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
        System.out.print(root.getKey() );
        System.out.println(root.getColor()?" Red": " Black");
        if(!root.getLeftChild().isNull()){
            print(root.getLeftChild());
        }else{
            System.out.println("null" + (root.getLeftChild().getColor()?" Red": " Black"));
        }
        if(!root.getRightChild().isNull()){
            print(root.getRightChild());
        }else{
            System.out.println("null" + (root.getRightChild().getColor()?" Red": " Black"));
        }
    }

}
