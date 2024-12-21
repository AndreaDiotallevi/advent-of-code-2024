package day19;

import java.io.*;
import java.util.*;

public class Day19Part1 {
    public static Set<String> validPatternsSet = new HashSet<>();
    public static Set<String> invalidPatternsSet = new HashSet<>();
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
                    validPatternsSet.add(pattern);
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

    // public static List<List<String>> generateCombinations(String input) {
    // List<String> result = new ArrayList<>();
    // int n = input.length();

    // int totalCombinations = 1 << (n - 1);

    // for (int i = 0; i < totalCombinations; i++) {
    // StringBuilder strings = new StringBuilder();

    // for (int j = 0; j < n; j++) {
    // // System.out.println(i + "-" + j);
    // strings.append(input.charAt(j));
    // if (j < n - 1 && ((i & (1 << j)) != 0)) {
    // strings.append('|');
    // }
    // }
    // if (strings.length() <= 8) {
    // result.add(strings.toString());
    // }

    // result.add(strings.toString());
    // }

    // List<List<String>> combinations = new ArrayList<>();

    // for (String part : result) {
    // List<String> sublist = Arrays.asList(part.split("\\|"));
    // combinations.add(sublist);
    // }

    // return combinations;
    // }

    // public static boolean isDesignPossible(String design) {
    // if (validPatternsSet.contains(design)) {
    // return true;
    // }
    // if (invalidPatternsSet.contains(design)) {
    // return false;
    // }
    // if (design.length() == 1) {
    // return false;
    // }

    // List<List<String>> combinations = generateCombinations(design);
    // // System.out.println(combinations);
    // boolean result = false;

    // for (List<String> strings : combinations) {
    // if (strings.size() == 1)
    // continue;
    // boolean combinationResult = true;
    // for (String str : strings) {
    // boolean strResult = isDesignPossible(str);
    // if (strResult) {
    // validPatternsSet.add(str);
    // } else {
    // invalidPatternsSet.add(str);
    // }
    // combinationResult = combinationResult && strResult;
    // }
    // if (combinationResult == true) {
    // result = true;
    // break;
    // }
    // }

    // return result;
    // }

    public static boolean valid(String str) {
        if (validPatternsSet.contains(str)) {
            return true;
        }
        if (invalidPatternsSet.contains(str)) {
            return false;
        }
        if (str.length() == 1) {
            return false;
        }

        for (String pattern : validPatternsSet) {
            if (str.startsWith(pattern)) {
                String subString = str.substring(pattern.length(), str.length());
                boolean r = valid(subString);
                if (r) {
                    validPatternsSet.add(subString);
                    return true;
                } else {
                    invalidPatternsSet.add(subString);
                }
            }
        }

        return false;
    }

    public static void run() {
        readInput();

        int count = 0;
        for (String design : designs) {
            System.out.println(design);
            if (valid(design)) {
                validPatternsSet.add(design);
                count++;
                System.out.println(count);
            } else {
                invalidPatternsSet.add(design);
            }
        }

        System.out.println(count);
    }
}
