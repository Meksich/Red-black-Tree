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

    public Node search(int value){
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
            System.out.print("No such value in tree\n");
            return result;
        }
        System.out.print("Node with value " + value + " successfully found\n");
        return result;
    }


    public void delete(int value){
        Node nodeToDelete = search(value);
        if (nodeToDelete == nilNode)
            return;
        Node nodeForBalance;
        boolean deleteColor = nodeToDelete.isBlack;
        if(nodeToDelete.left == nilNode) {
            nodeForBalance = nodeToDelete.right;
            replaceA_with_B(nodeToDelete, nodeToDelete.right);
        } else if (nodeToDelete.right == nilNode) {
            nodeForBalance = nodeToDelete.left;
            replaceA_with_B(nodeToDelete, nodeToDelete.left);
        } else {
            Node temp = minimum(nodeToDelete.right);
            deleteColor = temp.isBlack;
            nodeForBalance = temp.right;
            if (temp.parent == nodeToDelete) {
                nodeForBalance.parent = temp;
            } else {
                replaceA_with_B(temp, temp.right);
                temp.right = nodeToDelete.right;
                temp.right.parent = temp;
            }

            replaceA_with_B(nodeToDelete, temp);
            temp.left = nodeToDelete.left;
            temp.left.parent = temp;
            temp.isBlack = nodeToDelete.isBlack;
        }
        if (deleteColor)
            balanceDelete(nodeForBalance);
    }

    private void balanceDelete(Node nodeForBalance) {
        Node sibling;
        while (nodeForBalance != root && nodeForBalance.isBlack) {
            if (nodeForBalance == nodeForBalance.parent.left) {
                sibling = nodeForBalance.parent.right;
                if (!sibling.isBlack) {
                    sibling.isBlack = true;
                    nodeForBalance.parent.isBlack = false;
                    leftRotate(nodeForBalance.parent);
                    sibling = nodeForBalance.parent.right;
                }
                if(sibling.left.isBlack && sibling.right.isBlack) {
                    sibling.isBlack = false;
                    nodeForBalance = nodeForBalance.parent;
                } else {
                    if (sibling.right.isBlack) {
                        sibling.left.isBlack = true;
                        sibling.isBlack = false;
                        rightRotate(sibling);
                        sibling = nodeForBalance.parent.right;
                    }
                    sibling.isBlack = nodeForBalance.parent.isBlack;
                    nodeForBalance.parent.isBlack = true;
                    sibling.right.isBlack = true;
                    leftRotate(nodeForBalance.parent);
                    nodeForBalance = root;
                }
            } else {
                sibling = nodeForBalance.parent.left;
                if (!sibling.isBlack) {
                    sibling.isBlack = true;
                    nodeForBalance.parent.isBlack = false;
                    rightRotate(nodeForBalance.parent);
                    sibling = nodeForBalance.parent.left;
                }
                if (sibling.right.isBlack) {
                    sibling.isBlack = false;
                    nodeForBalance = nodeForBalance.parent;
                } else {
                    if(sibling.left.isBlack) {
                        sibling.right.isBlack = true;
                        sibling.isBlack = false;
                        leftRotate(sibling);
                        sibling = nodeForBalance.parent.left;
                    }
                    sibling.isBlack = nodeForBalance.parent.isBlack;
                    nodeForBalance.parent.isBlack = true;
                    sibling.left.isBlack = true;
                    rightRotate(nodeForBalance.parent);
                    nodeForBalance = root;
                }
            }
        }
        nodeForBalance.isBlack = true;
    }

    public Node minimum(Node node) {
        while (node.left != nilNode) {
            node = node.left;
        }
        return node;
    }

    public void replaceA_with_B(Node a, Node b){
        if(this.root == a)
            this.root = b;
        else if(a == a.parent.left)
            a.parent.left = b;
        else if(a == a.parent.right)
            a.parent.right = b;
        b.parent = a.parent;

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

            String sColor = !root.isBlack ?"RED":"BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }
}
