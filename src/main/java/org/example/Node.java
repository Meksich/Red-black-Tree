package org.example;

public class Node {
    public int data;
    public Node left, right, parent;
    public boolean isBlack;

    public Node(int element, Node left, Node right, Node parent ){
        this.data = element;
        this.left = left;
        this.right = right;
        this.isBlack = true;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Value is:" + this.data;
    }
}
