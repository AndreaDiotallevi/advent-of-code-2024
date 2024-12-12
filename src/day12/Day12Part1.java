package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day12Part1 {
    public List<Group> groups = new ArrayList<>();
    public Map<String,Group> myMap = new HashMap<>();
    public List<List<Character>> matrix = new ArrayList<>();

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
            File file = new File("resources/day12test.txt");
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
            long sum=0;
            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
