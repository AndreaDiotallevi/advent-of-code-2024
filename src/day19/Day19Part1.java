package day19;

import java.io.*;
import java.util.*;

public class Day19Part1 {
    public static Set<String> patternsSet = new HashSet<>();
    public static List<String> designs = new ArrayList<>();

    public static void readInput() {
        try {
            File file = new File("resources/day19.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty())
                    break;
                String[] lineArray = line.split(", ");
                for (String pattern : lineArray) {
                    patternsSet.add(pattern);
                }
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                designs.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Error("FileNotFoundException");
        }
    }

    public static List<List<String>> generateCombinations(String input) {
        List<String> result = new ArrayList<>();
        int n = input.length();

        int totalCombinations = 1 << (n - 1);

        for (int i = 0; i < totalCombinations; i++) {
            StringBuilder strings = new StringBuilder();

            for (int j = 0; j < n; j++) {
                strings.append(input.charAt(j));
                if (j < n - 1 && ((i & (1 << j)) != 0)) {
                    strings.append('|');
                }
            }

            result.add(strings.toString());
        }

        List<List<String>> combinations = new ArrayList<>();

        for (String part : result) {
            List<String> sublist = Arrays.asList(part.split("\\|"));
            combinations.add(sublist);
        }

        return combinations;
    }

    public static boolean isDesignPossible(String design) {
        if (patternsSet.contains(design)) {
            return true;
        }
        if (design.length() == 1) {
            return false;
        }

        List<List<String>> combinations = generateCombinations(design);
        System.out.println(combinations);
        boolean result = false;

        for (List<String> strings : combinations) {
            if (strings.size() == 1)
                continue;
            boolean combinationResult = true;
            for (String str : strings) {
                combinationResult = combinationResult && isDesignPossible(str);
            }
            if (combinationResult == true) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static void run() {
        readInput();
        // System.out.println(patternsSet);
        // System.out.println(designs);
        // boolean result = isDesignPossible("brb");
        // System.out.println(result);
        int count = 0;
        for (String design : designs) {
            if (isDesignPossible(design)) {
                patternsSet.add(design);
                count++;

            }
        }
        System.out.println(count);
    }
}
