package day18;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Day18Part2 {
    public static List<List<Integer>> memory = new ArrayList<>();
    public static List<List<Integer>> fallingBytes = new ArrayList<>();
    // public static int memorySize = 71;
    public static int memorySize = 7;
    // public static int bytesCount = 1024;
    public static int bytesCount = 12;
    public static int startX = 0;
    public static int startY = 0;
    public static int endX = memorySize - 1;
    public static int endY = memorySize - 1;
    public static List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)));

    public static class State implements Comparable<State> {
        int x, y, steps;
        State parent;

        public State(int x, int y, int steps, State parent) {
            this.x = x;
            this.y = y;
            this.steps = steps;
            this.parent = parent;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.steps, other.steps);
        }
    }

    public static void readInput() {
        try {
            File file = new File("resources/day18test.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split(",");
                List<Integer> fallingByte = new ArrayList<>();
                fallingByte.add(Integer.parseInt(lineArray[0]));
                fallingByte.add(Integer.parseInt(lineArray[1]));
                fallingBytes.add(fallingByte);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Error("FileNotFoundException");
        }
    }

    public static void printFallingBytes() {
        for (List<Integer> b : fallingBytes) {
            System.out.println(b);
        }
        System.out.println();
    }

    public static void createMemory() {
        for (int x = 0; x < memorySize; x++) {
            List<Integer> row = new ArrayList<>();
            for (int y = 0; y < memorySize; y++) {
                row.add(-1);
            }
            memory.add(row);
        }

        for (int time = 0; time < bytesCount; time++) {
            List<Integer> fallingByte = fallingBytes.get(time);
            int row = fallingByte.get(0);
            int col = fallingByte.get(1);
            memory.get(row).set(col, time);
        }
    }

    public static void printMemory() {
        for (int x = 0; x < memorySize; x++) {
            for (int y = 0; y < memorySize; y++) {
                String paddedNumber = String.format("%3d ", memory.get(y).get(x));
                System.out.print(paddedNumber);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isArrived(int x, int y) {
        return x == endX && y == endY;
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x <= endX && y <= endY && memory.get(x).get(y) == -1;
    }

    public static List<State> constructPath(State endState) {
        State current = endState;
        List<State> path = new ArrayList<>();

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);

        return path;
    }

    public static List<State> findShortestPath() {
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(startX, startY, 0, null));
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int x = current.x;
            int y = current.y;
            int steps = current.steps;

            if (x == endX && y == endY) {
                System.out.println(current.steps);
                return constructPath(current);
            }

            String stateKey = x + "," + y;

            if (visited.contains(stateKey)) {
                continue;
            }
            visited.add(stateKey);

            for (Point direction : directions) {
                Point next = new Point(x + direction.x, y + direction.y);
                if (isValid(next.x, next.y)) {
                    queue.add(new State(next.x, next.y, steps + 1, current));
                }
            }
        }

        return new ArrayList<>();
    }

    public static void printPath(List<State> path) {
        for (State s : path) {
            System.out.println(s.x + "-" + s.y);
        }
    }

    public static List<Integer> findFirstProblematicByte(List<State> path) {
        // Set<String> fallingBytesSet = new HashSet<>();
        // for (List<Integer> fallingByte : fallingBytes) {
        // fallingBytesSet.add(fallingByte.get(0) + "-" + fallingByte.get(1));
        // }

        // System.out.println(fallingBytesSet);

        // for (State s : path) {
        // if (fallingBytesSet.contains(s.x + "-" + s.y)) {
        // List<Integer> fallingByte = new ArrayList<>();
        // fallingByte.add(s.x);
        // fallingByte.add(s.y);
        // System.out.println(fallingByte);
        // return fallingByte;
        // }
        // }

        Set<String> pathSet = new HashSet<>();
        for (State state : path) {
            String key = state.x + "-" + state.y;
            System.out.println(key);
            pathSet.add(key);
        }
        // System.out.println(pathSet);

        for (int i = bytesCount; i < fallingBytes.size(); i++) {
            List<Integer> nextFallingByte = fallingBytes.get(i);
            System.out.println("next falling byte");
            System.out.println(nextFallingByte);
            if (pathSet.contains(nextFallingByte.get(0) + "-" + nextFallingByte.get(1))) {
                // System.out.println(nextFallingByte);
                return nextFallingByte;
            }
        }
        return new ArrayList<>();
    }

    public static void run() {
        readInput();
        // printFallingBytes();
        createMemory();
        // printMemory();
        List<State> path = findShortestPath();
        // printPath(path);
        findFirstProblematicByte(path);
    }
}