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

public class Day16Part1ok {
    public class Node {
        private int x;
        private int y;
        private Direction direction;

        // Enum for directions in a specific order
        public enum Direction {
            UP, RIGHT, DOWN, LEFT;

            // Get the next direction when turning right
            public Direction turnRight() {
                return values()[(this.ordinal() + 1) % values().length];
            }

            // Get the next direction when turning left
            public Direction turnLeft() {
                return values()[(this.ordinal() + values().length - 1) % values().length];
            }
        }

        // Constructor
        public Node(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        // Getters and setters
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        // Move in the current direction
        public void move() {
            switch (direction) {
                case UP -> y--;
                case RIGHT -> x++;
                case DOWN -> y++;
                case LEFT -> x--;
            }
        }

        // Turn right
        public void turnRight() {
            this.direction = direction.turnRight();
        }

        // Turn left
        public void turnLeft() {
            this.direction = direction.turnLeft();
        }

        public char getMazeValue() {
            return maze.get(this.x).get(this.y);
        }

        // Override equals to compare x, y, and direction
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y && direction == node.direction;
        }

        // Override hashCode to compute based on x, y, and direction
        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", direction=" + direction +
                    '}';
        }
    }

    public Point target;
    public Node start;
    public List<List<Character>> maze = new ArrayList<>();
    // public Map<Node, Long> scoreMap = new HashMap<>();

    public void initialiseMaze() {
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
                        start = new Node(i, maze.size(), Node.Direction.LEFT);
                    if (c == 'E')
                        target = new Point(i, maze.size());
                }
                maze.add(mazeRow);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Error("Problem with input");
        }
    }

    public void printMaze() {
        for (int y = 0; y < maze.size(); y++) {
            for (int x = 0; x < maze.size(); x++) {
                System.out.print(maze.get(y).get(x));
            }
            System.out.println();
        }
    }

    public void buildPathGraph(Node node, Set<Node> visited) {
        if (node.getMazeValue() == 'E') {
            return;
        }
        // Forward
        // stepForward();
    }

    public long processFile() {
        initialiseMaze();
        printMaze();
        buildPathGraph(start, new HashSet<>());
        return 0;
    }
}
