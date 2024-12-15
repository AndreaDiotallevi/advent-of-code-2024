package day15;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day15Part2 {
    public List<List<Character>> warehouse = new ArrayList<>();
    public List<Character> movements = new ArrayList<>();
    public Point robot;

    public long processFile() {
        try {
            File file = new File("resources/day15test.txt");
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
            System.out.println(warehouse);
            System.out.println(robot);

            // for (char movement : movements) {
            // attemptToMove(robot, movement);
            // }
            // System.out.println(warehouse);
            // System.out.println(robot);

            long sum = 0;

            // for (int x = 0; x < warehouse.get(0).size(); x++) {
            // for (int y = 0; y < warehouse.size(); y++) {
            // if (warehouse.get(y).get(x) == 'O') {
            // sum += 100 * y + x;
            // }
            // }
            // }

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

    private void attemptToMove(Point robot, char movement) {
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
            Point swapPointInner = new Point(swapPointOuter.x - vector.x, swapPointOuter.y - vector.y);
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
}
