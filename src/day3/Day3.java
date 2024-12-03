package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public int processFile() {
        try {
            File file = new File("resources/day3.txt");
            Scanner scanner = new Scanner(file);

            String regex = "mul\\((\\d+),(\\d+)\\)";
            Pattern pattern = Pattern.compile(regex);
            
            int sum = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String fullMatch = matcher.group(0);
                    
                    String firstInteger = matcher.group(1);
                    String secondInteger = matcher.group(2);
                    
                    sum += Integer.parseInt(firstInteger) * Integer.parseInt(secondInteger);
                    
                    System.out.printf("Pattern: %s -> Integers: %s, %s%n", fullMatch, firstInteger, secondInteger);
                }
            }
            
            scanner.close();
            return sum;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return -1;
        }
    }
}
