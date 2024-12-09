package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day9Part2 {
    public List<Integer> memory = new ArrayList<>();

    public long processFile() {
        try {
            File file = new File("resources/day9.txt");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();

            int id = 0;

            for (int i=0; i<line.length(); i++) {
                boolean isBlockFile = (i & 1) == 0;
                char currentChar = line.charAt(i);

                long times = currentChar - '0';
                for (long j=0; j<times; j++) {
                    if (isBlockFile) {
                        memory.add(id);
                    } else {
                        memory.add(-1);
                    }
                }
                if (isBlockFile) id++;
            }

            int i=0;
            int j=memory.size()-1;
            boolean exit = false;
            
            while (!exit) {
                int rightSpace=0;
                int currentRightMostId = -2;
                boolean rightFinished = false;
                while (!rightFinished) {
                    if (j<0) {
                        rightFinished = true;
                        break;
                    }
                    if (memory.get(j) != -1) {
                        if (currentRightMostId == -2) {
                            currentRightMostId = memory.get(j);
                        }
                        if (memory.get(j) == currentRightMostId) {
                            rightSpace++;
                        } else {
                            rightFinished = true;
                            break;
                        }
                    } else {
                        if (rightSpace > 0) {
                            rightFinished = true;
                            break;
                        }
                    }
                    
                    j--;
                }

                int leftSpace=0;
                boolean leftFinished = false;
                while (!leftFinished) {
                    if (i>j) {
                        leftFinished = true;
                        break;
                    }
                    if (memory.get(i) == -1) {
                        leftSpace++;
                    } else {
                        if (leftSpace > 0) {
                            leftFinished = true;
                            break;
                        }
                    }
                    
                    i++;
                }

                if (leftSpace >= rightSpace) {
                    for (int k=0; k<rightSpace; k++) {
                        Collections.swap(memory, k+i-leftSpace, k+j+1);
                    }
                    i=0;
                } else {
                    if (j<0) {
                        exit = true;
                        break;
                    }
                    if (i>=j) {
                        i=0;
                    } else {
                        j=j+rightSpace;
                    }
                }
            }

            long sum=0;
            for (int k=0; k<memory.size(); k++) {
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
