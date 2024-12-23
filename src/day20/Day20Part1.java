package day20;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Day20Part1 {
    public static List<List<Character>> track = new ArrayList<>();
    public static int memorySize = 15;
    public static int startX;
    public static int startY;
    public static int endX;
    public static int endY;
    public static List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)));

    public static class State implements Comparable<State> {
        int x, y, picoseconds;

        public State(int x, int y, int picoseconds) {
            this.x = x;
            this.y = y;
            this.picoseconds = picoseconds;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.picoseconds, other.picoseconds);
        }
    }

    public static void readInput() {
        try {
            File file = new File("resources/day20test.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                List<Character> row = new ArrayList<>();
                for (char c : charArray) {
                    row.add(c);
                }
                track.add(row);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Error("FileNotFoundException");
        }
    }

    public static void printTrack() {
        for (int x = 0; x < memorySize; x++) {
            for (int y = 0; y < memorySize; y++) {
                System.out.print(track.get(x).get(y));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isArrived(int x, int y) {
        return x == endX && y == endY;
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x <= endX && y <= endY && track.get(x).get(y) != '#';
    }

    public static long findShortestPath() {
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(startX, startY, 0));
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int x = current.x;
            int y = current.y;
            int picoseconds = current.picoseconds;

            if (x == endX && y == endY) {
                return picoseconds;
            }

            String stateKey = x + "," + y;

            if (visited.contains(stateKey)) {
                continue;
            }
            visited.add(stateKey);

            for (Point direction : directions) {
                Point next = new Point(x + direction.x, y + direction.y);
                if (isValid(next.x, next.y)) {
                    queue.add(new State(next.x, next.y, picoseconds + 1));
                }
            }
        }
        return -1;
    }

    public static void run() {
        readInput();
        printTrack();
        long result = findShortestPath();
        System.out.println(result);
    }
}
