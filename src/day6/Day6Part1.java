package day6;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day6Part1 {
    public List<List<Character>> matrix = new ArrayList<>();
    public List<String> directions = new ArrayList<>(Arrays.asList("up", "right", "down", "left"));
    public int currentDirectionIndex;
    public List<Point> travelledPointsSet = new ArrayList<>();
    public Point currentPoint = new Point();

    public int processFile() {
        try {
            File file = new File("resources/day6.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();

                List<Character> row = new ArrayList<>();

                for (int i=0; i<charArray.length; i++) {
                    char c = charArray[i];
                    row.add(c);
                    if (c == '^') currentDirectionIndex = 0; 
                    if (c == '>') currentDirectionIndex = 1; 
                    if (c == 'v') currentDirectionIndex = 2; 
                    if (c == '<') currentDirectionIndex = 3; 

                    if (c == '^' || c == '>' || c == 'v' || c == '<') {
                        currentPoint.setLocation(matrix.size(), i);
                        travelledPointsSet.add(currentPoint);
                        // System.out.println(currentPoint);
                    }
                }

                matrix.add(row);
            }

            scanner.close();

            int size = matrix.size();

            boolean finished = false;

            while (!finished) {
                String currentDirection = directions.get(currentDirectionIndex % 4);
                Point nextPoint = new Point();
                
                if (currentDirection=="up") nextPoint.setLocation(currentPoint.x-1, currentPoint.y);
                if (currentDirection=="right") nextPoint.setLocation(currentPoint.x, currentPoint.y+1);
                if (currentDirection=="down") nextPoint.setLocation(currentPoint.x+1, currentPoint.y);
                if (currentDirection=="left") nextPoint.setLocation(currentPoint.x, currentPoint.y-1);

                if (matrix.get(nextPoint.x).get(nextPoint.y)=='#') {
                    currentDirectionIndex++;
                    // System.out.println(directions.get(currentDirectionIndex%4));
                } else {
                    if (!travelledPointsSet.contains(nextPoint))  travelledPointsSet.add(nextPoint);
                   
                    currentPoint.setLocation(nextPoint);
                    if (currentPoint.x==size-1 || currentPoint.y==size-1) finished = true;
                }
            }

            return travelledPointsSet.size();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
