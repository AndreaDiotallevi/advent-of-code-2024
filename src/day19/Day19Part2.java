package day19;

import java.io.*;
import java.util.*;

public class Day19Part2 {
    public static Set<String> validPatternsSet = new TreeSet<String>();
    public static Map<String, Integer> validPatternsMap = new HashMap<String, Integer>();
    public static Set<String> visited = new HashSet<>();
    public static Set<String> invalidPatternsSet = new HashSet<>();
    public static List<String> designs = new ArrayList<>();
    public static int part2Count = 0;
    public static List<String> patternIterateList = new ArrayList<>();

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

    public static boolean valid(String str) {
        // System.out.println("valid function=" + str);
        // System.out.println(invalidPatternsSet);
        if (str.length() <= 8 && validPatternsMap.containsKey(str)) {
            int value = validPatternsMap.get(str);
            // System.out.println("final contains " + value);
            part2Count += value;
            return true;
        }
        if (invalidPatternsSet.contains(str)) {
            // System.out.println("invalid");
            // System.out.println(str);
            // System.out.println("///");
            return false;
        }
        // if (str.length() == 1) {
        // System.out.println("no");
        // return false;
        // }

        boolean result1 = false;

        // System.out.println();
        for (String pattern : patternIterateList) {
            // System.out.println("original str=" + str);
            // System.out.println("p=" + pattern);
            if (str.startsWith(pattern)) {
                result1 = true;
                String subString = str.substring(pattern.length(), str.length());
                boolean r = valid(subString);
                if (r) {
                    // System.out.println(subString);
                    // System.out.println("valid");
                    validPatternsSet.add(subString);
                    if (subString.length() <= 8) {
                        generateMapEntry(subString);
                    }
                    // return true;
                } else {
                    result1 = false;
                    // System.out.println("///");
                    // System.out.println("invalid");
                    // System.out.println(subString);
                    // System.out.println("///");
                    // System.out.println("//");
                    // invalidPatternsSet.add(subString);
                }
            }
        }

        if (!result1) {
            // System.out.println("result1");
            invalidPatternsSet.add(str);
        } else {
            // System.out.println("here");
        }

        return result1;
    }

    public static void createPatternIterateList() {
        for (String pattern : validPatternsSet) {
            patternIterateList.add(pattern);
        }
        Collections.sort(patternIterateList, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
    }

    public static List<List<String>> generateCombinations(String input) {
        // System.out.println("comb");
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
        // System.out.println(combinations.size());
        // System.out.println(combinations);
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
        validPatternsMap.put(str, num);
    }

    public static void run() {
        readInput();
        createPatternIterateList();
        for (String p : validPatternsSet) {
            generateMapEntry(p);
        }
        // System.out.println(validPatternsMap);

        // List<Integer> numbers = new ArrayList<>(Arrays.asList(
        // 0, 1, 2, 3, 4, 7, 8, 12, 13, 14, 17, 18, 19, 21, 22, 25, 26, 32, 33, 36, 37,
        // 44, 45, 47, 48, 50, 54, 55, 56, 58, 59, 65, 66, 67, 70, 72, 73, 75, 76, 84,
        // 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 98, 99, 100, 101, 102, 104, 105,
        // 111, 112, 115, 119, 120, 121, 122, 126, 130, 132, 135, 136, 138, 139, 141,
        // 142, 144, 145, 147, 148, 150, 151, 152, 154, 158, 160, 161, 162, 163, 167,
        // 169, 170, 173, 175, 176, 177, 178, 179, 182, 183, 185, 187, 188, 190, 192,
        // 193, 197, 198, 199, 201, 202, 206, 208, 211, 212, 213, 214, 215, 216, 217,
        // 218, 221, 222, 223, 224, 225, 226, 228, 232, 233, 236, 239, 240, 241, 242,
        // 243, 244, 248, 249, 250, 251, 252, 253, 255, 257, 258, 261, 262, 264, 265,
        // 266, 268, 269, 270, 274, 275, 276, 277, 278, 280, 281, 282, 283, 284, 287,
        // 288, 289, 290, 292, 293, 294, 295, 296, 297, 300, 301, 303, 304, 305, 307,
        // 312, 316, 318, 321, 324, 328, 331, 332, 337, 338, 340, 341, 342, 343, 346,
        // 347, 348, 349, 352, 353, 354, 358, 359, 361, 362, 363, 364, 365, 368, 369,
        // 371, 373, 374, 376, 377, 378, 379, 380, 382, 385, 387, 389, 390, 392, 394,
        // 395, 396, 399));

        // int i = 0;

        for (String design : designs) {
            // if (!numbers.contains(i))
            // continue;
            // System.out.println();
            System.out.println(design);
            // boolean valid = true;
            if (valid(design)) {
                // System.out.println("valid");
                validPatternsSet.add(design);
            } else {
                // System.out.println("else");
                // System.out.println("invalid");
                // System.out.println(design);
                // invalidPatternsSet.add(design);
            }
        }
        // System.out.println(invalidPatternsSet);
        // System.out.println(validPatternsSet);
        // System.out.println(validPatternsMap);

        System.out.println(part2Count);
    }
}
