package day20;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Day20Part2 {
    // public static int trackSize = 141;
    public static int trackSize = 15;
    public static char[][] track = new char[trackSize][trackSize];
    public static int startX;
    public static int startY;
    public static int endX;
    public static int endY;
    // public static int minPicosecondsToSave = 100;
    public static int minPicosecondsToSave = 0;
    public static int maxCheatPicoseconds = 20;
    // public static int picosecondsOriginal = 9504;
    public static int picosecondsOriginal = 84;
    public static List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)));

    public static class State implements Comparable<State> {
        int x, y, picoseconds, cheatStartX, cheatStartY, cheatEndX, cheatEndY, cheatPicosecondsUsed;
        Set<String> visited;

        public State(int x, int y, int picoseconds, int cheatPicosecondsUsed, Set<String> visited) {
            this.x = x;
            this.y = y;
            this.picoseconds = picoseconds;
            this.cheatPicosecondsUsed = 0;
            this.visited = visited;
        }

        public void setCheatStart(int x, int y) {
            this.cheatStartX = x;
            this.cheatStartY = y;
        }

        public void setCheatEnd(int x, int y) {
            this.cheatStartX = x;
            this.cheatStartY = y;
        }

        public void addToVisited(int x, int y) {
            visited.add(x + "," + y);
        }

        public boolean isVisited(int x, int y) {
            return visited.contains(x + "," + y);
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

    public static boolean isOutOfTrack(int x, int y) {
        return (x < 0 || y < 0 || x > trackSize - 1 || y > trackSize - 1);
    }

    public static boolean isWall(int x, int y) {
        return track[x][y] == '#';
    }

    public static List<State> findShortestPath() {
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(startX, startY, 0, 0, new HashSet<>()));
        int maxPicoseconds = picosecondsOriginal - minPicosecondsToSave;
        List<State> finalStates = new ArrayList<>();

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int x = current.x;
            int y = current.y;
            int picoseconds = current.picoseconds;
            int cheatPicosecondsUsed = current.cheatPicosecondsUsed;
            Set<String> visited = current.visited;
            // System.out.println(cheatPicosecondsUsed);
            // System.out.println(queue.size());

            if (picoseconds > maxPicoseconds) {
                System.out.println("Too many picoseconds");
                break;
            }

            if (x == endX && y == endY) {
                // System.out.println("Arrived");
                finalStates.add(current);
                continue;
            }

            List<State> adjacentNodes = new ArrayList<>();

            for (Point direction : directions) {
                boolean cheat = false;
                int newX = x + direction.x;
                int newY = y + direction.y;
                if (isOutOfTrack(newX, newY)) {
                    continue;
                }
                if (isWall(newX, newY)) {
                    if (cheatPicosecondsUsed + 1 > maxCheatPicoseconds) {
                        System.out.println("Too many cheat picoseconds");
                        continue;
                    } else {
                        cheat = true;
                    }
                }
                Set<String> newVisited = new HashSet<>(visited);
                newVisited.add(x + "," + y);
                State state = new State(newX, newY, picoseconds + 1, cheatPicosecondsUsed + (cheat ? 1 : 0),
                        newVisited);

                if (cheat) {
                    if (state.cheatPicosecondsUsed == 0) {
                        state.setCheatStart(newX, newY);
                    }
                    // System.out.println(state.cheatPicosecondsUsed);
                    state.cheatPicosecondsUsed++;
                    // System.out.println(state.cheatPicosecondsUsed);
                    state.setCheatEnd(newX, newY);
                }

                adjacentNodes.add(state);
            }

            for (State adjacent : adjacentNodes) {
                if (visited.contains(adjacent.x + "," + adjacent.y)) {
                    System.out.println("visited");
                    continue;
                }
                visited.add(x + "," + y);
                adjacent.visited = new HashSet<>(visited);

                queue.add(adjacent);
            }
        }

        return finalStates;
    }

    public static void run() {
        readInput();
        printTrack();
        System.out.printf("Start at (%d,%d)%n", startX, startY);
        System.out.printf("End at (%d,%d)%n", endX, endY);
        List<State> finalStates = findShortestPath();
        System.out.println(finalStates.size());

        // int times = 0;

        // for (int x = 1; x < trackSize - 1; x++) {
        // for (int y = 1; y < trackSize - 1; y++) {
        // System.out.println(x + "-" + y);
        // char cell = track[x][y];
        // if (cell == '.')
        // continue;
        // track[x][y] = '.';
        // long picoseconds = findShortestPath();
        // // System.out.println(picoseconds);
        // if (picosecondsOriginal - picoseconds >= minPicosecondsToSave) {
        // times++;
        // }
        // track[x][y] = cell;
        // }
        // }

        // System.out.println(times);
    }
}
