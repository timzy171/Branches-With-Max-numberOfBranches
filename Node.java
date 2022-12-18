package org.example;
import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Node> children = new ArrayList<>();
    public Node parent;
    public int value;
    protected int childrensCount;

    public Node(int value) {
        this.value = value;    }
}
