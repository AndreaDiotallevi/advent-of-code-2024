package day6;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day6Part2 {
    public List<List<Character>> matrix = new ArrayList<>();
    public List<String> directions = new ArrayList<>(Arrays.asList("u", "r", "d", "l"));

    public Point startingPoint = new Point();
    public String startingDirection;

    public int processFile() {
        try {
            File file = new File("resources/day6.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();

                List<Character> row = new ArrayList<>();

                for (int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    row.add(c);

                    if (c == '^')
                        startingDirection = "u";
                    if (c == '>')
                        startingDirection = "r";
                    if (c == 'v')
                        startingDirection = "d";
                    if (c == '<')
                        startingDirection = "l";

                    if (c == '^' || c == '>' || c == 'v' || c == '<') {
                        Point newPoint = new Point();
                        newPoint.setLocation(matrix.size(), i);
                        startingPoint.setLocation(newPoint);
                    }
                }

                matrix.add(row);
            }

            scanner.close();

            int sum = 0;

            for (int x = 0; x < matrix.size(); x++) {
                for (int y = 0; y < matrix.size(); y++) {
                    Character currentChar = matrix.get(x).get(y);
                    if (currentChar == '#')
                        continue;
                    if (currentChar == '^')
                        continue;
                    if (currentChar == '>')
                        continue;
                    if (currentChar == 'v')
                        continue;
                    if (currentChar == '<')
                        continue;

                    matrix.get(x).set(y, '#');

                    List<Point> travelledPoints = new ArrayList<>();
                    List<String> travelledDirections = new ArrayList<>();
                    travelledPoints.add(startingPoint);
                    travelledDirections.add(startingDirection);

                    Point currentPoint = new Point();
                    currentPoint.setLocation(startingPoint);
                    String currentDirection = startingDirection;

                    boolean finished = false;

                    while (!finished) {
                        Point point = new Point();

                        if (currentDirection == "u")
                            point.setLocation(currentPoint.x - 1, currentPoint.y);
                        if (currentDirection == "r")
                            point.setLocation(currentPoint.x, currentPoint.y + 1);
                        if (currentDirection == "d")
                            point.setLocation(currentPoint.x + 1, currentPoint.y);
                        if (currentDirection == "l")
                            point.setLocation(currentPoint.x, currentPoint.y - 1);

                        if (point.x < 0 || point.y < 0 || point.x == matrix.size() || point.y == matrix.size())
                            break;

                        if (matrix.get(point.x).get(point.y) == '#') {
                            int currentDirectionIndex = directions.indexOf(currentDirection);
                            currentDirection = directions.get((currentDirectionIndex + 1) % 4);
                        } else {
                            if (travelledPoints.contains(point)) {
                                int index = travelledPoints.indexOf(point);
                                String thisDirection = travelledDirections.get(index);
                                if (thisDirection == currentDirection) {
                                    sum += 1;
                                    System.out.println(sum);
                                    break;
                                }
                            }

                            travelledPoints.add(point);
                            travelledDirections.add(currentDirection);

                            currentPoint.setLocation(point);

                            if (point.x == matrix.size() - 1 || point.y == matrix.size() - 1)
                                finished = true;
                        }
                    }

                    matrix.get(x).set(y, '.');
                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
