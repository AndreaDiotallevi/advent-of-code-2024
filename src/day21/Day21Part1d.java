package day21;

import java.util.*;

public class Day21Part1d {
    public static Map<String, List<Character>> shortestDirections = new HashMap<>();
    public static int directionalKeypadsCount = 19;
    public static List<Character> currentPositions = new ArrayList<>();

    static {
        shortestDirections.put("A-^", new ArrayList<>(List.of('<')));
        shortestDirections.put("A->", new ArrayList<>(List.of('v')));
        shortestDirections.put("A-v", new ArrayList<>(List.of('<', 'v')));
        shortestDirections.put("A-<", new ArrayList<>(List.of('v', '<', '<')));
        shortestDirections.put("^-A", new ArrayList<>(List.of('>')));
        shortestDirections.put("^->", new ArrayList<>(List.of('>', 'v')));
        shortestDirections.put("^-<", new ArrayList<>(List.of('v', '<')));
        shortestDirections.put("<-A", new ArrayList<>(List.of('>', '>', '^')));
        shortestDirections.put("<-^", new ArrayList<>(List.of('>', '^')));
        shortestDirections.put("<-v", new ArrayList<>(List.of('>')));
        shortestDirections.put("v-A", new ArrayList<>(List.of('>', '^')));
        shortestDirections.put("v->", new ArrayList<>(List.of('>')));
        shortestDirections.put("v-<", new ArrayList<>(List.of('<')));
        shortestDirections.put(">-A", new ArrayList<>(List.of('^')));
        shortestDirections.put(">-^", new ArrayList<>(List.of('<', '^')));
        shortestDirections.put(">-v", new ArrayList<>(List.of('<')));
        shortestDirections.put("A-0", new ArrayList<>(List.of('<')));
        shortestDirections.put("0-2", new ArrayList<>(List.of('^')));
        shortestDirections.put("2-9", new ArrayList<>(List.of('^', '^', '>')));
        shortestDirections.put("9-A", new ArrayList<>(List.of('v', 'v', 'v')));
        shortestDirections.put("A-9", new ArrayList<>(List.of('^', '^', '^')));
        shortestDirections.put("9-8", new ArrayList<>(List.of('<')));
        shortestDirections.put("8-0", new ArrayList<>(List.of('v', 'v', 'v')));
        shortestDirections.put("0-A", new ArrayList<>(List.of('>')));
        shortestDirections.put("A-1", new ArrayList<>(List.of('^', '<', '<')));
        shortestDirections.put("1-7", new ArrayList<>(List.of('^', '^')));
        shortestDirections.put("7-9", new ArrayList<>(List.of('>', '>')));
        shortestDirections.put("A-4", new ArrayList<>(List.of('^', '^', '<', '<')));
        shortestDirections.put("4-5", new ArrayList<>(List.of('>')));
        shortestDirections.put("5-6", new ArrayList<>(List.of('>')));
        shortestDirections.put("6-A", new ArrayList<>(List.of('v', 'v')));
        shortestDirections.put("A-3", new ArrayList<>(List.of('^')));
        shortestDirections.put("3-7", new ArrayList<>(List.of('<', '<', '^', '^')));
        shortestDirections.put("1-2", new ArrayList<>(List.of('>')));
        shortestDirections.put("7-6", new ArrayList<>(List.of('v', '>', '>')));
        shortestDirections.put("8-5", new ArrayList<>(List.of('v')));
        shortestDirections.put("5-A", new ArrayList<>(List.of('v', 'v', '>')));
        shortestDirections.put("7-0", new ArrayList<>(List.of('>', 'v', 'v', 'v')));
        shortestDirections.put("0-A", new ArrayList<>(List.of('>')));
        shortestDirections.put("A-5", new ArrayList<>(List.of('<', '^', '^')));
        shortestDirections.put("5-2", new ArrayList<>(List.of('v')));
        shortestDirections.put("2-8", new ArrayList<>(List.of('^', '^')));
        shortestDirections.put("8-A", new ArrayList<>(List.of('v', 'v', 'v', '>')));

        for (int i = 0; i < directionalKeypadsCount; i++) {
            currentPositions.add('A');
        }
    }

    public static long findScore(List<Character> myList, int depth) {
        if (depth == directionalKeypadsCount) {
            // result.addAll(myList);
            return myList.size();
        }

        // if (depth == 1) {
        // result1.addAll(myList);
        // }

        long score = 0;

        for (Character target : myList) {
            char current = currentPositions.get(depth);
            List<Character> path = new ArrayList<>();
            if (current != target) {
                path.addAll(shortestDirections.get(current + "-" + target));
            }
            path.add('A');

            currentPositions.set(depth, target);
            score += findScore(path, depth + 1);
        }

        return score;
    }

    public static List<Character> wordResult(String s) {
        char[] charArray = s.toCharArray();

        List<Character> charList = new ArrayList<>();
        for (char c : charArray) {
            charList.add(c);
        }

        int keypadIndex = 0;

        while (keypadIndex < directionalKeypadsCount) {
            if (keypadIndex > 5)
                return new ArrayList<>();
            System.out.println(keypadIndex);
            char current = 'A';
            List<Character> result = new ArrayList<>();
            for (char c : charList) {
                if (current != c) {
                    List<Character> path = shortestDirections.get(current + "-" + c);
                    result.addAll(path);
                }
                result.add('A');
                current = c;
            }
            keypadIndex++;
            charList = result;
            System.out.println(result);
            System.out.println(result.size());
            System.out.println("v=" + Collections.frequency(result, 'v'));
            System.out.println(">=" + Collections.frequency(result, '>'));
        }

        return charList;
    }

    public static long findNumericPart(String s) {
        String numericPart = s.replaceAll("[^0-9]", "");
        int number = Integer.parseInt(numericPart);
        return number;
    }

    public static void run() {
        List<String> words = new ArrayList<>(Arrays.asList("029A"));
        // List<String> words = new ArrayList<>(Arrays.asList("029A", "980A", "179A",
        // "456A", "379A"));

        // List<String> words = new ArrayList<>(Arrays.asList("129A", "176A", "985A",
        // "170A", "528A"));

        long sum = 0;

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            char[] charArray = word.toCharArray();
            List<Character> charList = new ArrayList<>();
            for (char c : charArray) {
                charList.add(c);
            }

            long score = findScore(charList, 0);
            System.out.println("score=" + score);

            sum += score * findNumericPart(word);
        }

        // System.out.println(result1);
        // System.out.println(result);
        System.out.println(sum);
    }
}

// 18 61688450
// 19 154417672
