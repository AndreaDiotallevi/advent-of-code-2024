package day21;

import java.util.*;

public class Day21Part1b {

    // +---+---+---+
    // |***| ^ | A |
    // +---+---+---+
    // | < | v | > |
    // +---+---+---+

    public static Map<String, List<Character>> myMap = new HashMap<>();

    static {
        myMap.put("A-^", new ArrayList<>(List.of('<')));
        myMap.put("A->", new ArrayList<>(List.of('v')));
        myMap.put("A-v", new ArrayList<>(List.of('<', 'v')));
        myMap.put("A-<", new ArrayList<>(List.of('v', '<', '<')));
        myMap.put("^-A", new ArrayList<>(List.of('>')));
        myMap.put("^->", new ArrayList<>(List.of('>', 'v')));
        myMap.put("^-<", new ArrayList<>(List.of('v', '<')));
        myMap.put("<-A", new ArrayList<>(List.of('>', '>', '^')));
        myMap.put("<-^", new ArrayList<>(List.of('>', '^')));
        myMap.put("<-v", new ArrayList<>(List.of('>')));
        myMap.put("v-A", new ArrayList<>(List.of('>', '^')));
        myMap.put("v->", new ArrayList<>(List.of('>')));
        myMap.put("v-<", new ArrayList<>(List.of('<')));
        myMap.put(">-A", new ArrayList<>(List.of('^')));
        myMap.put(">-^", new ArrayList<>(List.of('<', '^')));
        myMap.put(">-v", new ArrayList<>(List.of('<')));
        myMap.put("A-0", new ArrayList<>(List.of('<')));
        myMap.put("0-2", new ArrayList<>(List.of('^')));
        myMap.put("2-9", new ArrayList<>(List.of('^', '^', '>')));
        myMap.put("9-A", new ArrayList<>(List.of('v', 'v', 'v')));
        myMap.put("A-9", new ArrayList<>(List.of('^', '^', '^')));
        myMap.put("9-8", new ArrayList<>(List.of('<')));
        myMap.put("8-0", new ArrayList<>(List.of('v', 'v', 'v')));
        myMap.put("0-A", new ArrayList<>(List.of('>')));
        myMap.put("A-1", new ArrayList<>(List.of('^', '<', '<')));
        myMap.put("1-7", new ArrayList<>(List.of('^', '^')));
        myMap.put("7-9", new ArrayList<>(List.of('>', '>')));
        myMap.put("A-4", new ArrayList<>(List.of('^', '^', '<', '<')));
        myMap.put("4-5", new ArrayList<>(List.of('>')));
        myMap.put("5-6", new ArrayList<>(List.of('>')));
        myMap.put("6-A", new ArrayList<>(List.of('v', 'v')));
        myMap.put("A-3", new ArrayList<>(List.of('^')));
        myMap.put("3-7", new ArrayList<>(List.of('<', '<', '^', '^')));
    }

    public static List<Character> wordResult(String s) {
        char[] charArray = s.toCharArray();

        List<Character> charList = new ArrayList<>();
        for (char c : charArray) {
            charList.add(c);
        }

        int depth = 0;

        while (depth <= 2) {
            char current = 'A';
            List<Character> result = new ArrayList<>();
            for (char c : charList) {
                if (current != c) {
                    List<Character> path = myMap.get(current + "-" + c);
                    result.addAll(path);
                }
                result.add('A');
                current = c;
            }
            depth++;
            charList = result;
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
        // List<String> words = new ArrayList<>(
        // Arrays.asList("<A^A>^^AvvvA", "^^^A<AvvvA>A", "^<<A^^A>>AvvvA",
        // "^A<<^^A>>AvvvA", "^^<<A>A>AvvA"));

        // List<Integer> numbers = new ArrayList<>(Arrays.asList(29, 980, 179, 456,
        // 379));

        // List<String> words = new ArrayList<>(
        // Arrays.asList("^<<A>A^^>AvvvA", "^<<A^^A>>vAvvA", "^^^A<AvAvv>A",
        // "^<<A^^A>vvvA>A", "^^<AvA^^A>vvvA"));

        // List<Integer> numbers = new ArrayList<>(Arrays.asList(129, 176, 985, 170,
        // 528));

        List<String> words = new ArrayList<>(Arrays.asList("029A", "980A", "179A",
                "456A", "379A"));

        long sum = 0;

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            List<Character> result = wordResult(word);
            System.out.println(result);
            System.out.println(word + " = " + result.size());
            sum += result.size() * findNumericPart(word);
        }

        System.out.println(sum);
    }
}

// 141708 too high
// 145896
