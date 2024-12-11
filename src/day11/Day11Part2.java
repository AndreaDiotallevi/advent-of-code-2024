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
    public Long sum = (long)0;
    public int times = 75;
    public Map<Long,Long> mySet = new HashMap<>();

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
            for (int i=0; i<myList.size(); i++) {
                System.out.println(i);
                recursiveFind(myList.get(i), 0);
            }
            return sum; 
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private void recursiveFind(long num, int depth) {
        // System.out.printf("depth=%d, num=%d %n",depth,num);
        if (depth==times) {
            sum++;
            // System.out.println(sum);
            return;
        }

        if (num==0) {
            recursiveFind(1, depth+1);
        } else {
            String str = String.valueOf(num);
            int size = str.length();
            if (size%2==0) {
                recursiveFind(Long.parseLong(str.substring(0, size/2)), depth+1);
                recursiveFind(Long.parseLong(str.substring(size/2)), depth+1);
            } else {
                recursiveFind(num*2024, depth+1);
            }
        }
    }
}
