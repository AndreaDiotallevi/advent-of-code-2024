package day16;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day16Part1 {
    class Reindeer {
        Point location;
        Point direction;

        public Reindeer(Point location, Point direction) {
            this.location = location;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "Reindeer{" +
                    "location=" + location +
                    ", direction=" + direction +
                    '}';
        }
    }

    public List<List<Character>> maze = new ArrayList<>();
    public Reindeer reindeer;
    public Point target;
    public List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)));

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
                        reindeer = new Reindeer(new Point(i, maze.size()), new Point(-1, 0));
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

            // for (List<Character> row : maze) {
            // for (Character cell : row) {
            // System.out.print(cell);
            // }
            // System.out.println();
            // }

            System.out.println(reindeer);
            System.out.println(target);

            long sum = 0;
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
