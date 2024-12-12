package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day12Part1 {
    public List<Group> groups = new ArrayList<>();
    public Map<String,Group> myMap = new HashMap<>();
    public List<List<Character>> matrix = new ArrayList<>();
    public int size;
    List<int[]> directions = new ArrayList<>(Arrays.asList(
        new int[]{0, 1},
        new int[]{1, 0},
        new int[]{0, -1},
        new int[]{-1, 0}
    ));

    class Group {
        long area;
        long perimeter;
        public Group() {
            this.area=0;
            this.perimeter=0;
        }
    }
    
    public long processFile() {
        try {
            File file = new File("resources/day12test2.txt");
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
            System.out.println(matrix);
            size = matrix.size();
            System.out.println(" ");

            for (int x=0; x<size; x++) {
                for (int y=0; y<size; y++) {
                    Group group = getGroup(x, y);
                    int perimeter = getPerimeter(x, y);
                    group.area++;
                    group.perimeter+=perimeter;
                }
            }

            System.out.println(groups.size());
            System.out.println(" ");
            long sum=0;
            for (Group group : groups) {
                sum+=group.area*group.perimeter;
                System.out.println(group.area+"-"+group.perimeter);
            }
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private Group getGroup(int x, int y) {
        System.out.printf("(%d,%d)%n",x,y);
        char currentChar = matrix.get(x).get(y);

        for (int[] direction : directions) {
            // System.out.println(direction);
            int newX = x+direction[0];
            int newY = y+direction[1];
            // System.out.println(newX+"-"+newY);

            if (newX>=0&&newX<size&&newY>=0&&newY<size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar==c) {
                    Group group = myMap.get(newX + "-" + newY);
                    if (group!=null) {
                        System.out.println("found group"+newX+"-"+newY);
                        myMap.put(x+"-"+y, group);
                        return group;
                    } 
                }
            }
        }
        System.out.println("new group");
        Group newGroup = new Group();
        myMap.put(x+"-"+y, newGroup);
        groups.add(newGroup);
        return newGroup;
    }

    private int getPerimeter(int x, int y) {
        // System.out.printf("(%d,%d)%n",x,y);
        char currentChar = matrix.get(x).get(y);
        int perimeter = 4;

        for (int[] direction : directions) {
            int newX = x+direction[0];
            int newY = y+direction[1];

            if (newX>=0&&newX<size&&newY>=0&&newY<size) {
                char c = matrix.get(newX).get(newY);
                if (currentChar==c) {
                    perimeter--;
                }
            }
        }
        return perimeter;
    }
}
