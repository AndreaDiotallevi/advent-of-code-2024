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
        int i = 0;
        List<Integer> myList = new ArrayList<>();
        for (String design : designs) {
            if (valid(design)) {
                validPatternsSet.add(design);
                count++;
                myList.add(i);
            } else {
                invalidPatternsSet.add(design);
            }
            i++;
        }
        System.out.println(count);
    }
}
