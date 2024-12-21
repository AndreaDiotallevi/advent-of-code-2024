package day18;

import java.io.*;
import java.util.*;

public class Day18Part1 {
    public static class Node {
        int x, y, time;

        public Node(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, time);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            return x == other.x && y == other.y && time == other.time;
        }
    }

    public static Set<Node> memory = new HashSet<>();
    public static List<List<Integer>> fallingBytes = new ArrayList<>();
    public static int memorySize = 7;

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
            for (int y = 0; y < memorySize; y++) {
                Node node = new Node(x, y, 0);
                memory.add(node);
            }
        }

        for (int time = 0; time < fallingBytes.size(); time++) {
            List<Integer> fallingByte = fallingBytes.get(time);
            Node node = new Node(fallingByte.get(0), fallingByte.get(1), time);
            memory.add(node);
        }
    }

    // public static void createMemory() {
    // for (int x = 0; x < memorySize; x++) {
    // List<Character> row = new ArrayList<>();
    // for (int y = 0; y < memorySize; y++) {
    // row.add('.');
    // }
    // memory.add(row);
    // }

    // for (List<Integer> fallingByte : fallingBytes) {
    // int row = fallingByte.get(0);
    // int col = fallingByte.get(1);
    // memory.get(row).set(col, '#');
    // }
    // }

    public static void printMemory() {
        for (int x = 0; x < memorySize; x++) {
            for (int y = 0; y < memorySize; y++) {
                System.out.print(memory.get(y).get(x));
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
