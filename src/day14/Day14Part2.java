package day14;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part2 {
    public List<Robot> robots = new ArrayList<>();
    // int sizeX = 101;
    // int sizeY = 103;
    int sizeX = 11;
    int sizeY = 7;
    // List<Point> directions = new ArrayList<>(
    // Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(0, -1), new
    // Point(-1, 0)));

    class Robot {
        Point location;
        Point velocity;

        public Robot(Point location, Point velocity) {
            this.location = location;
            this.velocity = velocity;
        }

        public Point move() {
            // System.out.println("moving point");
            // System.out.println(this);
            int x = (this.location.x + this.velocity.x) % sizeX;
            int y = (this.location.y + this.velocity.y) % sizeY;
            x = x < 0 ? sizeX + x : x;
            y = y < 0 ? sizeY + y : y;

            this.location.x = x;
            this.location.y = y;
            // System.out.println(this.location);
            return new Point(x, y);
        }

        @Override
        public String toString() {
            return "Robot{" +
                    "location=(" + location.x + ", " + location.y + "), " +
                    "velocity=(" + velocity.x + ", " + velocity.y + ")" +
                    "}";
        }
    }

    public long processFile() {
        try {
            File file = new File("resources/day14test.txt");
            Scanner scanner = new Scanner(file);
            String regex = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)";
            Pattern pattern = Pattern.compile(regex);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int vx = Integer.parseInt(matcher.group(3));
                    int vy = Integer.parseInt(matcher.group(4));
                    Point location = new Point(x, y);
                    Point velocity = new Point(vx, vy);
                    Robot robot = new Robot(location, velocity);
                    robots.add(robot);
                }
            }
            scanner.close();
            // System.out.println("robots:");
            // System.out.println(robots);
            // System.out.println(" ");

            boolean exit = false;
            long seconds = 1;

            while (!exit) {
                System.out.println("seconds+" + seconds);
                Map<Point, List<Robot>> grid = new HashMap<Point, List<Robot>>();
                for (Robot robot : robots) {
                    Point point = robot.move();
                    List<Robot> robotsAtPoint = grid.get(point);
                    if (robotsAtPoint == null)
                        robotsAtPoint = new ArrayList<>();
                    robotsAtPoint.add(robot);
                    grid.put(point, robotsAtPoint);
                }
                List<List<String>> newGrid = new ArrayList<>();
                for (int x = 0; x < sizeX; x++) {
                    List<String> row = new ArrayList<>();
                    for (int y = 0; y < sizeY; y++) {
                        row.add("");
                    }
                    newGrid.add(row);
                }
                for (int x = 0; x < sizeX; x++) {
                    for (int y = 0; y < sizeY; y++) {
                        List<Robot> robots = grid.get(new Point(x, y));
                        if (robots != null) {
                            newGrid.get(x).set(y, "O");
                        }
                    }
                }
                // for (int x = 0; x < sizeX; x++) {
                // System.out.println(newGrid.get(x));
                // }
                // System.out.println(" ");

                for (int x = 0; x < sizeX; x++) {
                    // Assume newGrid.get(x) returns a list or array for a row
                    List<String> row = newGrid.get(x); // Or whatever type newGrid contains

                    for (int y = 0; y < row.size(); y++) {
                        // Format each cell to be 2 characters wide (adjust as needed)
                        System.out.printf("%-2s", row.get(y).isEmpty() ? "." : row.get(y));
                    }
                    System.out.println(); // Move to the next line after printing a row
                }
                System.out.println();

                if (seconds == 100) {
                    exit = true;
                    break;
                }

                seconds++;
                // try {
                // TimeUnit.MILLISECONDS.sleep(500);
                // ;
                // } catch (InterruptedException e) {

                // }
                // exit = true;
            }

            return seconds;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}

// 30
// 31
// 32
// 68
