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

    public Point start;
    public Point end;
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
                    maze.put(new Point(x, y), new Node(new Point(x, y), c));
                    if (c == 'S')
                        start = new Point(x, y);
                    if (c == 'E')
                        end = new Point(x, y);
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
        // System.out.println(mazeSize);
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                System.out.print(maze.get(new Point(x, y)).value);
            }
            System.out.println();
        }
    }

    public long processFile() {
        readInput();
        printMaze();
        return 0;
    }
}
