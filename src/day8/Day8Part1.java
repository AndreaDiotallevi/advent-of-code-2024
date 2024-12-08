package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day8Part1 {
    public List<List<Character>> matrix = new ArrayList<>();
    public Map<Character,List<List<Integer>>> myMap = new HashMap<>();
    public Set<String> occurrencesMap = new HashSet<>();

    public int processFile() {
        try {
            File file = new File("resources/day8.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                List<Character> myList = new ArrayList<>();
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                for (int i=0; i<charArray.length; i++) {
                    char c = charArray[i];
                    myList.add(c);
                    if (c != '.') {
                        List<List<Integer>> mapItem = myMap.get(c);
                        if (mapItem == null) myMap.put(c, new ArrayList<>());
                        myMap.get(c).add(new ArrayList<>(Arrays.asList(matrix.size(), i)));
                    }
                }
                
                matrix.add(myList);
            }

            scanner.close();
            int matrixSize = matrix.size();

            // System.out.println(matrix);
            // System.out.println(myMap);

            for (Map.Entry<Character,List<List<Integer>>> entry : myMap.entrySet()) {
                // System.out.println(entry);
                Character key = entry.getKey();
                List<List<Integer>> coordinates = entry.getValue();
                // System.out.println(coordinates);

                int poolSize = coordinates.size();
                int i = 0;

                while (i < poolSize - 1) {
                    for (int j=i+1; j<poolSize; j++) {
                        // System.out.printf("%d %d %n",i,j);
                        List<Integer> first = coordinates.get(i);
                        List<Integer> second = coordinates.get(j);
                        // System.out.println(first);
                        // System.out.println(second);
                        int xDiff = Math.abs(first.get(0) - second.get(0));
                        int yDiff = Math.abs(first.get(1) - second.get(1));
                        // System.out.println(xDiff);
                        // System.out.println(yDiff);

                        int new1X = (first.get(0)<second.get(0)) ? (first.get(0)-xDiff) : (first.get(0)+xDiff);
                        int new1Y = (first.get(1)<second.get(1)) ? (first.get(1)-yDiff) : (first.get(1)+yDiff);

                        int new2X = (first.get(0)<second.get(0)) ? (second.get(0)+xDiff) : (second.get(0)-xDiff);
                        int new2Y = (first.get(1)<second.get(1)) ? (second.get(1)+yDiff) : (second.get(1)-yDiff);

                        // System.out.printf("%d %d %n",new1X,new1Y);
                        // System.out.printf("%d %d %n",new2X,new2Y);

                        if (
                            new1X>=0&&
                            new1Y>=0&&
                            new1X<matrixSize&&
                            new1Y<matrixSize&&
                            matrix.get(new1X).get(new1Y) != key
                        ) {
                            System.out.printf("key-%s %d,%d%n",key,new1X,new1Y);
                            occurrencesMap.add(String.valueOf(new1X)+ "-" +String.valueOf(new1Y));
                        }

                        if (
                            new2X>=0&&
                            new2Y>=0&&
                            new2X<matrixSize&&
                            new2Y<matrixSize&&
                            matrix.get(new2X).get(new2Y) != key
                        ) {
                            System.out.printf("key-%s %d,%d%n",key,new2X,new2Y);
                            occurrencesMap.add(String.valueOf(new2X)+ "-" +String.valueOf(new2Y));
                        }
                    }
                    i++;
                    System.out.println(" ");
                }
            }

            return occurrencesMap.size();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
