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
    public static int minPicosecondsToSave = 72;
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
        int x, y, picoseconds, cheatPicoseconds, startCheatX, startCheatY, endCheatX, endCheatY, endCheatStandbyX,
                endCheatStandbyY;
        Set<String> visited;

        public State(int x, int y, int picoseconds, Set<String> visited, int cheatPicoseconds,
                int startCheatX, int startCheatY, int endCheatX, int endCheatY, int endCheatStandbyX,
                int endCheatStandbyY) {
            this.x = x;
            this.y = y;
            this.picoseconds = picoseconds;
            this.visited = visited;
            this.cheatPicoseconds = cheatPicoseconds;
            this.startCheatX = startCheatX;
            this.startCheatY = startCheatY;
            this.endCheatX = endCheatX;
            this.endCheatY = endCheatY;
            this.endCheatStandbyX = endCheatStandbyX;
            this.endCheatStandbyY = endCheatStandbyY;
        }

        public void setStateCheat(int x, int y) {
            this.startCheatX = x;
            this.startCheatY = y;
        }

        public void setEndCheat(int x, int y) {
            System.out.println("setting end cheat" + x + "-" + y);
            this.endCheatX = x;
            this.endCheatY = y;
            System.out.println(this.endCheatX + "-" + this.endCheatY);
        }

        public String getVisitedKey() {
            return x + "," + y + "," + startCheatX + "," + startCheatY + "," + endCheatX + ","
                    + endCheatY + "," + picoseconds;
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
        State startingState = new State(startX, startY, 0, new HashSet<>(), 0, -1,
                -1, -1, -1, -1, -1);
        queue.add(startingState);
        List<State> finalStates = new ArrayList<>();
        int max = originalPicoseconds - minPicosecondsToSave;
        Set<String> visitedGeneral = new HashSet<>();

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int x = current.x;
            int y = current.y;
            int picoseconds = current.picoseconds;
            Set<String> visited = current.visited;
            int cheatPicoseconds = current.cheatPicoseconds;
            String visitedGeneralKey = current.getVisitedKey();
            int startCheatX = current.startCheatX;
            int startCheatY = current.startCheatY;
            int endCheatX = current.endCheatX;
            int endCheatY = current.endCheatY;
            int endCheatStandbyX = current.endCheatStandbyX;
            int endCheatStandbyY = current.endCheatStandbyY;
            // System.out.println();

            // if (!(x == 3 && y == 1 || x == 4 && y == 1 || x == 5 && y == 1 || x == 6 && y
            // == 1 || x == 7 && y == 1
            // || x == 7 && y == 2
            // || x == 7 && y == 3 || x == 7 && y == 4 || x == 7 && y == 5)) {
            // continue;
            // }

            if (visitedGeneral.contains(visitedGeneralKey)) {
                if (current.startCheatX > -1 && current.endCheatX > -1) {
                    System.out.println("KKKKK");
                    // continue;
                }
            }

            visitedGeneral.add(visitedGeneralKey);

            if (Math.abs(x - endX) + Math.abs(y - endY) + picoseconds > max) {
                continue;
            }

            if (x == endX && y == endY) {
                current.endCheatX = current.endCheatStandbyX;
                current.endCheatY = current.endCheatStandbyY;
                System.out.println("Arrived with " + picoseconds + " picoseconds.");
                System.out.println("End cheat=" + current.endCheatX + "," + current.endCheatY);
                finalStates.add(current);
                continue;
            }

            for (Point direction : directions) {
                Point next = new Point(x + direction.x, y + direction.y);
                boolean cheat = false;

                String stateKey = next.x + "," + next.y;
                if (visited.contains(stateKey)) {
                    // continue;
                }
                visited.add(stateKey);

                if (isOutOfTrack(next.x, next.y)) {
                    continue;
                }

                int nextStartCheatX = startCheatX;
                int nextStartCheatY = startCheatY;
                int nextEndCheatX = endCheatX;
                int nextEndCheatY = endCheatY;
                int nextEndCheatStandbyX = endCheatStandbyX;
                int nextEndCheatStandbyY = endCheatStandbyY;

                if (cheatPicoseconds > 0 && cheatPicoseconds <= maxCheatPicoseconds) {
                    cheat = true;
                }

                if (isWall(next.x, next.y)) {
                    if (cheatPicoseconds == maxCheatPicoseconds) {
                        continue;
                    }
                    if (cheatPicoseconds == 0) {
                        nextStartCheatX = x;
                        nextStartCheatY = y;
                    }
                    cheat = true;
                } else {
                    if (cheatPicoseconds > 0 && cheatPicoseconds <= maxCheatPicoseconds
                            && isWall(x, y)) {
                        System.out.println("///");
                        System.out.println("current wall=" + x + "," + y);
                        System.out.println("next end cheat=" + next.x + "," + next.y);
                        nextEndCheatStandbyX = next.x;
                        nextEndCheatStandbyY = next.y;
                    }
                }

                State state = new State(next.x, next.y, picoseconds + 1, new HashSet<>(visited),
                        cheat ? cheatPicoseconds + 1 : cheatPicoseconds,
                        nextStartCheatX,
                        nextStartCheatY,
                        nextEndCheatX,
                        nextEndCheatY, nextEndCheatStandbyX, nextEndCheatStandbyY);

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

        Map<Integer, Set<String>> myMap = new HashMap<>();
        Set<String> generalSet = new HashSet<>();

        for (State finalState : finalStates) {
            String key = finalState.startCheatX + "," + finalState.startCheatY + "," + finalState.endCheatX + ","
                    + finalState.endCheatY;

            if (generalSet.contains(key)) {
                continue;
            } else {
                generalSet.add(key);
            }

            Set<String> mySet = myMap.getOrDefault(finalState.picoseconds, new HashSet<>());

            mySet.add(key);

            myMap.put(finalState.picoseconds, mySet);
        }

        System.out.println(myMap);

        long sum = 0;
        for (int key : myMap.keySet()) {
            sum += myMap.get(key).size();
        }
        System.out.println(sum);
    }
}
