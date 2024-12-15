package day15;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15Part2 {
    public List<List<Character>> warehouse = new ArrayList<>();
    public List<Character> movements = new ArrayList<>();
    public Point robot;

    public long processFile() {
        try {
            File file = new File("resources/day15.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty())
                    break;
                List<Character> warehouseRow = new ArrayList<>();
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char c = chars[i];
                    if (c == '#') {
                        warehouseRow.add(c);
                        warehouseRow.add(c);
                    }
                    if (c == 'O') {
                        warehouseRow.add('[');
                        warehouseRow.add(']');
                    }
                    if (c == '.') {
                        warehouseRow.add('.');
                        warehouseRow.add('.');
                    }
                    if (c == '@') {
                        warehouseRow.add('@');
                        warehouseRow.add('.');
                        robot = new Point(i * 2, warehouse.size());
                    }
                }
                warehouse.add(warehouseRow);
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] chars = line.toCharArray();
                for (char c : chars) {
                    movements.add(c);
                }
            }
            scanner.close();
            // for (int y = 0; y < warehouse.size(); y++) {
            // for (int x = 0; x < warehouse.get(0).size(); x++) {
            // System.out.print(warehouse.get(y).get(x));
            // }
            // System.out.println();
            // }
            // System.out.println(robot);

            // int counter = 0;

            for (char movement : movements) {
                // counter++;
                // if (counter >= 7)
                // break;

                List<List<Point>> swapList = new ArrayList<>();

                Point vector = getVector(movement);
                if (vector.x == 0) {
                    // System.out.println("vertically");
                    boolean canMove = checkVertically(robot, movement, swapList, 0);
                    // System.out.println("can move");
                    // System.out.println(swapList);
                    if (canMove) {
                        // System.out.println("can move vertically");
                        moveCells(swapList, vector);
                        warehouse.get(robot.y).set(robot.x, '.');
                        warehouse.get(robot.y + vector.y).set(robot.x + vector.x, '@');
                        robot.translate(vector.x, vector.y);

                    } else {
                        // System.out.println("cannot move vertically");
                    }
                } else {
                    // System.out.println("horizontally");
                    checkHorizontally(robot, movement);
                }

                for (int y = 0; y < warehouse.size(); y++) {
                    for (int x = 0; x < warehouse.get(0).size(); x++) {
                        // System.out.print(warehouse.get(y).get(x));
                    }
                    // System.out.println();
                }
            }

            long sum = 0;

            for (int x = 0; x < warehouse.get(0).size(); x++) {
                for (int y = 0; y < warehouse.size(); y++) {
                    if (warehouse.get(y).get(x) == '[') {
                        sum += 100 * y + x;
                    }
                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private Point getVector(char c) {
        if (c == '<')
            return new Point(-1, 0);
        if (c == '^')
            return new Point(0, -1);
        if (c == '>')
            return new Point(1, 0);
        if (c == 'v')
            return new Point(0, 1);
        else
            throw new Error("Movement char is invalid");
    }

    private boolean checkVertically(Point point, char movement, List<List<Point>> swapList, int depth) {
        // System.out.println();
        char currentChar = warehouse.get(point.y).get(point.x);
        // System.out.println("current char=" + currentChar);
        Point vector = getVector(movement);
        Point nextPoint = new Point(point.x + vector.x, point.y + vector.y);
        char nextChar = warehouse.get(nextPoint.y).get(nextPoint.x);
        // System.out.println("next char=" + nextChar);
        if (nextChar == '#') {
            return false;
        }
        if (nextChar == '[') {
            boolean response1 = checkVertically(nextPoint, movement, swapList, depth + 1);
            Point nextPointRight = new Point(nextPoint.x + 1, nextPoint.y);
            boolean response2 = checkVertically(nextPointRight, movement, swapList, depth + 1);
            if (swapList.size() <= depth) {
                // Ensure the list has enough elements up to the 'depth' index
                while (swapList.size() <= depth) {
                    swapList.add(new ArrayList<>()); // Add empty lists
                }
            }

            // Now 'depth' is guaranteed to exist in the list
            List<Point> points = swapList.get(depth);
            points.add(nextPoint);
            swapList.set(depth, points);
            // System.out.println("swap list=" + swapList);
            return response1 && response2;
        }
        if (nextChar == ']') {
            // System.out.println("HERERE");
            boolean response1 = checkVertically(nextPoint, movement, swapList, depth + 1);
            Point nextPointLeft = new Point(nextPoint.x - 1, nextPoint.y);
            boolean response2 = checkVertically(nextPointLeft, movement, swapList, depth + 1);
            if (swapList.size() <= depth) {
                // Ensure the list has enough elements up to the 'depth' index
                while (swapList.size() <= depth) {
                    swapList.add(new ArrayList<>()); // Add empty lists
                }
            }

            // Now 'depth' is guaranteed to exist in the list
            List<Point> points = swapList.get(depth);
            points.add(nextPointLeft);
            swapList.set(depth, points);
            // System.out.println("swap list=" + swapList);
            return response1 && response2;
        }
        if (nextChar == '.') {
            return true;
        }
        return true;
    }

    private void checkHorizontally(Point point, char movement) {
        Point vector = getVector(movement);
        Point firstEmptyCell = null;

        Point nextPoint = new Point(robot.x + vector.x, robot.y + vector.y);
        // System.out.println("next point");
        // System.out.println(nextPoint);

        boolean exit1 = false;
        while (!exit1) {
            char nextChar = warehouse.get(nextPoint.y).get(nextPoint.x);
            if (nextChar == '.') {
                // System.out.println(" found empty cell");
                firstEmptyCell = nextPoint;
                exit1 = true;
                break;
            }
            if (nextChar == '#') {
                // System.out.println("found border");
                exit1 = true;
                break;
            }
            nextPoint.translate(vector.x, vector.y);
        }

        if (firstEmptyCell == null) {
            // System.out.println("no empty cell");
            return;
        }
        // System.out.println("after");

        Point swapPointOuter = new Point(firstEmptyCell.x, firstEmptyCell.y);
        // System.out.println("outer");
        // System.out.println(swapPointOuter);
        boolean exit2 = false;

        while (!exit2) {
            Point swapPointInner = new Point(swapPointOuter.x - vector.x,
                    swapPointOuter.y - vector.y);
            // System.out.println("inner");
            // System.out.println(swapPointInner);
            char innerChar = warehouse.get(swapPointInner.y).get(swapPointInner.x);
            // System.out.println("inner char");
            // System.out.println(innerChar);
            warehouse.get(swapPointOuter.y).set(swapPointOuter.x, innerChar);
            warehouse.get(swapPointInner.y).set(swapPointInner.x, '.');
            if (innerChar == '@') {
                // System.out.println("robot found - stop");
                robot.translate(vector.x, vector.y);
                exit2 = true;
            } else {
                swapPointInner.translate(-vector.x, -vector.y);
                swapPointOuter.translate(-vector.x, -vector.y);
            }
        }
    }

    private void moveCells(List<List<Point>> swapList, Point vector) {
        for (int i = swapList.size() - 1; i >= 0; i--) {
            List<Point> points = swapList.get(i);
            for (Point point : points) {
                warehouse.get(point.y + vector.y).set(point.x, '[');
                warehouse.get(point.y).set(point.x, '.');
                warehouse.get(point.y + vector.y).set(point.x + 1, ']');
                warehouse.get(point.y).set(point.x + 1, '.');
            }
        }
    }
}
