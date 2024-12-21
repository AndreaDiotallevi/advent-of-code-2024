package day18;

import java.io.*;
import java.util.*;

public class Day18Part1 {
    public static List<List<Character>> memory = new ArrayList<>();
    public static List<int[]> fallingBytes = new ArrayList<>();

    public static void readInput() {
        try {
            File file = new File("resources/day18test.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split(",");
                int[] fallingByte = new int[] { Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]) };
                fallingBytes.add(fallingByte);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // Do nothing
        }
    }

    public static void run() {
        readInput();
        for (int[] b : fallingBytes) {
            System.out.println(Arrays.toString(b));
        }
    }
}
