package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day5Part2 {
    public Map<Integer,Set<Integer>> map = new HashMap<>();

    public int processFile() {
        try {
            File file = new File("resources/day5.txt");
            Scanner scanner = new Scanner(file);

            int sum = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line == "") break;

                String[] inputs = line.split("\\|");
                int left = Integer.parseInt(inputs[0]);
                int right = Integer.parseInt(inputs[1]);

                if (map.containsKey(left)) {
                    map.get(left).add(right);
                } else {
                    map.put(left, new HashSet<>());
                    map.get(left).add(right);
                }
            }

            while (scanner.hasNextLine()) {
                boolean valid = true;
                String line = scanner.nextLine();

                String[] inputs = line.split(",");

                List<Integer> collection = new ArrayList<>();

                for (String str : inputs) {
                    collection.add(Integer.parseInt(str));
                }

                for (int i=collection.size()-1; i>0; i--) {
                    int current = collection.get(i);
                    for (int previous : collection.subList(0, i)) {
                        if (map.get(current) != null && map.get(current).contains(previous)) {
                            valid = false;
                        };
                    }
                }

                if (!valid) {
                    int loopIndex = 0;

                    while (loopIndex < 5) {
                        for (Map.Entry<Integer,Set<Integer>> mapEntry : map.entrySet()) {
                            int currentLeft = mapEntry.getKey();
                            
                            for (int setEntry : mapEntry.getValue()) {
                                if (collection.contains(currentLeft) && collection.contains(setEntry)) {
                                    int leftIndex = collection.indexOf(currentLeft);
                                    int rightIndex = collection.indexOf(setEntry);
                                    
                                    if (leftIndex > rightIndex) {
                                        Collections.swap(collection, leftIndex, rightIndex);
                                    }  
                                }
                            }
                        }
                        loopIndex++;
                    }

                    sum += collection.get(collection.size()/2);
                }
            }

            scanner.close();
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
