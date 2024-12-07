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
            int radix = 3;

            for (int i=0; i<resultList.size(); i++) {
                Long result = resultList.get(i);
                List<Long> factors = factorsList.get(i);

                Long binarySize = (long)factors.size()-1;

                for (int j=0; j<Math.pow(radix, binarySize); j++) {
                    String base3 = Long.toString(j, radix);
                    String padded = String.format("%" + binarySize + "s", base3).replace(' ', '0');

                    long equationResult = factors.get(0);

                    for (int k=1; k<factors.size(); k++) {
                        long currentFactor = factors.get(k);
                        char operator = padded.charAt(k-1);
                        if (operator == '0') equationResult += currentFactor;
                        if (operator == '1') equationResult *= currentFactor;
                        if (operator == '2') {
                            String str = String.valueOf(equationResult) + String.valueOf(currentFactor);
                            Long num = Long.parseLong(str);
                            equationResult = num;
                        }
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
