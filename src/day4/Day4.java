package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    public List<Character> word = new ArrayList<>(Arrays.asList('X', 'M', 'A', 'S'));
    public List<List<Character>> matrix = new ArrayList<>();
    public int xSize = 0;
    public int ySize = 0;

    public int processFile() {
        try {
            File file = new File("resources/day4.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    row.add(c);
                }
                matrix.add(row);
            }
            scanner.close();
            xSize = matrix.size();
            ySize = matrix.get(0).size();

            int sum = 0;

            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    if (right(x, y))
                        sum += 1;
                    if (left(x, y))
                        sum += 1;
                    if (up(x, y))
                        sum += 1;
                    if (down(x, y))
                        sum += 1;
                    if (rightDown(x, y))
                        sum += 1;
                    if (rightUp(x, y))
                        sum += 1;
                    if (leftDown(x, y))
                        sum += 1;
                    if (leftUp(x, y))
                        sum += 1;
                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private boolean right(int x, int y) {
        if (y > ySize - 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x).get(y + i) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    // MMMSXXMASM
    // 0123456789
    // Size 10

    private boolean left(int x, int y) {
        if (y < 3) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x).get(y - i) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean down(int x, int y) {
        if (x > xSize - 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x + i).get(y) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean up(int x, int y) {
        if (x < 3) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x - i).get(y) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean rightDown(int x, int y) {
        if (y > ySize - 4 || x > xSize - 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x + i).get(y + i) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean rightUp(int x, int y) {
        if (y > ySize - 4 || x < 3) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x - i).get(y + i) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean leftDown(int x, int y) {
        if (y < 3 || x > xSize - 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x + i).get(y - i) != word.get(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean leftUp(int x, int y) {
        if (y < 3 || x < 3) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (matrix.get(x - i).get(y - i) != word.get(i)) {
                return false;
            }
        }

        return true;
    }
}
