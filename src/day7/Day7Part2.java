package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day7Part2 {
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
                // System.out.println("result");
                // System.out.println(result);
                List<Long> factors = factorsList.get(i);
                // System.out.println("factors");
                // System.out.println(factors);
                List<String> operatorsCombinations = generateOperatorsCombinations(factors.size());

                for (String operatorCombination : operatorsCombinations) {
                    // System.out.println("current combination of operators");
                    // System.out.println(operatorCombination);
                    Long equationResult = calculate1(factors, operatorCombination);
                    // System.out.println(result);
                    // System.out.println((long)equationResult == (long)result);

                    if ((long)equationResult == (long)result) {
                        // System.out.println("result matched");
                        // System.out.println(result);
                        sum+= result;
                        break;
                    }

                }
            }

            return sum;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private List<String> generateOperatorsCombinations(int factorsSize) {
        Integer n = factorsSize - 1;
        List<String> operatorGroups = new ArrayList<>();

        for (int i=0; i<Math.pow(2, n); i++) {
            String base3 = Long.toString(i, 2);
            String padded = String.format("%" + n + "s", base3).replace(' ', '0');
            operatorGroups.add(padded);
        }
        // System.out.println("operator groups");
        // System.out.println(operatorGroups);
        return operatorGroups;
    }

    private Long calculate1(List<Long> factors, String operatorsCombination) {
        Long equationResult = factors.get(0);

        for (int i=1; i<factors.size(); i++) {
            Long currentFactor = factors.get(i);
            char operator = operatorsCombination.charAt(i-1);
            // System.out.println("current operator");
            // System.out.println(operator);
            if (operator == '0') equationResult += currentFactor;
            if (operator == '1') equationResult *= currentFactor;
        }

        // System.out.println("equation result");
        // System.out.println(equationResult);
        return equationResult;
    }
}
