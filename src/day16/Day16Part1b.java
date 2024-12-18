package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16Part1b {

    private static final int[][] DIRECTIONS = {
            { 0, -1 },
            { 1, 0 },
            { 0, 1 },
            { -1, 0 }
    };

    public static class State implements Comparable<State> {
        int x, y, direction, cost;
        List<int[]> path;
        State parent;

        public State(int x, int y, int direction, int cost, List<int[]> path, State parent) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.cost = cost;
            this.path = path;
            this.parent = parent;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static class CompletedPath {
        List<int[]> path;
        int score;

        public CompletedPath(List<int[]> path, int score) {
            this.path = path;
            this.score = score;
        }
    }

    public static long shortestPaths(char[][] grid, int startX, int startY, int startDirection, int endX,
            int endY) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(startX, startY, startDirection, 0, new ArrayList<>(), null));
        Set<String> visited = new HashSet<>();
        List<CompletedPath> completedPaths = new ArrayList<>();

        while (!pq.isEmpty()) {
            State current = pq.poll();
            State parent = current.parent;
            int x = current.x;
            int y = current.y;
            int direction = current.direction;
            int cost = current.cost;
            List<int[]> path = current.path;
            // System.out.println();
            // System.out.println(current.x + "-" + current.y);
            // System.out.println("direction=" + direction);
            // for (int[] c : path) {
            // System.out.println(Arrays.toString(c));
            // }

            if (x == endX && y == endY) {
                System.out.println();
                System.out.println("END");
                path.add(new int[] { current.x, current.y });
                for (int[] c : path) {
                    System.out.println(Arrays.toString(c));
                }
                CompletedPath completedPath = new CompletedPath(path, cost);
                completedPaths.add(completedPath);
                continue;
            }

            int parentX = parent == null ? -1 : parent.x;
            int parentY = parent == null ? -1 : parent.y;
            int parentDirection = parent == null ? -1 : parent.direction;

            String stateKey = x + "," + y + "," + direction + "," + parentX + "-" + parentY + "-" + parentDirection;
            if (visited.contains(stateKey)) {
                // System.out.println("VISITED");
                continue;
            }
            visited.add(stateKey);

            int newX = x + DIRECTIONS[direction][0];
            int newY = y + DIRECTIONS[direction][1];
            if (isValid(grid, newX, newY)) {
                // System.out.println("straight");
                List<int[]> newPath1 = new ArrayList<>(path);
                newPath1.add(new int[] { current.x, current.y });
                pq.add(new State(newX, newY, direction, cost + 1, newPath1, current));
            } else {
                // System.out.println("straight not valid #");
            }

            // System.out.println("left");
            int leftDirection = (direction + 3) % 4;
            List<int[]> newPath2 = new ArrayList<>(path);
            newPath2.add(new int[] { current.x, current.y });
            pq.add(new State(x, y, leftDirection, cost + 1000, newPath2, current));

            // System.out.println("right");
            int rightDirection = (direction + 1) % 4;
            List<int[]> newPath3 = new ArrayList<>(path);
            newPath3.add(new int[] { current.x, current.y });
            pq.add(new State(x, y, rightDirection, cost + 1000, newPath3, current));
        }

        Integer minScore = Integer.MAX_VALUE;
        for (CompletedPath path : completedPaths) {
            if (path.score < minScore) {
                minScore = path.score;
            }
        }
        System.out.println("score=" + minScore);
        // System.out.println("size");
        System.out.println(completedPaths.size());

        Set<int[]> finalSet = new HashSet<>();
        for (CompletedPath path : completedPaths) {
            if (path.score == minScore) {
                for (int[] c : path.path) {
                    finalSet.add(c);
                }
            }
        }
        // System.out.println("size");
        // System.out.println(finalSet.toString());
        System.out.println(finalSet.size());
        return finalSet.size();
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
            File file = new File("resources/day16test.txt");
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

            Long result = shortestPaths(grid, startX, startY, startDirection, endX, endY);
            System.out.println(result);
            // if (result != -1) {
            // System.out.println("The shortest path cost is: " + result);
            // } else {
            // System.out.println("No path exists.");
            // }
            // return result;
            return 0L;

        } catch (FileNotFoundException e) {
            return -1L;
        }
    }
}
