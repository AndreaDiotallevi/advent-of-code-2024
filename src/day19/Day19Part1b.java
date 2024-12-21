package day19;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19Part1b {
    public static Set<String> validPatternsSet = new HashSet<>();
    public static Set<String> invalidPatternsSet = new HashSet<>();
    public static List<String> designs = new ArrayList<>();
    public static String regex = "";

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

    public static boolean isDesignPossible(String design) {
        return true;
    }

    private static String buildRegex(Set<String> patterns) {
        StringBuilder regexBuilder = new StringBuilder();

        // Iterate over the set of patterns and join them with "|"
        for (String pattern : patterns) {
            if (regexBuilder.length() > 0) {
                regexBuilder.append("|"); // Add "or" between patterns
            }
            // Escape special characters
            regexBuilder.append(Pattern.quote(pattern));
        }

        // Return the combined regex string
        return regexBuilder.toString();
    }

    public static void run() {
        readInput();
        String s = buildRegex(validPatternsSet);
        System.out.println(s);

        int count = 0;
        for (String design : designs) {
            System.out.println();
            System.out.println(design);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(design);

            while (matcher.find()) {
                System.out.println("Found match: " + matcher.group());
                count++;
                break;
            }
        }

        System.out.println(regex);
        System.out.println(count);
    }
}
