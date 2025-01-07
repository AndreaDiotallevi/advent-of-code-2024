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
    }

    public static List<Character> wordResult(String s) {
        char[] charArray = s.toCharArray();

        List<Character> charList = new ArrayList<>();
        for (char c : charArray) {
            charList.add(c);
        }

        int depth = 0;

        while (depth <= 1) {
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

    public static void run() {
        List<String> words = new ArrayList<>(
                Arrays.asList("<A^A>^^AvvvA", "^^^A<AvvvA>A", "^<<A^^A>>AvvvA", "^A<<^^A>>AvvvA", "^^<<A>A>AvvA"));

        List<Integer> numbers = new ArrayList<>(Arrays.asList(29, 980, 179, 456, 379));

        long sum = 0;

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            int number = numbers.get(i);
            List<Character> result = wordResult(word);
            System.out.println(result);
            System.out.println(word + " = " + result.size());
            sum += result.size() * number;
        }

        System.out.println(sum);
    }
}
