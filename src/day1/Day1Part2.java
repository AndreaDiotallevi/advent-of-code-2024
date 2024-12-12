package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day1Part2 {
    public int processFile() {
        try {
            File file = new File("resources/day1.txt");
            Scanner scanner = new Scanner(file);

            List<Integer> leftList = new ArrayList<>();
            Map<Integer, Integer> rightMap = new HashMap<>();

            while (scanner.hasNextInt()) {
                int left = scanner.nextInt();
                int right = scanner.nextInt();

                if (!leftList.contains(left)) {
                    leftList.add(left);
                }

                if (rightMap.containsKey(right)) {
                    int currentValue = rightMap.get(right);
                    rightMap.put(right, currentValue + 1);
                } else {
                    rightMap.put(right, 1);
                }
            }

            int sum = 0;

            for (int num : leftList) {
                int value = rightMap.getOrDefault(num, 0);
                sum += num * value;
            }

            scanner.close();
            return sum;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            return -1;
        }
    }
}
