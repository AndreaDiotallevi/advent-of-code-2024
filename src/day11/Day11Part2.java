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
    public int times = 55;
    public Map<Integer,Long> mySet = new HashMap<>();

    public long processFile() {
        try {
            File file = new File("resources/day11test2.txt");
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

    // 1 x 1 = 1
    // 1 x 2 = 2
    // 1 x 3 = 4
    // 1 x 4 = 4
    // 1 x 5 = 7
    // 1 x 6 = 14
    // 1 x 7 = 16
    // 1 x 8 = 20
    // 1 x 9 = 39
    // 1 x 10 = 62
    // 1 x 20 = 3572
    // 1 x 30 = 234511
    // 1 x 40 = 15458147
    // 1 x 45 = 125001266
    // 1 x 46 = 189148778
    // 1 x 50 = 1010392024

    private void recursiveFind(long num, int depth) {
        // System.out.printf("depth=%d, num=%d %n",depth,num);
        
        if (num==1 && times-depth==3) {
            sum+=4;
            return;
        }
        if (num==1 && times-depth==10) {
            sum+=62;
            return;
        }
        if (num==1 && times-depth==20) {
            sum+=3572;
            return;
        }
        if (num==1 && times-depth==30) {
            sum+=234511;
            return;
        }
        if (num==1 && times-depth==40) {
            sum+=15458147;
            return;
        }
        if (num==1 && times-depth==46) {
            sum+=189148778;
            return;
        }
        if (num==1 && times-depth==50) {
            sum+=1010392024;
            return;
        }

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
