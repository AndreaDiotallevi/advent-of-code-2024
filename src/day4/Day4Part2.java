package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4Part2 {
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

            for (int x = 1; x < xSize - 1; x++) {
                for (int y = 1; y < ySize - 1; y++) {
                    if (matrix.get(x).get(y) != 'A') {
                        // Do nothing
                    } else {
                        if (check(x, y)) {
                            sum += 1;
                        }
                    }
                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private boolean check(int x, int y) {
        char char1 = matrix.get(x + 1).get(y + 1);
        char char2 = matrix.get(x - 1).get(y - 1);

        if (!((char1 == 'M' && char2 == 'S') || (char1 == 'S' && char2 == 'M')))
            return false;

        char char3 = matrix.get(x + 1).get(y - 1);
        char char4 = matrix.get(x - 1).get(y + 1);

        if (!((char3 == 'M' && char4 == 'S') || (char3 == 'S' && char4 == 'M')))
            return false;

        return true;
    }
}
