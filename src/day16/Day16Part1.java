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

public class Day16Part1 {
    class Node {
        Point location;
        Point direction;
        List<Edge> edges;

        public Node(Point location, Point direction) {
            this.location = location;
            this.direction = direction;
            this.edges = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "location=" + location +
                    ", direction=" + direction +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Node that = (Node) o;
            return Objects.equals(location, that.location) &&
                    Objects.equals(direction, that.direction);
        }

        @Override
        public int hashCode() {
            return Objects.hash(location, direction);
        }
    }

    class Edge {
        Node node;
        int weight;

        public Edge(Node node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public Point target;
    public Node reindeer;
    public List<List<Character>> maze = new ArrayList<>();
    public Map<Node, Long> scoreMap = new HashMap<>();
    public List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1), // v
            new Point(1, 0), // >
            new Point(0, -1), // ^
            new Point(-1, 0))); // <

    public long processFile() {
        try {
            File file = new File("resources/day16test.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<Character> mazeRow = new ArrayList<>();
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char c = chars[i];
                    mazeRow.add(c);
                    if (c == 'S')
                        reindeer = new Node(new Point(i, maze.size()), new Point(-1, 0));
                    if (c == 'E')
                        target = new Point(i, maze.size());
                }
                maze.add(mazeRow);
            }
            scanner.close();

            for (int y = 0; y < maze.size(); y++) {
                for (int x = 0; x < maze.size(); x++) {
                    System.out.print(maze.get(y).get(x));
                }
                System.out.println();
            }
            System.out.println(reindeer);
            System.out.println(target);

            // long result = getMinimumScoreForPointWithDirection(reindeer, 0);
            // System.out.println(scoreMap);

            buildGraph(reindeer, new HashSet<>());
            // Node edge1 = reindeer.edges.get(0).node;
            // Node edge2 = reindeer.edges.get(1).node;
            // System.out.println(reindeer);
            // System.out.println(edge1);
            // System.out.println(edge2);
            // System.out.println(reindeer.edges.get(0));
            Long minScore = null;
            findShortestPath(reindeer, 0L, minScore);
            System.out.println(minScore);

            long result = 0;
            return result;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    public void buildGraph(Node currentNode, Set<Node> visited) {
        // System.out.println("here");
        if (currentNode.location == target) {
            return;
        }

        if (visited.contains(currentNode)) {
            // System.out.println("visited");
            return;
        } else {
            // System.out.println("not visited");
            visited.add(currentNode);
        }

        // Step forward
        Point forwardPoint = new Point(currentNode.location.x + currentNode.direction.x,
                currentNode.location.y + currentNode.direction.y);

        Character forwardChar = maze.get(forwardPoint.x).get(forwardPoint.y);
        if (forwardChar != '#') {
            Node node = new Node(forwardPoint, currentNode.direction);
            Edge edge = new Edge(node, 1);
            currentNode.edges.add(edge);
            buildGraph(node, visited);
        }

        // Turn right
        int currentDirectionIndex = directions.indexOf(currentNode.direction);
        Point newDirection1 = directions.get((currentDirectionIndex + 1) % 4);
        Node node1 = new Node(new Point(currentNode.location), newDirection1);
        Edge edge1 = new Edge(node1, 1000);
        currentNode.edges.add(edge1);
        buildGraph(node1, visited);

        // Turn left
        Point newDirection2 = directions.get((currentDirectionIndex + 3) % 4);
        Node node2 = new Node(new Point(currentNode.location), newDirection2);
        Edge edge2 = new Edge(node2, 1000);
        currentNode.edges.add(edge2);
        buildGraph(node2, visited);
    }

    public void findShortestPath(Node node, Long score, Long minScore) {
        System.out.println();
        System.out.println(node);
        System.out.println(node.edges);
        if (node.location == target) {
            System.out.println("here");
            System.out.println(score);
            if (minScore == null || score < minScore) {
                minScore = score;
                return;
            }
        }
        for (Edge edge : node.edges) {
            findShortestPath(edge.node, score + edge.weight, minScore);
        }
    }

    // private long getMinimumScoreForPointWithDirection(Node
    // reindeer, long score) {
    // if (scoreMap.containsKey(reindeer)) {
    // return scoreMap.get(reindeer);
    // }
    // System.out.println(reindeer);
    // List<Long> scores = new ArrayList<>();
    // for (Point direction : directions) {
    // System.out.println(direction);
    // Node next = new Node(
    // new Point(reindeer.location.x + direction.x, reindeer.location.y +
    // direction.y),
    // new Point(direction));
    // System.out.println(next);
    // if (reindeer.direction != direction) {
    // score += 1000;
    // }
    // char nextChar = maze.get(next.location.x).get(next.location.y);
    // if (nextChar == 'E') {
    // return 1;
    // }
    // if (nextChar == '#') {
    // continue;
    // }

    // score++;
    // scores.add(getMinimumScoreForPointWithDirection(next, score));
    // }
    // System.out.println(scores);
    // scores.removeIf(n -> n == -1);
    // long minScore = scores.size() == 0 ? -1 : Collections.min(scores);
    // scoreMap.put(reindeer, minScore);
    // return minScore;
    // }

    // private long search(Node reindeer, long score) {
    // char nextChar = maze.get(reindeer.location.x + reindeer.direction.x)
    // .get(reindeer.location.y + reindeer.direction.y);

    // if (nextChar == '#') {
    // scoreMap.put(reindeer, score);
    // int index = directions.indexOf(reindeer.location);

    // Node reindeerAntiClockwise = new Node(new
    // Point(reindeer.location),
    // directions.get((index + 1) % 4));
    // long result1 = search(reindeerAntiClockwise, score);

    // Node reindeerClockwise = new Node(new
    // Point(reindeer.location),
    // directions.get((index - 1) % 4));
    // long result2 = search(reindeerClockwise, score);
    // }
    // }
}
