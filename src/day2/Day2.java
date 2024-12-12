package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {
    public int processFile() {
        try {
            File file = new File("resources/day2.txt");
            Scanner scanner = new Scanner(file);

            List<List<Integer>> listOfLists = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(" ");
                List<Integer> list = new ArrayList<>();

                for (String value : values) {
                    list.add(Integer.parseInt(value));
                }

                listOfLists.add(list);
            }

            int sum = 0;

            for (List<Integer> list : listOfLists) {
                if (alwaysDecreasing(list) && diffsLessThanFour(list)) {
                    sum += 1;
                } else if (alwaysIncreasing(list) && diffsLessThanFour(list)) {
                    sum += 1;
                }

            }

            scanner.close();
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private boolean alwaysDecreasing(List<Integer> nums) {
        int length = nums.size();
        int index = 0;
        boolean result = true;

        while (index < length - 1) {
            if (nums.get(index) <= nums.get(index + 1)) {
                result = false;
                break;
            }
            index++;
        }

        return result;
    }

    private boolean alwaysIncreasing(List<Integer> nums) {
        int length = nums.size();
        int index = 0;
        boolean result = true;

        while (index < length - 1) {
            if (nums.get(index) >= nums.get(index + 1)) {
                result = false;
                break;
            }
            index++;
        }

        return result;
    }

    private boolean diffsLessThanFour(List<Integer> nums) {
        int length = nums.size();
        int index = 0;
        boolean result = true;

        while (index < length - 1) {
            if (Math.abs((nums.get(index) - nums.get(index + 1))) >= 4) {
                result = false;
                break;
            }
            index++;
        }

        return result;
    }
}
