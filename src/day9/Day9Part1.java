package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day9Part1 {
    public List<Integer> memory = new ArrayList<>();

    public int processFile() {
        try {
            File file = new File("resources/day9test.txt");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            System.out.println(line);
            scanner.close();

            int digit = 0;

            for (int i=0; i<line.length(); i++) {
                boolean isBlockFile = (i & 1) == 0;
                // System.out.println(i);
                // System.out.println(isBlockFile);
                char currentChar = line.charAt(i);

                int times = currentChar - '0';
                for (int j=0; j<times; j++) {
                    if (isBlockFile) {
                        memory.add(digit);
                    } else {
                        memory.add(-1);
                    }
                }
                if (isBlockFile) digit++;
            }

            System.out.println(memory);

            int i=0;
            int j=memory.size()-1;

            while (i<j) {
                boolean found = false;

                while (!found) {
                    if (memory.get(i) == -1) {
                        found = true;
                    } else {
                        i++;
                    }
                }

                found = false;

                while (!found) {
                    if (memory.get(j) == -1) {
                        j--;
                    } else {
                        found = true;
                    }
                }

                Collections.swap(memory, i, j);
                i++;
                j--;
            }

            // System.out.println(memory);
            // System.out.println(j);

            int sum=0;
            for (int k=0; k<j+1; k++) {
                int num = memory.get(k);
                if (num != -1) {
                    sum += num * k;
                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
