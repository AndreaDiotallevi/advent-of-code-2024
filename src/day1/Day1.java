package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day1 {
    public int processFile() {
        try {
            File file = new File("resources/day1.txt");
            Scanner scanner = new Scanner(file);

            List<List<Integer>> myList = new ArrayList<>();
            myList.add(new ArrayList<>());
            myList.add(new ArrayList<>());

            while (scanner.hasNextInt()) {
                int left = scanner.nextInt();
                int right = scanner.nextInt();

                myList.get(0).add(left);
                myList.get(1).add(right);
            }
            scanner.close();

            Collections.sort(myList.get(0));
            Collections.sort(myList.get(1));

            int sum = 0;

            for (int i = 0; i < myList.get(0).size(); i++) {
                int diff = Math.abs(myList.get(0).get(i) - myList.get(1).get(i));
                sum += diff;
            }

            return sum;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            return -1;
        }
    }
}
