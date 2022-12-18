package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Tree tree = new Tree();
        int n = 10;
        Tree.generateFile(100000,n,"C:\\test\\tree.txt");
        Tree.parseFile(tree,n,"C:\\test\\tree.txt");
        long time = System.currentTimeMillis();
        Tree.searchMaxBranching(tree);
        System.out.println(tree.getNodesSize() + " elements");
        long usedBytes = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576;
        System.out.println(usedBytes + " mb");
        System.out.println(System.currentTimeMillis() - time + " ms");
    }
}
