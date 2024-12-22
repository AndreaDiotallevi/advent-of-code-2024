package day19;

import java.io.*;
import java.util.*;

public class Day19Part2 {
    public static Set<String> validPatternsSet = new TreeSet<String>();
    public static Set<String> invalidPatternsSet = new HashSet<>();
    public static List<String> designs = new ArrayList<>();
    public static int part2Count = 0;
    public static List<String> patternIterateList = new ArrayList<>();

    public static void readInput() {
        try {
            File file = new File("resources/day19test.txt");
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

    public static boolean valid(String str) {
        System.out.println(str);
        if (validPatternsSet.contains(str)) {
            // if (str.length() > 1) {
            // return valid(str.substring(0, 1)) && valid(str.substring(1, str.length()));
            // }
            part2Count++;
            return true;
        }
        if (invalidPatternsSet.contains(str)) {
            System.out.println("invalid");
            System.out.println(str);
            System.out.println("///");
            return false;
        }
        // if (str.length() == 1) {
        // System.out.println("no");
        // return false;
        // }

        // System.out.println();
        for (String pattern : patternIterateList) {
            // System.out.println("p=" + pattern);
            if (str.startsWith(pattern)) {
                String subString = str.substring(pattern.length(), str.length());
                boolean r = valid(subString);
                if (r) {
                    System.out.println(subString);
                    System.out.println("valid");
                    validPatternsSet.add(subString);
                    // return true;
                } else {
                    System.out.println("else");
                    invalidPatternsSet.add(subString);
                }
            }
        }

        return false;
    }

    public static void createPatternIterateList() {
        for (String pattern : validPatternsSet) {
            patternIterateList.add(pattern);
        }
        Collections.sort(patternIterateList, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
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

            // Only add combinations that meet the length condition
            if (strings.length() <= 8) {
                result.add(strings.toString());
            }
        }

        // Convert each string into a list of substrings split by '|'
        List<List<String>> combinations = new ArrayList<>();
        for (String part : result) {
            List<String> sublist = Arrays.asList(part.split("\\|"));
            combinations.add(sublist);
        }

        return combinations;
    }

    public static void generateMapEntry(String str) {
        List<List<String>> combinations = generateCombinations(str);
        System.out.println(combinations.size());
        System.out.println(combinations);
        int num = 0;
        for (List<String> c : combinations) {
            boolean result = true;
            for (String s : c) {
                if (!patternIterateList.contains(s)) {
                    result = false;
                }
            }
            if (result) {
                num++;
            }
        }
        System.out.println(num);
    }

    public static void run() {
        readInput();
        createPatternIterateList();
        generateMapEntry("gbr");

        // for (String design : designs) {
        // // System.out.println();
        // // System.out.println(design);
        // if (valid(design)) {
        // validPatternsSet.add(design);
        // } else {
        // // invalidPatternsSet.add(design);
        // }
        // }

        System.out.println(part2Count);
    }
}
