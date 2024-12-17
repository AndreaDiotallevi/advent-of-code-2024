package day16;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Day16Part1mod {
    class Node {
        char value;
        Point location;
        List<Node> edges;

        public Node(Point location, char value) {
            this.location = location;
            this.value = value;
            this.edges = new ArrayList<>();
        }
    }

    public Node start;
    public Node end;
    public Map<Point, Node> maze = new HashMap<>();
    public int mazeSize;
    public List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1), // v
            new Point(1, 0), // >
            new Point(0, -1), // ^
            new Point(-1, 0))); // <

    public void readInput() {
        try {
            File file = new File("resources/day16test.txt");
            Scanner scanner = new Scanner(file);
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] chars = line.toCharArray();
                for (int x = 0; x < chars.length; x++) {
                    char c = chars[x];
                    Point point = new Point(x, y);
                    Node node = new Node(point, c);
                    maze.put(point, node);
                    if (c == 'S')
                        start = node;
                    if (c == 'E')
                        end = node;
                }
                y++;
            }
            scanner.close();
            mazeSize = y;

        } catch (FileNotFoundException e) {
            throw new Error("Error while reading input");
        }
    }

    public void printMaze() {
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                System.out.print(maze.get(new Point(x, y)).value);
            }
            System.out.println();
        }
    }

    public void buildGraph(Node currentNode, Set<Point> visited) {
        if (visited.contains(currentNode.location)) {
            return;
        } else {
            visited.add(currentNode.location);
        }

        if (currentNode.value == 'E') {
            return;
        }

        for (Point direction : directions) {
            Point nextPoint = new Point(currentNode.location.x + direction.x, currentNode.location.y + direction.y);
            Node nextNode = maze.get(nextPoint);
            if (nextNode.value == '#') {
                continue;
            }
            currentNode.edges.add(nextNode);
            buildGraph(nextNode, visited);
        }
    }

    public long processFile() {
        readInput();

        printMaze();

        Set<Point> visited = new HashSet<>();
        buildGraph(start, visited);

        return 0;
    }
}
