package day19;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Day19Part2c {
    public static List<String> patterns = new ArrayList<>();
    public static List<String> designs = new ArrayList<>();
    public static Map<String, Integer> myMap = new ConcurrentHashMap<>();

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

    public static int createMap(String design) {
        if (design.isEmpty()) {
            return 1;
        }
        if (myMap.containsKey(design)) {
            return myMap.get(design);
        }
        int count = 0;
        for (String pattern : patterns) {
            if (design.startsWith(pattern)) {
                int result = createMap(design.substring(pattern.length()));
                int value = myMap.getOrDefault(design, 0);
                myMap.put(design, value + result);
                count += result;
            }
        }
        return count;
    }

    public static void run() {
        readInput();
        patterns.sort(Comparator.comparingInt(String::length));
        System.out.println(patterns);

        for (String pattern : patterns) {
            createMap(pattern);
        }

        for (String design : designs) {
            createMap(design);
        }

        System.out.println(myMap);

        long sum = 0;
        for (String design : designs) {
            sum += myMap.getOrDefault(design, 0);
        }
        System.out.println(sum);
    }
}

// 35003692383
