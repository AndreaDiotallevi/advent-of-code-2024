package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day12Part1 {
    public List<List<Character>> matrix = new ArrayList<>();
    public int size;
    public List<Set<String>> listOfSets = new ArrayList<>();
    public Set<String> seen = new HashSet<>();
    List<int[]> directions = new ArrayList<>(Arrays.asList(
            new int[] { 0, 1 },
            new int[] { 1, 0 },
            new int[] { 0, -1 },
            new int[] { -1, 0 }));

    public long processFile() {
        try {
            File file = new File("resources/day12.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                List<Character> row = new ArrayList<>();
                for (char c : charArray) {
                    row.add(c);
                }
                matrix.add(row);
            }
            scanner.close();
            size = matrix.size();

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    boolean completed = seen.contains(x + "-" + y);
                    if (completed)
                        continue;
                    Set<String> mySet = new HashSet<>();
                    mySet.add(x + "-" + y);
                    listOfSets.add(mySet);
                    recursiveFind(x, y, mySet);
                }
            }

            long sum = 0;
            for (Set<String> mySet : listOfSets) {
                long perimeter = 0;
                for (String item : mySet) {
                    String[] keySplit = item.split("-");
                    int x = Integer.parseInt(keySplit[0]);
                    int y = Integer.parseInt(keySplit[1]);
                    perimeter += getPerimeter(x, y);
                }
                sum += mySet.size() * perimeter;
            }
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private void recursiveFind(int x, int y, Set<String> mySet) {
        boolean completed = seen.contains(x + "-" + y);
        if (completed) {
            return;
        }

        char currentChar = matrix.get(x).get(y);
        seen.add(x + "-" + y);

        for (int[] direction : directions) {
            int newX = (int) x + direction[0];
            int newY = (int) y + direction[1];

            if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar == c) {
                    mySet.add(newX + "-" + newY);
                    recursiveFind(newX, newY, mySet);
                }
            } else {
                continue;
            }
        }
    }

    private int getPerimeter(int x, int y) {
        char currentChar = matrix.get(x).get(y);
        int perimeter = 4;

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar == c) {
                    perimeter--;
                }
            }
        }
        return perimeter;
    }
}
