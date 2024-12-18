package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16Part2test {

    private static final int[][] DIRECTIONS = {
            { 0, -1 }, // UP
            { 1, 0 }, // RIGHT
            { 0, 1 }, // DOWN
            { -1, 0 } // LEFT
    };

    public static class State implements Comparable<State> {
        int x, y, direction, cost;
        List<State> parents;

        public State(int x, int y, int direction, int cost) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.cost = cost;
            this.parents = new ArrayList<>();
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.cost, other.cost);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            State state = (State) obj;
            return x == state.x && y == state.y && direction == state.direction;
        }
    }

    public static int shortestPaths(char[][] grid, int startX, int startY, int startDirection, int endX, int endY) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(new State(startX, startY, startDirection, 0));

        Map<State, Integer> distance = new HashMap<>();
        Map<State, List<State>> parentMap = new HashMap<>();

        Set<String> uniqueCells = new HashSet<>();
        int shortestCost = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            State current = pq.poll();

            if (current.x == endX && current.y == endY) {
                if (current.cost < shortestCost) {
                    shortestCost = current.cost;
                    uniqueCells.clear();
                }

                if (current.cost == shortestCost) {
                    addPathCells(current, uniqueCells);
                }

                continue;
            }

            for (int i = -1; i <= 1; i++) {
                int newDirection = (current.direction + i + 4) % 4;
                int newCost = current.cost + (i == 0 ? 1 : 1000);

                int newX = current.x + (i == 0 ? DIRECTIONS[newDirection][0] : 0);
                int newY = current.y + (i == 0 ? DIRECTIONS[newDirection][1] : 0);

                if (!isValid(grid, newX, newY))
                    continue;

                State neighbor = new State(newX, newY, newDirection, newCost);

                if (!distance.containsKey(neighbor) || newCost < distance.get(neighbor)) {
                    distance.put(neighbor, newCost);
                    parentMap.put(neighbor, new ArrayList<>(Collections.singletonList(current)));
                    pq.add(neighbor);
                } else if (newCost == distance.get(neighbor)) {
                    parentMap.get(neighbor).add(current);
                }
            }
        }

        return uniqueCells.size();
    }

    private static void addPathCells(State current, Set<String> uniqueCells) {
        Queue<State> queue = new LinkedList<>();
        queue.add(current);

        while (!queue.isEmpty()) {
            State state = queue.poll();
            uniqueCells.add(state.x + "," + state.y);
            queue.addAll(state.parents);
        }
    }

    private static boolean isValid(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != '#';
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
                        startDirection = 3;
                    } else if (grid[x][y] == 'E') {
                        endX = x;
                        endY = y;
                    }
                }
            }

            if (startX == -1 || endX == -1) {
                System.out.println("Start (S) or end (E) point not found.");
                return -1L;
            }

            return (long) shortestPaths(grid, startX, startY, startDirection, endX, endY);
        } catch (FileNotFoundException e) {
            return -1L;
        }
    }
}