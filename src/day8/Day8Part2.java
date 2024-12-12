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

public class Day8Part2 {
    public List<List<Character>> matrix = new ArrayList<>();
    public Map<Character, List<List<Integer>>> myMap = new HashMap<>();
    public Set<String> occurrencesMap = new HashSet<>();

    public int processFile() {
        try {
            File file = new File("resources/day8.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                List<Character> myList = new ArrayList<>();
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    myList.add(c);
                    if (c != '.') {
                        List<List<Integer>> mapItem = myMap.get(c);
                        if (mapItem == null)
                            myMap.put(c, new ArrayList<>());
                        myMap.get(c).add(new ArrayList<>(Arrays.asList(matrix.size(), i)));
                    }
                }

                matrix.add(myList);
            }

            scanner.close();
            int matrixSize = matrix.size();

            for (Map.Entry<Character, List<List<Integer>>> entry : myMap.entrySet()) {
                Character key = entry.getKey();
                List<List<Integer>> coordinates = entry.getValue();

                if (coordinates.size() > 1) {
                    for (List<Integer> c : coordinates) {
                        occurrencesMap.add(String.valueOf(c.get(0)) + "-" + String.valueOf(c.get(1)));
                    }
                }

                int poolSize = coordinates.size();
                int i = 0;

                while (i < poolSize - 1) {
                    for (int j = i + 1; j < poolSize; j++) {
                        List<Integer> first = coordinates.get(i);
                        List<Integer> second = coordinates.get(j);

                        int xDiff = Math.abs(first.get(0) - second.get(0));
                        int yDiff = Math.abs(first.get(1) - second.get(1));

                        boolean finished = false;
                        int multiplier = 1;
                        while (!finished) {
                            List<Integer> candidate = new ArrayList<>();
                            candidate.add(0, (first.get(0) < second.get(0)) ? (first.get(0) - xDiff * multiplier)
                                    : (first.get(0) + xDiff * multiplier));
                            candidate.add(1, (first.get(1) < second.get(1)) ? (first.get(1) - yDiff * multiplier)
                                    : (first.get(1) + yDiff * multiplier));

                            if (candidate.get(0) >= 0 &&
                                    candidate.get(1) >= 0 &&
                                    candidate.get(0) < matrixSize &&
                                    candidate.get(1) < matrixSize) {
                                if (matrix.get(candidate.get(0)).get(candidate.get(1)) != key) {
                                    occurrencesMap.add(
                                            String.valueOf(candidate.get(0)) + "-" + String.valueOf(candidate.get(1)));
                                }
                                multiplier++;
                            } else {
                                finished = true;
                            }
                        }

                        finished = false;
                        multiplier = 1;

                        while (!finished) {
                            List<Integer> candidate = new ArrayList<>();
                            candidate.add(0, (first.get(0) < second.get(0)) ? (second.get(0) + xDiff * multiplier)
                                    : (second.get(0) - xDiff * multiplier));
                            candidate.add(1, (first.get(1) < second.get(1)) ? (second.get(1) + yDiff * multiplier)
                                    : (second.get(1) - yDiff * multiplier));

                            if (candidate.get(0) >= 0 &&
                                    candidate.get(1) >= 0 &&
                                    candidate.get(0) < matrixSize &&
                                    candidate.get(1) < matrixSize) {
                                if (matrix.get(candidate.get(0)).get(candidate.get(1)) != key) {
                                    occurrencesMap.add(
                                            String.valueOf(candidate.get(0)) + "-" + String.valueOf(candidate.get(1)));
                                }
                                multiplier++;
                            } else {
                                finished = true;
                            }
                        }
                    }
                    i++;
                }
            }

            return occurrencesMap.size();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
