package day16;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day16Part1 {
    public List<List<Character>> maze = new ArrayList<>();
    public Point reindeer;
    public Point target;

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
                        reindeer = new Point(i, maze.size());
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

            long sum = 0;
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
