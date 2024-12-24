package day20;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Day20Part2b {
    // public static int trackSize = 141;
    public static int trackSize = 15;
    public static char[][] track = new char[trackSize][trackSize];
    public static int startX;
    public static int startY;
    public static int endX;
    public static int endY;
    // public static int minPicosecondsToSave = 100;
    // public static int originalPicoseconds = 9504;
    public static int originalPicoseconds = 84;
    public static int minPicosecondsToSave = 76;
    public static int maxCheatPicoseconds = 20;
    public static List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)));

    public static enum CheatingStatus {
        NOT_STARTED,
        IN_PROGRESS,
        FINISHED
    }

    public static class State implements Comparable<State> {
        int x, y, picoseconds, cheatPicoseconds, startCheatX, startCheatY, endCheatX, endCheatY;
        Set<String> visited;
        CheatingStatus cheatingStatus;

        public State(int x, int y, int picoseconds, Set<String> visited, int cheatPicoseconds,
                CheatingStatus cheatingStatus) {
            this.x = x;
            this.y = y;
            this.picoseconds = picoseconds;
            this.visited = visited;
            this.cheatPicoseconds = cheatPicoseconds;
            this.cheatingStatus = cheatingStatus;
        }

        public void setStateCheat(int x, int y) {
            this.startCheatX = x;
            this.startCheatY = y;
        }

        public void setEndCheat(int x, int y) {
            this.endCheatX = x;
            this.endCheatY = y;
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
        queue.add(new State(startX, startY, 0, new HashSet<>(), 0, CheatingStatus.NOT_STARTED));
        List<State> finalStates = new ArrayList<>();
        int max = originalPicoseconds - minPicosecondsToSave;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int x = current.x;
            int y = current.y;
            int picoseconds = current.picoseconds;
            Set<String> visited = current.visited;
            int cheatPicoseconds = current.cheatPicoseconds;
            CheatingStatus cheatingStatus = current.cheatingStatus;

            System.out.println(picoseconds);

            if (Math.abs(x - endX) + Math.abs(y - endY) + picoseconds > max) {
                continue;
            }

            if (x == endX && y == endY) {
                System.out.println(picoseconds);
                finalStates.add(current);
                continue;
            }

            for (Point direction : directions) {
                Point next = new Point(x + direction.x, y + direction.y);
                boolean cheat = false;

                String stateKey = next.x + "," + next.y;
                if (visited.contains(stateKey)) {
                    continue;
                }
                visited.add(stateKey);

                if (isOutOfTrack(next.x, next.y)) {
                    continue;
                }

                CheatingStatus newCheatingStatus = cheatingStatus;
                boolean setStartCheat = false;

                if (isWall(next.x, next.y)) {
                    if (cheatingStatus == CheatingStatus.FINISHED) {
                        continue;
                    }
                    if (cheatPicoseconds >= maxCheatPicoseconds) {
                        continue;
                    }
                    if (cheatingStatus == CheatingStatus.NOT_STARTED) {
                        newCheatingStatus = CheatingStatus.IN_PROGRESS;
                        setStartCheat = true;
                    }
                    cheat = true;
                } else {
                    if (cheatingStatus == CheatingStatus.IN_PROGRESS) {
                        newCheatingStatus = CheatingStatus.FINISHED;
                    }
                }

                State state = new State(next.x, next.y, picoseconds + 1, new HashSet<>(visited),
                        cheat ? cheatPicoseconds + 1 : cheatPicoseconds, newCheatingStatus);

                if (setStartCheat) {
                    System.out.println("start");
                    state.setStateCheat(next.x, next.y);
                    System.out.println("///");
                    System.out.println(state.startCheatX);
                }

                if (cheat) {
                    System.out.println("end");
                    state.setEndCheat(next.x, next.y);
                }

                queue.add(state);
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
        System.out.println("Number of paths: " + finalStates.size());

        for (State finalState : finalStates) {
            // System.out.println();
            // System.out.println(finalState.startCheatX + "-" + finalState.startCheatY);
            // System.out.println(finalState.endCheatX + "-" + finalState.endCheatY);
        }

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
