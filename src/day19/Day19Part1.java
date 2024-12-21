package day19;

import java.io.*;
import java.util.*;

public class Day19Part1 {
    public static Set<String> patternsSet = new HashSet<>();
    public static List<String> designs = new ArrayList<>();

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

    public static void run() {
        readInput();
        System.out.println(patternsSet);
        System.out.println(designs);
    }
}
