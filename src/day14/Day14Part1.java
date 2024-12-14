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

public class Day14Part1 {
    public List<Robot> robots = new ArrayList<>();
    public Map<Point, List<Robot>> robotsFinalPositions = new HashMap<>();
    int sizeX = 101;
    int sizeY = 103;

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

            System.out.println(robotsFinalPositions);

            int quadrant1 = 0;
            int quadrant2 = 0;
            int quadrant3 = 0;
            int quadrant4 = 0;

            for (Map.Entry<Point, List<Robot>> entry : robotsFinalPositions.entrySet()) {
                Point position = entry.getKey();
                System.out.println(position);
                List<Robot> robots = entry.getValue();
                int count = robots.size();

                if (position.x < sizeX / 2 && position.y < sizeY / 2) {
                    System.out.println("111");
                    System.out.println(count);
                    quadrant1 += count;
                }
                if (position.x < sizeX / 2 && position.y >= sizeY - sizeY / 2) {
                    System.out.println("222");
                    System.out.println(count);
                    quadrant2 += count;
                }
                if (position.x >= sizeX - sizeX / 2 && position.y < sizeY / 2) {
                    System.out.println("333");
                    System.out.println(count);
                    quadrant3 += count;
                }
                if (position.x >= sizeX - sizeX / 2 && position.y >= sizeY - sizeY / 2) {
                    System.out.println("444");
                    System.out.println(count);
                    quadrant4 += count;
                }
            }
            System.out.println(quadrant1);
            System.out.println(quadrant2);
            System.out.println(quadrant3);
            System.out.println(quadrant4);

            return quadrant1 * quadrant2 * quadrant3 * quadrant4;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
