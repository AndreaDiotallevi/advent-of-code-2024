package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day10Part1 {
    public List<List<Integer>> matrix = new ArrayList<>();
    public List<List<Node>> nodeMatrix = new ArrayList<>();
    public Set<String> trailheads = new HashSet<>();

    public class Node {
        int value;
        int x, y; // Coordinates of the node
        List<int[]> edges; // Store edges as (x, y) pairs
    
        public Node(int value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
            this.edges = new ArrayList<>();
        }
    
        public void addEdge(int x, int y) {
            edges.add(new int[]{x, y});
        }
    
        @Override
        public String toString() {
            StringBuilder edgeList = new StringBuilder();
            for (int[] edge : edges) {
                edgeList.append("(").append(edge[0]).append(", ").append(edge[1]).append("), ");
            }
            // Remove trailing comma and space if edges exist
            if (!edges.isEmpty()) {
                edgeList.setLength(edgeList.length() - 2);
            }
            return "Node{value=" + value + ", edges=[" + edgeList + "]}";
        }
    }
    
    public int processFile() {
        try {
            // Initialise matrix
            File file = new File("resources/day10.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                List<Integer> row = new ArrayList<>();
                for (int i=0; i<charArray.length; i++) {
                    int num = charArray[i] - '0';
                    row.add(num);
                }
                matrix.add(row);
            }
            scanner.close();
            System.out.println(matrix);
            int size = matrix.size();

            // Create initial nodes
            for (int x=0; x<size; x++) {
                List<Node> nodeMatrixRow = new ArrayList<>();
                for (int y=0; y<size; y++) {
                    Node newNode = new Node(matrix.get(x).get(y),x,y);
                    nodeMatrixRow.add(newNode);
                }
                nodeMatrix.add(nodeMatrixRow);
            }
            System.out.println(nodeMatrix);

            // Add children
            for (int x=0; x<size; x++) {
                for (int y=0; y<size; y++) {
                    Node node = nodeMatrix.get(x).get(y);

                    // Check right
                    if (y<size-1) {
                        Node nextNode = nodeMatrix.get(x).get(y+1);
                        if (nextNode.value-node.value==1) {
                            node.addEdge(x, y+1);
                        }
                    }
                    // Check down
                    if (x<size-1) {
                        Node nextNode = nodeMatrix.get(x+1).get(y);
                        if (nextNode.value-node.value==1) {
                            node.addEdge(x+1, y);
                        }
                    }
                    // Check left
                    if (y>0) {
                        Node nextNode = nodeMatrix.get(x).get(y-1);
                        if (nextNode.value-node.value==1) {
                            node.addEdge(x, y-1);
                        }
                    }
                    // Check up
                    if (x>0) {
                        Node nextNode = nodeMatrix.get(x-1).get(y);
                        if (nextNode.value-node.value==1) {
                            node.addEdge(x-1, y);
                        }
                    }
                }
            }
            System.out.println(nodeMatrix);

            // Start checking from zeros
            for (int x=0; x<size; x++) {
                for (int y=0; y<size; y++) {
                    Node node = nodeMatrix.get(x).get(y);
                    if (node.value != 0) continue;
                    // Traverse from zero
                    search(node, x, y);
                }
            }

            return trailheads.size();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private void search(Node originalNode, int x, int y) {
        Node node = nodeMatrix.get(x).get(y);

        if (node.edges.size()==0) {
            if (node.value==9) {
                trailheads.add(originalNode.x + "-" + originalNode.y + "-" + x + "-" + y);
            }
            return;
        }

        for (int[] edge : node.edges) {
            search(originalNode, edge[0], edge[1]);
        }
    }
}
