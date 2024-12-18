package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16Part1 {

    private static final int[][] DIRECTIONS = {
            { 0, -1 },
            { 1, 0 },
            { 0, 1 },
            { -1, 0 }
    };

    public static class State implements Comparable<State> {
        int x, y, direction, cost;

        public State(int x, int y, int direction, int cost) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.cost = cost;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static int shortestPath(char[][] grid, int startX, int startY, int startDirection, int endX, int endY) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(startX, startY, startDirection, 0));
        Set<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            State current = pq.poll();
            int x = current.x;
            int y = current.y;
            int direction = current.direction;
            int cost = current.cost;
            System.out.println();
            System.out.println("current=" + current.x + "-" + current.y);
            System.out.println("direction=" + direction);
            System.out.println("cost=" + cost);

            if (x == endX && y == endY) {
                return cost;
            }

            String stateKey = x + "," + y + "," + direction;
            if (visited.contains(stateKey)) {
                continue;
            }
            visited.add(stateKey);

            int newX = x + DIRECTIONS[direction][0];
            int newY = y + DIRECTIONS[direction][1];
            if (isValid(grid, newX, newY)) {
                pq.add(new State(newX, newY, direction, cost + 1));
            }

            int leftDirection = (direction + 3) % 4;
            pq.add(new State(x, y, leftDirection, cost + 1000));

            int rightDirection = (direction + 1) % 4;
            pq.add(new State(x, y, rightDirection, cost + 1000));
        }

        return -1;
    }

    private static boolean isValid(char[][] grid, int x, int y) {
        return grid[x][y] != '#';
    }

    public static char[][] parseMap(Scanner scanner) {
        List<String> mapLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            mapLines.add(line);
        }

        int rows = mapLines.size();
        int cols = mapLines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = mapLines.get(i).toCharArray();
        }
        return grid;
    }

    public static Long processFile() {
        try {
            File file = new File("resources/day16test3.txt");
            Scanner scanner = new Scanner(file);

            char[][] grid = parseMap(scanner);

            int startX = -1, startY = -1, startDirection = -1;
            int endX = -1, endY = -1;

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    if (grid[x][y] == 'S') {
                        startX = x;
                        startY = y;
                        startDirection = 2;
                    } else if (grid[x][y] == 'E') {
                        endX = x;
                        endY = y;
                    }
                }
            }

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    System.out.print(grid[x][y]);
                }
                System.out.println();
            }
            // System.out.println(startX);
            // System.out.println(startY);
            // System.out.println(endX);
            // System.out.println(endY);

            if (startX == -1 || endX == -1) {
                System.out.println("Start (S) or end (E) point not found.");
                return -1L;
            }

            long result = shortestPath(grid, startX, startY, startDirection, endX, endY);
            if (result != -1) {
                System.out.println("The shortest path cost is: " + result);
            } else {
                System.out.println("No path exists.");
            }
            return result;

        } catch (FileNotFoundException e) {
            return -1L;
        }
    }
}
