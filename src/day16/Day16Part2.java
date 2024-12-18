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

    public static int shortestPath(char[][] grid, int startX, int startY, int startDirection, int endX, int endY) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        State startingState = new State(startX, startY, startDirection, 0);
        pq.add(startingState);
        Map<State, Integer> visitedWithCostFromStart = new HashMap<>();
        visitedWithCostFromStart.put(startingState, 0);
        List<State> finalStates = new ArrayList<>();
        int shortestCost = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            State current = pq.poll();
            int x = current.x;
            int y = current.y;
            int direction = current.direction;
            int cost = current.cost;

            if (cost > shortestCost)
                break;

            if (x == endX && y == endY) {
                if (cost < shortestCost) {
                    shortestCost = cost;
                    finalStates.clear();
                }
                finalStates.add(current);
                continue;
            }

            List<State> adjacentNodes = new ArrayList<>();

            int newX = x + DIRECTIONS[direction][0];
            int newY = y + DIRECTIONS[direction][1];
            if (isValid(grid, newX, newY)) {
                State newStateForward = new State(newX, newY, direction, cost + 1);
                adjacentNodes.add(newStateForward);
            }

            int leftDirection = (direction + 3) % 4;
            State newStateLeft = new State(x, y, leftDirection, cost + 1000);
            adjacentNodes.add(newStateLeft);

            int rightDirection = (direction + 1) % 4;
            State newStateRight = new State(x, y, rightDirection, cost + 1000);
            adjacentNodes.add(newStateRight);

            for (State adjacent : adjacentNodes) {
                if (visitedWithCostFromStart.containsKey(adjacent)) {
                    Integer adjacentCost = visitedWithCostFromStart.get(adjacent);
                    if (cost > adjacentCost) {
                        continue;
                    }
                    if (cost < adjacentCost) {
                        adjacent.parents = new ArrayList<>();
                        adjacent.parents.add(current);
                        continue;
                    }
                    if (cost == adjacentCost) {
                        adjacent.parents.add(current);
                    }
                    continue;
                } else {
                    visitedWithCostFromStart.put(current, adjacent.cost);
                    pq.add(adjacent);
                    adjacent.parents.add(current);
                }
            }
        }

        Set<List<Integer>> mergedSet = new HashSet<>();
        for (State finalState : finalStates) {
            Set<List<Integer>> mySet = reconstructPath(finalState, new HashSet<>());
            mergedSet.addAll(mySet);
        }

        return mergedSet.size();
    }

    public static Set<List<Integer>> reconstructPath(State state, Set<List<Integer>> cells) {
        cells.add(Arrays.asList(state.x, state.y));
        if (state.parents.isEmpty()) {
            return cells;
        }

        for (State parent : state.parents) {
            cells.add(Arrays.asList(parent.x, parent.y));
            reconstructPath(parent, cells);
        }

        return cells;
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
            File file = new File("resources/day16.txt");
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

            if (startX == -1 || endX == -1) {
                System.out.println("Start (S) or end (E) point not found.");
                return -1L;
            }

            long result = shortestPath(grid, startX, startY, startDirection, endX, endY);
            return result;

        } catch (FileNotFoundException e) {
            return -1L;
        }
    }
}
