package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String []args){
        Tree bst = new Tree();
        bst.insert(8);
        bst.insert(18);
        bst.insert(5);
        bst.insert(15);
        bst.insert(17);
        bst.insert(25);
        bst.insert(40);
        bst.insert(80);
        bst.prettyPrint();
        bst.delete(8);
        bst.insert(6);
        bst.delete(6);
        bst.delete(80);
        bst.delete(1);
        bst.prettyPrint();
        bst.search(165);
    }

}
