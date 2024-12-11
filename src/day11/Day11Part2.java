package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day11Part2 {
    public List<Long> myList = new ArrayList<>();
    public int times = 75;
    public Map<String,Long> myMap = new HashMap<>();

    public long processFile() {
        try {
            File file = new File("resources/day11.txt");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] stringArray = line.split(" ");
            for (String s : stringArray) {
                myList.add(Long.parseLong(s));
            }
            scanner.close();
            Long sum = (long)0;
            for (int i=0; i<myList.size(); i++) {
                long partial = recursiveFind(myList.get(i), 0,0);
                myMap.put(myList.get(i) + "--" + 0, partial);
                sum+= partial;
            }
            return sum; 
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private long recursiveFind(long num, int depth, long sum) {
        if (myMap.containsKey(num+"--"+depth)) {
            return myMap.get(num+"--"+depth);
        }

        if (depth==times) {
            sum++;
            return 1;
        }

        if (num==0) {
            long result = recursiveFind(1, depth+1, sum);
            myMap.put(1 + "--" + (depth+1), result);
            return result;
        } else {
            String str = String.valueOf(num);
            int size = str.length();
            if (size%2==0) {
                long result1 = recursiveFind(Long.parseLong(str.substring(0, size/2)), depth+1,sum);
                myMap.put(Long.parseLong(str.substring(0, size/2)) + "--" + (depth+1), result1);
                long result2 = recursiveFind(Long.parseLong(str.substring(size/2)), depth+1,sum);
                myMap.put(Long.parseLong(str.substring(size/2)) + "--" + (depth+1), result2);
                return result1 + result2;
            } else {
                long result = recursiveFind(num*2024, depth+1,sum);
                myMap.put(num*2024 + "--" + (depth+1), result);
                return result;
            }
        }
    }
}
