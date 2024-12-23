package day19;

import java.io.*;
import java.util.*;

public class Day19Part2 {
    public static List<String> patterns = new ArrayList<>();
    public static List<String> designs = new ArrayList<>();
    public static Map<String, Long> myMap = new HashMap<>();

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
                    patterns.add(pattern);
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

    public static long createMap(String design) {
        if (design.isEmpty()) {
            return 1;
        }
        if (myMap.containsKey(design)) {
            return myMap.get(design);
        }
        long count = 0;
        for (String pattern : patterns) {
            if (design.startsWith(pattern)) {
                long result = createMap(design.substring(pattern.length()));
                long value = myMap.getOrDefault(design, 0L);
                myMap.put(design, value + result);
                count += result;
            }
        }
        return count;
    }

    public static void run() {
        readInput();
        patterns.sort(Comparator.comparingLong(String::length));

        for (String pattern : patterns) {
            createMap(pattern);
        }

        for (String design : designs) {
            createMap(design);
        }

        long sum = 0;
        for (String design : designs) {
            sum += myMap.getOrDefault(design, 0L);
        }
        System.out.println(sum);
    }
}
