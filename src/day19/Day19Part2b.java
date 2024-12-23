package day19;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Day19Part2b {
    public static List<String> inputPatterns = new ArrayList<>();
    public static List<String> designs = new ArrayList<>();
    public static Map<String, Set<String>> combinationsMap = new ConcurrentHashMap<>();

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
                    inputPatterns.add(pattern);
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

    public static void createCombinationsMap(String design, List<String> previous) {
        String stringSoFar = String.join("", previous); // "rwbs"
        Set<String> stringSoFarValids = combinationsMap.getOrDefault(stringSoFar, new HashSet<>()); // []
        System.out.println("design=" + design);
        // System.out.println("string so far=" + stringSoFar);
        // System.out.println("previous=" + previous);

        if (design.isEmpty()) {
            stringSoFarValids.add(String.join("-", previous)); // ["r-w-bs"]
            combinationsMap.put(stringSoFar, stringSoFarValids);
            // System.out.println("enmpty string");
            // System.out.println();
            return;
        }

        if (combinationsMap.containsKey(design) && !previous.isEmpty()) {
            System.out.println("HERE in map");
            Set<String> currentStringValids = combinationsMap.get(design); // []
            if (currentStringValids.isEmpty())
                return;
            for (String s : currentStringValids) {
                String validSoFar = String.join("-", previous);
                stringSoFarValids.add(validSoFar + "-" + s);
            }
            combinationsMap.put(stringSoFar, currentStringValids);
            System.out.println("new comb");
            System.out.println(combinationsMap);
            System.out.println();
            return;
        }

        boolean invalid = true;

        for (Map.Entry<String, Set<String>> entry : combinationsMap.entrySet()) {
            String pattern = entry.getKey();
            if (entry.getValue().isEmpty())
                continue;
            if (design.startsWith(pattern)) {
                invalid = false;
                String nextDesign = design.substring(pattern.length());
                List<String> nextPrevious = new ArrayList<>(previous);
                nextPrevious.add(pattern);
                createCombinationsMap(nextDesign, nextPrevious);
            }
        }

        if (invalid) {
            combinationsMap.put(design, new HashSet<>());
        }
    }

    public static void run() {
        readInput();
        // inputPatterns.sort(Comparator.comparingInt(String::length));
        // System.out.println(inputPatterns);
        // System.out.println(designs);

        for (String s : inputPatterns) {
            Set<String> mySet = new HashSet<>();
            mySet.add(s);
            combinationsMap.put(s, mySet);
        }

        for (String d : inputPatterns) {
            createCombinationsMap(d, new ArrayList<>());
        }
        // System.out.println(combinationsMap);

        // for (String d : designs) {
        // System.out.println(d);
        // createCombinationsMap(d, new ArrayList<>());
        // Set<String> mySet = combinationsMap.getOrDefault(d, new HashSet<>());
        // mySet.remove(d);
        // }

        long sum = 0;
        // for (String design : designs) {
        // Set<String> mySet = combinationsMap.getOrDefault(design, new HashSet<>());
        // sum += mySet.size();
        // }
        // System.out.println(combinationsMap);
        System.out.println(sum);
    }
}
