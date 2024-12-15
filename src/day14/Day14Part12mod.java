package day14;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part12mod {
    public List<Robot> robots = new ArrayList<>();
    public Map<Point, List<Robot>> robotsFinalPositions = new HashMap<>();
    // int sizeX = 101;
    // int sizeY = 103;
    int sizeX = 11;
    int sizeY = 7;

    class Robot {
        Point location;
        Point velocity;

        public Robot(Point location, Point velocity) {
            this.location = location;
            this.velocity = velocity;
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
            System.out.println(robots);

            for (Robot robot : robots) {
                int x = (robot.location.x + robot.velocity.x * 100) % sizeX;
                int y = (robot.location.y + robot.velocity.y * 100) % sizeY;
                x = x < 0 ? sizeX + x : x;
                y = y < 0 ? sizeY + y : y;
                Point point = new Point(x, y);
                List<Robot> robotsList = robotsFinalPositions.get(point);
                if (robotsList == null)
                    robotsList = new ArrayList<>();
                robotsList.add(robot);
                robotsFinalPositions.put(point, robotsList);
            }

            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    Point point = new Point(x, y);
                    List<Robot> robotsList = robotsFinalPositions.get(point);
                    boolean robotsListEmpty = robotsList == null;
                    System.out.printf("%-2s", robotsListEmpty ? "." : "O");
                }
                System.out.println();
            }

            return 0;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
