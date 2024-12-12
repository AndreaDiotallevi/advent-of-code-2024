package day12;

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

public class Day12Part2 {
    public List<List<Character>> matrix = new ArrayList<>();
    public int size;
    public List<Set<String>> listOfSets = new ArrayList<>();
    public Set<String> seen = new HashSet<>();
    List<int[]> directions = new ArrayList<>(Arrays.asList(
        new int[]{0, 1},
        new int[]{1, 0},
        new int[]{0, -1},
        new int[]{-1, 0}
    ));
    
    public long processFile() {
        try {
            File file = new File("resources/day12test5.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                List<Character> row = new ArrayList<>();
                for (char c : charArray) {
                    row.add(c);
                }
                matrix.add(row);
            }
            scanner.close();
            size = matrix.size();

            for (int x=0; x<size; x++) {
                for (int y=0; y<size; y++) {
                    // if (matrix.get(x).get(y)!='C') continue;
                    boolean completed = seen.contains(x+"-"+y);
                    if (completed) continue;
                    // System.out.println(" ");
                    // System.out.println("new set");
                    Set<String> mySet = new HashSet<>();
                    mySet.add(x+"-"+y);
                    listOfSets.add(mySet);
                    recursiveFind(x, y, mySet);
                }
            }

            System.out.println(listOfSets.size());
            System.out.println(" ");
            
            long sum=0;
            for (Set<String> mySet : listOfSets) {
                System.out.println(" ");
                Map<String,Map<Integer,List<Integer>>> myMap = new HashMap<>();
                for (String item : mySet) {
                    String[] keySplit = item.split("-");
                    int x = Integer.parseInt(keySplit[0]);
                    int y = Integer.parseInt(keySplit[1]);
                    getPerimeter(x, y, myMap);
                }
                System.out.println(myMap);
                long perimeter = 0;

                for (Map.Entry<String, Map<Integer, List<Integer>>> outerEntry : myMap.entrySet()) {
                    Map<Integer, List<Integer>> innerMap = outerEntry.getValue();
                    for (Map.Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
                        int count = 0;
                        List<Integer> innerArray = innerEntry.getValue();
                        if (innerArray.size()>0) count++;

                        for (int k=0; k<innerArray.size()-1; k++) {
                            int current = innerArray.get(k);
                            int next = innerArray.get(k+1);
                            if (next-current!=1) {
                                count++;
                            }
                        }
                        perimeter += count;
                    }
                }

                System.out.println(perimeter);
                sum+= mySet.size()*perimeter;
            }
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private void recursiveFind(int x, int y, Set<String> mySet) {
        boolean completed = seen.contains(x+"-"+y);
        if (completed) {
            return;
        }
        char currentChar = matrix.get(x).get(y);
        seen.add(x+"-"+y);

        for (int[] direction: directions) {
            int newX = (int)x+direction[0];
            int newY = (int)y+direction[1];

            if (newX>=0&&newX<size&&newY>=0&&newY<size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar==c) {
                    mySet.add(newX+"-"+newY);
                    recursiveFind(newX, newY, mySet);
                }
            } else {
                continue;
            }
        }
    }

    private void getPerimeter(int x, int y, Map<String,Map<Integer,List<Integer>>> map1) {
        char currentChar = matrix.get(x).get(y);
        System.out.println(currentChar + "-" + x + "-" + y);

        for (int[] direction : directions) {
            int newX = x+direction[0];
            int newY = y+direction[1];

            if (newX>=0&&newX<size&&newY>=0&&newY<size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar!=c) {
                    String key = direction[0]+"-"+direction[1];
                    Map<Integer,List<Integer>> map2 = map1.get(key);
                    if (map2 == null) map2 = new HashMap<>();

                    int key2 = direction[0]==0 ? y : x;
                    int value2 = key2==x ? y : x;

                    List<Integer> mylist = map2.get(key2);
                    if (mylist==null) mylist = new ArrayList<>();
                    mylist.add(value2);
                    map2.put(key2, mylist);
                    map1.put(key, map2);
                }
            } else {
                String key = direction[0]+"-"+direction[1];
                Map<Integer,List<Integer>> map2 = map1.get(key);
                if (map2 == null) map2 = new HashMap<>();

                int key2 = direction[0]==0 ? y : x;
                int value2 = key2==x ? y : x;

                List<Integer> mylist = map2.get(key2);
                if (mylist==null) mylist = new ArrayList<>();
                mylist.add(value2);
                map2.put(key2, mylist);
                map1.put(key, map2);
            }
        }
    }
}
