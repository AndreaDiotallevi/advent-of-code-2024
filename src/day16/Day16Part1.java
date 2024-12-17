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
    // class PointWithDirection {
    // Point location;
    // Point direction;

    // public PointWithDirection(Point location, Point direction) {
    // this.location = location;
    // this.direction = direction;
    // }

    // @Override
    // public String toString() {
    // return "PointWithDirection{" +
    // "location=" + location +
    // ", direction=" + direction +
    // '}';
    // }

    // @Override
    // public boolean equals(Object o) {
    // if (this == o)
    // return true; // Same reference
    // if (o == null || getClass() != o.getClass())
    // return false; // Null or different class
    // PointWithDirection that = (PointWithDirection) o;
    // return Objects.equals(location, that.location) &&
    // Objects.equals(direction, that.direction); // Compare fields
    // }

    // @Override
    // public int hashCode() {
    // return Objects.hash(location, direction); // Generate hash based on fields
    // }
    // }

    public Point start;
    public Point end;
    // public List<List<Character>> maze = new ArrayList<>();
    public Map<Point,Nod> maze = new HashMap<>();
    // public Map<Point, Long> scoreMap = new HashMap<>();
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
                        start = new Point(i, maze.size());
                    if (c == 'E')
                        end = new Point(i, maze.size());
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

            // long result = getMinimumScoreForPointWithDirection(reindeer, 0);
            // System.out.println(scoreMap);

            long result = 0;
            return result;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private 

    // private long getMinimumScoreForPointWithDirection(PointWithDirection
    // reindeer, long score) {
    // if (scoreMap.containsKey(reindeer)) {
    // return scoreMap.get(reindeer);
    // }
    // System.out.println(reindeer);
    // List<Long> scores = new ArrayList<>();
    // for (Point direction : directions) {
    // System.out.println(direction);
    // PointWithDirection next = new PointWithDirection(
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

    // private long search(PointWithDirection reindeer, long score) {
    // char nextChar = maze.get(reindeer.location.x + reindeer.direction.x)
    // .get(reindeer.location.y + reindeer.direction.y);

    // if (nextChar == '#') {
    // scoreMap.put(reindeer, score);
    // int index = directions.indexOf(reindeer.location);

    // PointWithDirection reindeerAntiClockwise = new PointWithDirection(new
    // Point(reindeer.location),
    // directions.get((index + 1) % 4));
    // long result1 = search(reindeerAntiClockwise, score);

    // PointWithDirection reindeerClockwise = new PointWithDirection(new
    // Point(reindeer.location),
    // directions.get((index - 1) % 4));
    // long result2 = search(reindeerClockwise, score);
    // }
    // }
}
