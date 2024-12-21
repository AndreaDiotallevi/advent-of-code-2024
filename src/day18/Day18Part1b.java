package day18;

import java.io.*;
import java.util.*;

public class Day18Part1b {
    public static List<List<Integer>> memory = new ArrayList<>();
    public static List<List<Integer>> fallingBytes = new ArrayList<>();
    public static int memorySize = 7;
    public static int bytesCount = 12;

    public static void readInput() {
        try {
            File file = new File("resources/day18test.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split(",");
                List<Integer> fallingByte = new ArrayList<>();
                fallingByte.add(Integer.parseInt(lineArray[0]));
                fallingByte.add(Integer.parseInt(lineArray[1]));
                fallingBytes.add(fallingByte);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new Error("FileNotFoundException");
        }
    }

    public static void printFallingBytes() {
        for (List<Integer> b : fallingBytes) {
            System.out.println(b);
        }
        System.out.println();
    }

    public static void createMemory() {
        for (int x = 0; x < memorySize; x++) {
            List<Integer> row = new ArrayList<>();
            for (int y = 0; y < memorySize; y++) {
                row.add(-1);
            }
            memory.add(row);
        }

        for (int time = 0; time < bytesCount; time++) {
            List<Integer> fallingByte = fallingBytes.get(time);
            int row = fallingByte.get(0);
            int col = fallingByte.get(1);
            memory.get(row).set(col, time);
        }
    }

    public static void printMemory() {
        for (int x = 0; x < memorySize; x++) {
            for (int y = 0; y < memorySize; y++) {
                String paddedNumber = String.format("%3d ", memory.get(y).get(x));
                System.out.print(paddedNumber);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void run() {
        readInput();
        printFallingBytes();
        createMemory();
        printMemory();
    }
}
