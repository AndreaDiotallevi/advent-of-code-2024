package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
            File file = new File("resources/day12test3.txt");
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
                Set<String> mySetPerimeter = new HashSet<>();
                for (String item : mySet) {
                    String[] keySplit = item.split("-");
                    int x = Integer.parseInt(keySplit[0]);
                    int y = Integer.parseInt(keySplit[1]);
                    getPerimeter(x, y, mySetPerimeter);
                }
                long perimeter = mySetPerimeter.size();
                // System.out.println(perimeter);
                sum+= mySet.size()*perimeter;
            }
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private void recursiveFind(int x, int y, Set<String> mySet) {
        // System.out.printf("(%d,%d)%n",x,y);
        boolean completed = seen.contains(x+"-"+y);
        if (completed) {
            // System.out.println("was seen: "+x+"-"+y);
            return;
        }
        // System.out.println("seen does not contain: "+ x+"-"+y);
        char currentChar = matrix.get(x).get(y);
        seen.add(x+"-"+y);

        for (int[] direction: directions) {
            // System.out.println("====");
            // System.out.println("direction"+direction[0]+",,,"+direction[1]);
            // System.out.println("directions");
            // System.out.println(direction[0]);
            // System.out.println(direction[1]);
            int newX = (int)x+direction[0];
            int newY = (int)y+direction[1];
            // System.out.println("x & y");
            // System.out.println(x);
            // System.out.println(y);
            // System.out.println("newx & newy");
            // System.out.println(newX);
            // System.out.println(newY);
            // System.out.println("newx newy"+newX+",,,"+newY);

            if (newX>=0&&newX<size&&newY>=0&&newY<size) {
                // System.out.println("newx newy"+newX+",,,"+newY);
                // System.out.println("inside grid");
                char c = matrix.get(newX).get(newY);
                if (currentChar==c) {
                    // System.out.println(currentChar);
                    // System.out.println("add"+newX+"-"+newY);
                    mySet.add(newX+"-"+newY);
                    recursiveFind(newX, newY, mySet);
                }
                // System.out.println("not same char");
            } else {
                // System.out.println("out of grid");
                continue;
            }
        }
    }

    private void getPerimeter(int x, int y, Set<String> mySet) {
        char currentChar = matrix.get(x).get(y);
        // System.out.println(currentChar + "-" + x + "-" + y);

        for (int[] direction : directions) {
            int newX = x+direction[0];
            int newY = y+direction[1];

            if (newX>=0&&newX<size&&newY>=0&&newY<size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar!=c) {
                    boolean vertical = direction[0]==0;
                    if (vertical) {
                        // System.out.println("vertical");
                        mySet.add("h"+"-"+newY);
                    } else {
                        // System.out.println("horizontal");
                        mySet.add("v"+"-"+newX);
                    }
                }
            } else {
                boolean vertical = direction[0]==0;
                if (vertical) {
                    // System.out.println("vertical");
                    mySet.add("h"+"-"+newY);
                } else {
                    // System.out.println("horizontal");
                    mySet.add("v"+"-"+newX);
                }
            }
        }
    }
}
