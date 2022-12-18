package org.example;
import java.io.*;
import java.util.*;

    public class Tree {

        public Set<Node> maxSonNodes = new HashSet<>();
        public Node root;
        private Set<Node> nodes = new HashSet<>();
        private int maxChildrenCount = 0;

        public int getNodesSize() {
            return nodes.size();
        }

        public Tree(Node root) {
            this.root = root;
            nodes.add(root);
        }

        public Tree() {
        }

        public void addChildren(Node parent, Node children) {
            parent.children.add(children);
            children.parent = parent;
            nodes.add(children);
            ++parent.childrensCount;
            this.maxChildrenCount = Integer.max(parent.childrensCount, maxChildrenCount);
        }

        public Node goPrev(Node node) {
            return node.parent;
        }


        private static void searchMaxSonNodes(Tree tree) {
            for (Node node : tree.nodes) {
                if (node.childrensCount == tree.maxChildrenCount) {
                    tree.maxSonNodes.add(node);
                }
            }
        }

        public static void searchMaxBranching(Tree tree) {
            Tree.searchMaxSonNodes(tree);
            Map<Node, Node> map = new HashMap<>();
            for (Node node : tree.maxSonNodes) {
                Node current = node;
                if (current.parent != null) {
                    if (current.parent.childrensCount == 1) {
                        map.put(current.parent, current);
                    } else {
                        while (current.parent.childrensCount != 1) {
                            if (current.parent.equals(tree.root)) {
                                break;
                            }
                            current = current.parent;
                        }
                        map.put(current.parent, current);
                    }
                }
            }

            for (Node node : map.keySet()) {
                System.out.println(node.value + " " + map.get(node).value);
            }
        }

        public static void generateFile(int size, int n, String fileName) throws IOException {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File was created");
            }

            FileWriter fileWriter = new FileWriter(fileName);
            int root = (int) (Math.random() * 10);
            if (root == 0) root = 1;
            fileWriter.write(root + "\n");
            String[] commands = new String[]{"c", "gs", "gp", "c"};


            for (int i = 0; i < size; i++) {
                int randomN = (int) (Math.random() * (n - 1));
                int command = (int) (Math.random() * 4);
                int value = (int) (Math.random() * 10000);
                switch (command) {
                    case 0, 3 -> {
                        fileWriter.write(commands[command] + " " + value + "\n");
                    }
                    case 1 -> {
                        fileWriter.write(commands[command] + " " + randomN + "\n");
                    }
                    default -> {
                        fileWriter.write(commands[command] + "\n");
                    }
                }
            }
            System.out.println("File was filled");
            fileWriter.close();
        }

        public static void parseFile(Tree tree, int n, String fileName) throws IOException {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);
            tree.root = new Node(Integer.parseInt(reader.readLine()));
            tree.nodes.add(tree.root);
            Node current = tree.root;
            String line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split(" ");
                switch (lineArray[0]) {
                    case "c" -> {
                        if (current.childrensCount < n) {
                            tree.addChildren(current, new Node(Integer.parseInt(lineArray[1])));
                        }
                    }
                    case "gs" -> {
                        try {
                            if (current.childrensCount == 1 && Integer.parseInt(lineArray[1]) == 0) {
                                current = current.children.get(0);
                            }
                            if (current.childrensCount > 1 && current.childrensCount > Integer.parseInt(lineArray[1])) {
                                current = current.children.get(Integer.parseInt(lineArray[1]));
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR");
                        }
                    }
                    case "gp" -> {
                        if (current.parent != null) {
                            current = tree.goPrev(current);
                        }
                    }
                    default -> System.out.println("Error");
                }
                line = reader.readLine();
            }
        }
    }
