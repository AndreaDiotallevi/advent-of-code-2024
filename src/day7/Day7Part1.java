package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day7Part1 {
    public List<Long> resultList = new ArrayList<>();
    public List<List<Long>> factorsList = new ArrayList<>();

    public long processFile() {
        try {
            File file = new File("resources/day7.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(": ");
                resultList.add(Long.parseLong(values[0]));
                String[] factorsString = values[1].split(" ");
                List<Long> item = new ArrayList<>();
                for (String s : factorsString) item.add(Long.parseLong(s));
                factorsList.add(item);
            }
            scanner.close();

            long sum = 0;

            for (int i=0; i<resultList.size(); i++) {
                Long result = resultList.get(i);
                List<Long> factors = factorsList.get(i);

                System.out.println(result);
                System.out.println(factors);

                Long binarySize = (long)factors.size()-1;

                for (int j=0; j<Math.pow(2, binarySize); j++) {
                    String paddedBinary = String.format("%" + binarySize + "s", Long.toBinaryString(j)).replace(' ', '0');
                    String operatorsString = paddedBinary.replace('0', '+').replace('1', '*');
                    // System.out.println(operatorsString);

                    long equationResult = factors.get(0);
                    // System.out.println(equationResult);

                    for (int k=1; k<factors.size(); k++) {
                        long currentFactor = factors.get(k);
                        // System.out.println(currentFactor);
                        char operator = operatorsString.charAt(k-1);
                        if (operator == '+') equationResult += currentFactor;
                        if (operator == '*') equationResult *= currentFactor;
                        if (equationResult > result) break;
                    }

                    if (equationResult == result) {
                        sum += result;
                        break;
                    }
                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
