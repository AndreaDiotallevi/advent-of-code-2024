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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part2 {
    public List<Robot> robots = new ArrayList<>();
    int sizeX = 101;
    int sizeY = 103;
    // int sizeX = 5;
    // int sizeY = 5;
    List<Point> directions = new ArrayList<>(
            Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0)));

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
            File file = new File("resources/day14.txt");
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
            System.out.println("robots:");
            System.out.println(robots);
            System.out.println(" ");

            boolean exit = false;
            long seconds = 1;

            while (!exit) {
                // System.out.println("seconds+" + seconds);
                Map<Point, List<Robot>> grid = new HashMap<Point, List<Robot>>();
                Set<Robot> seen = new HashSet<Robot>();
                for (Robot robot : robots) {
                    Point point = robot.move();
                    List<Robot> robotsAtPoint = grid.get(point);
                    if (robotsAtPoint == null)
                        robotsAtPoint = new ArrayList<>();
                    robotsAtPoint.add(robot);
                    grid.put(point, robotsAtPoint);
                }
                // System.out.println(grid);

                find(grid, robots.get(0), seen);
                // for (Robot robot : robots) {
                // find(grid, robot, seen);
                // }
                if (seen.size() > 10)
                    System.out.printf("seen=%d%n", seen.size());
                // System.out.printf("robots-size=%d%n", robots.size());
                if (seen.size() == robots.size()) {
                    exit = true;
                    break;
                }
                if (seen.size() == 221) {
                    System.out.println(grid);
                    exit = true;
                }
                seconds++;
                // System.out.println(seconds);
                // exit = true;
                // if (seconds > 10) {
                // exit = true;
                // }

            }

            return seconds;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private void find(Map<Point, List<Robot>> grid, Robot robot, Set<Robot> seen) {
        // System.out.println("visiting:");
        // System.out.println(robot);
        if (seen.contains(robot))
            return;

        seen.add(robot);
        // System.out.println("adding:");
        // System.out.println(seen);

        for (Point direction : directions) {
            Point searchPoint = new Point(robot.location.x + direction.x, robot.location.y + direction.y);
            if (searchPoint.x < 0 || searchPoint.y < 0 || searchPoint.x > sizeX - 1 || searchPoint.y > sizeY - 1) {
                return;
            }
            List<Robot> robots = grid.get(searchPoint);
            if (robots == null)
                return;
            for (Robot searchRobot : robots) {
                find(grid, searchRobot, seen);
            }
        }
    }
}
