package day20;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Day20Part1 {
    public static int trackSize = 141;
    public static char[][] track = new char[trackSize][trackSize];
    public static int startX;
    public static int startY;
    public static int endX;
    public static int endY;
    public static int minPicosecondsToSave = 100;
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
            File file = new File("resources/day20.txt");
            Scanner scanner = new Scanner(file);
            int x = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                for (int y = 0; y < charArray.length; y++) {
                    char c = charArray[y];
                    track[x][y] = c;
                    if (c == 'S') {
                        startX = x;
                        startY = y;
                    }
                    if (c == 'E') {
                        endX = x;
                        endY = y;
                    }
                }
                x++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Error("FileNotFoundException");
        }
    }

    public static void printTrack() {
        for (int x = 0; x < trackSize; x++) {
            for (int y = 0; y < trackSize; y++) {
                System.out.print(track[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isArrived(int x, int y) {
        return x == endX && y == endY;
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x <= trackSize && y <= trackSize && track[x][y] != '#';
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
        System.out.printf("Start at (%d,%d)%n", startX, startY);
        System.out.printf("End at (%d,%d)%n", endX, endY);
        long picosecondsOriginal = findShortestPath();
        System.out.println(picosecondsOriginal);

        int times = 0;

        for (int x = 1; x < trackSize - 1; x++) {
            for (int y = 1; y < trackSize - 1; y++) {
                System.out.println(x + "-" + y);
                char cell = track[x][y];
                if (cell == '.')
                    continue;
                track[x][y] = '.';
                long picoseconds = findShortestPath();
                // System.out.println(picoseconds);
                if (picosecondsOriginal - picoseconds >= minPicosecondsToSave) {
                    times++;
                }
                track[x][y] = cell;
            }
        }

        System.out.println(times);
    }
}
