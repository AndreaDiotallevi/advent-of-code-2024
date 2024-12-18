package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16Part2 {

    private static final int[][] DIRECTIONS = {
            { 0, -1 },
            { 1, 0 },
            { 0, 1 },
            { -1, 0 }
    };

    public static class State implements Comparable<State> {
        int x, y, direction, cost;
        State parent;

        public State(int x, int y, int direction, int cost, State parent) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.cost = cost;
            this.parent = parent;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static List<List<int[]>> shortestPaths(char[][] grid, int startX, int startY, int startDirection, int endX,
            int endY) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(startX, startY, startDirection, 0, null));
        Set<String> visited = new HashSet<>();
        Map<String, Integer> distance = new HashMap<>();
        List<List<int[]>> allPaths = new ArrayList<>();

        while (!pq.isEmpty()) {
            State current = pq.poll();
            int x = current.x;
            int y = current.y;
            int direction = current.direction;
            int cost = current.cost;

            if (x == endX && y == endY) {
                allPaths.add(reconstructPath(current));
                continue;
            }

            String stateKey = x + "," + y + "," + direction;
            if (visited.contains(stateKey)) {
                continue;
            }
            visited.add(stateKey);

            int newX = x + DIRECTIONS[direction][0];
            int newY = y + DIRECTIONS[direction][1];
            if (isValid(grid, newX, newY)) {
                int newCost = cost + 1;
                String newStateKey = newX + "," + newY + "," + direction;
                if (!distance.containsKey(newStateKey) || newCost < distance.get(newStateKey)) {
                    pq.add(new State(newX, newY, direction, newCost, current));
                    distance.put(newStateKey, newCost);
                }
            }

            int leftDirection = (direction + 3) % 4;
            pq.add(new State(x, y, leftDirection, cost + 1000, current));

            int rightDirection = (direction + 1) % 4;
            pq.add(new State(x, y, rightDirection, cost + 1000, current));
        }

        return allPaths;
    }

    public static List<int[]> reconstructPath(State endState) {
        List<int[]> path = new ArrayList<>();
        State current = endState;

        while (current != null) {
            path.add(new int[] { current.x, current.y });
            current = current.parent;
        }

        return path;
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
                    // System.out.print(grid[y][x]);
                }
                System.out.println();
            }

            if (startX == -1 || endX == -1) {
                System.out.println("Start (S) or end (E) point not found.");
                return -1L;
            }

            List<List<int[]>> allPaths = shortestPaths(grid, startX, startY, startDirection, endX, endY);
            System.out.println(allPaths.size());
            Set<String> uniqueCells = new HashSet<>();

            for (List<int[]> path : allPaths) {
                System.out.println();
                for (int[] coordinates : path) {
                    System.out.println(Arrays.toString(coordinates));
                    String cellKey = coordinates[0] + "--" + coordinates[1];
                    uniqueCells.add(cellKey);
                }
                System.out.println();
            }
            System.out.println(uniqueCells);
            return (long) uniqueCells.size();

        } catch (FileNotFoundException e) {
            return -1L;
        }
    }
}
