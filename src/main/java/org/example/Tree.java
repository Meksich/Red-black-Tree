package org.example;

public class Tree {
    public Node root;
    public static Node nilNode;

    public Tree(){
        this.root = nilNode;
    }

    static {
        nilNode = new Node(0, null, null, null);
    }

    public void insert(int value) {
        Node newNode = new Node(value, nilNode, nilNode, null);
        newNode.isBlack = false;

        Node currentParent = null;
        Node current = this.root;

        while (current != nilNode) {
            currentParent = current;
            if (newNode.data < current.data)
                current = current.left;
            else
                current = current.right;
        }
        newNode.parent = currentParent;
        if (currentParent == null)
            root = newNode;
        else if (newNode.data < currentParent.data)
            currentParent.left = newNode;
        else
            currentParent.right = newNode;

        if (newNode.parent == null){
            newNode.isBlack = true;
            return;
        }
        if (newNode.parent.parent == null) return;

        balanceTree(newNode);
    }

    private void balanceTree(Node currentNode) {
        Node uncle;
        while (!currentNode.parent.isBlack) {
            if (currentNode.parent == currentNode.parent.parent.right) {
                uncle = currentNode.parent.parent.left; // uncle
                if (!uncle.isBlack) {
                    uncle.isBlack = true;
                    currentNode.parent.isBlack = true;
                    currentNode.parent.parent.isBlack = false;
                    currentNode = currentNode.parent.parent;
                } else {
                    if (currentNode == currentNode.parent.left) {
                        currentNode = currentNode.parent;
                        rightRotate(currentNode);
                    }
                    currentNode.parent.isBlack = true;
                    currentNode.parent.parent.isBlack = false;
                    leftRotate(currentNode.parent.parent);
                }
            } else {
                uncle = currentNode.parent.parent.right;
                if (!uncle.isBlack) {
                    uncle.isBlack = true;
                    currentNode.parent.isBlack = true;
                    currentNode.parent.parent.isBlack = false;
                    currentNode = currentNode.parent.parent;
                } else {
                    if (currentNode == currentNode.parent.right) {
                        currentNode = currentNode.parent;
                        leftRotate(currentNode);
                    }
                    currentNode.parent.isBlack = true;
                    currentNode.parent.parent.isBlack = false;
                    rightRotate(currentNode.parent.parent);
                }
            }
            if (currentNode == root) {
                break;
            }
        }
        root.isBlack = true;
    }

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != nilNode) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != nilNode) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void delete(int value){

    }

    public void search(int value){
        Node result = this.root;
        while (result != nilNode){
            if (result.data == value)
                break;
            else if (result.data > value)
                result = result.left;
            else
                result = result.right;
        }
        if (result == nilNode) {
            System.out.print("No such value in tree");
            return;
        }
        System.out.print("Node with value " + value + " successfully found");
    }






    public void prettyPrint() {
        printHelper(this.root, "", true);
    }

    private void printHelper(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != nilNode) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.isBlack == false?"RED":"BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }
}
