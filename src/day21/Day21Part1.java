package day21;

import java.awt.Point;
// import java.io.*;
import java.util.*;

public class Day21Part1 {
    public static List<Keypad> keypads = new ArrayList<>();
    public static List<Point> directions = new ArrayList<>(Arrays.asList(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)));
    public static Map<Point, Character> directionsToChars = Map.of(
            new Point(0, 1), 'v',
            new Point(1, 0), '>',
            new Point(0, -1), '^',
            new Point(-1, 0), '<');
    public static Map<Character, Integer> priorityMap1 = Map.of(
            '>', 0,
            '<', 0,
            '^', 1,
            'v', 1,
            'A', 2);
    public static Map<Character, Integer> priorityMap2 = Map.of(
            '>', 1,
            '<', 1,
            '^', 0,
            'v', 0,
            'A', 2);

    public static class State implements Comparable<State> {
        int steps;
        Point point;
        Point direction;
        State previous;

        public State(Point point, int steps, Point direction, State previous) {
            this.point = point;
            this.steps = steps;
            this.direction = direction;
            this.previous = previous;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.steps, other.steps);
        }
    }

    public static class Keypad {
        public Map<Point, Character> buttonsToChars = new HashMap<>();
        public Map<Character, Point> charsToButtons = new HashMap<>();
        public Point current;

        public Keypad(Map<Point, Character> buttonsToChars, Map<Character, Point> charsToButtons, Point current) {
            this.buttonsToChars = buttonsToChars;
            this.charsToButtons = charsToButtons;
            this.current = current;
        }

        public List<Character> findShortestPath(char end) {
            Queue<State> queue = new PriorityQueue<>();
            Point startPoint = charsToButtons.get(buttonsToChars.get(current));
            queue.add(new State(startPoint, 0, null, null));
            Set<Point> visited = new HashSet<>();

            while (!queue.isEmpty()) {
                State current = queue.poll();
                if (buttonsToChars.get(current.point) == end) {
                    this.current = charsToButtons.get(end);
                    List<Character> path = backtrackDirections(current);
                    return path;
                }
                if (visited.contains(current.point)) {
                    continue;
                }
                visited.add(current.point);
                for (Point direction : directions) {
                    Point next = new Point(current.point.x + direction.x, current.point.y + direction.y);
                    if (isValid(next)) {
                        queue.add(new State(next, current.steps + 1, direction, current));
                    }
                }
            }

            return new ArrayList<>();
        }

        public List<Character> backtrackDirections(State endState) {
            State current = endState;
            List<Character> path = new ArrayList<>();
            while (current.direction != null) {
                char c = directionsToChars.get(current.direction);
                path.add(c);
                current = current.previous;
            }
            Collections.reverse(path);
            path.add('A');
            return path;
        }

        public List<Character> orderBest1(List<Character> path) {
            Comparator<Character> customComparator = (c1, c2) -> {
                int priority1 = priorityMap1.get(c1);
                int priority2 = priorityMap1.get(c2);

                return Integer.compare(priority1, priority2);
            };

            Collections.sort(path, customComparator);

            return path;
        }

        public List<Character> orderBest2(List<Character> path) {
            Comparator<Character> customComparator = (c1, c2) -> {
                int priority1 = priorityMap2.get(c1);
                int priority2 = priorityMap2.get(c2);

                return Integer.compare(priority1, priority2);
            };

            Collections.sort(path, customComparator);

            return path;
        }

        public List<Character> translatePathToChars() {
            return new ArrayList<>();
        }

        public boolean isValid(Point point) {
            return buttonsToChars.containsKey(point);
        }
    }

    public static Map<Character, Point> invertMap(Map<Point, Character> buttonsToChars) {
        Map<Character, Point> charsToButtons = new HashMap<>();
        for (Map.Entry<Point, Character> entry : buttonsToChars.entrySet()) {
            charsToButtons.put(entry.getValue(), entry.getKey());
        }
        return charsToButtons;
    }

    public static void setup() {
        Map<Point, Character> buttonsToChars = new HashMap<>();
        buttonsToChars.put(new Point(0, 0), '7');
        buttonsToChars.put(new Point(1, 0), '8');
        buttonsToChars.put(new Point(2, 0), '9');
        buttonsToChars.put(new Point(0, 1), '4');
        buttonsToChars.put(new Point(1, 1), '5');
        buttonsToChars.put(new Point(2, 1), '6');
        buttonsToChars.put(new Point(0, 2), '1');
        buttonsToChars.put(new Point(1, 2), '2');
        buttonsToChars.put(new Point(2, 2), '3');
        buttonsToChars.put(new Point(1, 3), '0');
        buttonsToChars.put(new Point(2, 3), 'A');
        Keypad numericKeypad = new Keypad(buttonsToChars, invertMap(buttonsToChars), new Point(2, 3));
        keypads.add(numericKeypad);

        Map<Point, Character> buttonsToChars2 = new HashMap<>();
        buttonsToChars2.put(new Point(1, 0), '^');
        buttonsToChars2.put(new Point(2, 0), 'A');
        buttonsToChars2.put(new Point(0, 1), '<');
        buttonsToChars2.put(new Point(1, 1), 'v');
        buttonsToChars2.put(new Point(2, 1), '>');
        Keypad directionalKeypad1 = new Keypad(buttonsToChars2, invertMap(buttonsToChars2), new Point(2, 0));
        Keypad directionalKeypad2 = new Keypad(buttonsToChars2, invertMap(buttonsToChars2), new Point(2, 0));
        Keypad directionalKeypad3 = new Keypad(buttonsToChars2, invertMap(buttonsToChars2), new Point(2, 0));
        keypads.add(directionalKeypad1);
        keypads.add(directionalKeypad2);
        keypads.add(directionalKeypad3);
    }

    public static List<Character> wordResult(String s) {
        char[] charArray = s.toCharArray();

        List<Character> charList = new ArrayList<>();
        for (char c : charArray) {
            charList.add(c);
        }

        int depth = 0;

        while (depth <= 2) {
            List<Character> result = new ArrayList<>();
            for (char c : charList) {
                List<Character> path = keypads.get(depth).findShortestPath(c);
                if (depth > 0) {
                    keypads.get(depth).orderBest1(path);
                } else {
                    keypads.get(depth).orderBest2(path);
                }
                result.addAll(path);
            }
            depth++;
            charList = result;
            System.out.println(result);
            System.out.println();
        }

        return charList;
    }

    public static long findNumericPart(String s) {
        String numericPart = s.replaceAll("[^0-9]", "");
        int number = Integer.parseInt(numericPart);
        return number;
    }

    public static void run() {
        setup();
        // List<String> words = new ArrayList<>(Arrays.asList("129A", "176A", "985A",
        // "170A", "528A"));
        List<String> words = new ArrayList<>(Arrays.asList("029A", "980A", "179A",
                "456A", "379A"));
        // List<String> words = new ArrayList<>(Arrays.asList("456A"));
        long sum = 0;

        for (String word : words) {
            List<Character> result = wordResult(word);
            System.out.println(word + " = " + result.size());
            // System.out.println(findNumericPart(word));
            sum += result.size() * findNumericPart(word);
        }

        // System.out.println(sum);
    }
}

// 145896
