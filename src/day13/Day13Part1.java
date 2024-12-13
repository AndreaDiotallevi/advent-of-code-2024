package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Part1 {
    public class Button {
        long x;
        long y;
        long tokens;

        public Button(long x, long y, long tokens) {
            this.x = x;
            this.y = y;
            this.tokens = tokens;
        }

        @Override
        public String toString() {
            return "Button{" +
                    "x=" + x +
                    ", y=" + y +
                    ", tokens=" + tokens +
                    '}';
        }
    }

    public class Machine {
        List<Button> buttons;
        long x;
        long y;

        public Machine(List<Button> buttons, long x, long y) {
            this.buttons = buttons;
            this.x = x;
            this.y = y;
        }

        public void print() {
            System.out.println(buttons);
            System.out.println(this.x + "-" + this.y);
        }
    }

    List<Machine> machines = new ArrayList<>();

    public long processFile() {
        try {
            File file = new File("resources/day13.txt");
            Scanner scanner = new Scanner(file);

            List<Button> buttons = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String regexButton = "Button (A|B): X\\+(\\d+), Y\\+(\\d+)";
                Pattern patternButton = Pattern.compile(regexButton);
                Matcher matcherButton = patternButton.matcher(line);

                String regexPrize = "X=(\\d+), Y=(\\d+)";
                Pattern patternPrize = Pattern.compile(regexPrize);
                Matcher matcherPrize = patternPrize.matcher(line);

                if (matcherButton.find()) {
                    String buttonType = matcherButton.group(1);
                    int xValue = Integer.parseInt(matcherButton.group(2));
                    int yValue = Integer.parseInt(matcherButton.group(3));

                    Button button = new Button(xValue, yValue, buttonType.equals("A") ? 3 : 1);
                    buttons.add(button);
                }
                if (matcherPrize.find()) {
                    int xValue = Integer.parseInt(matcherPrize.group(1));
                    int yValue = Integer.parseInt(matcherPrize.group(2));

                    Machine machine = new Machine(buttons, xValue, yValue);
                    machines.add(machine);
                    buttons = new ArrayList<>();
                }

            }
            scanner.close();

            int maxPresses = 100;
            long result = 0;

            for (Machine machine : machines) {
                machine.print();
                Button buttonA = machine.buttons.get(0);
                Button buttonB = machine.buttons.get(1);
                long minPrizeTokens = 0;
                boolean prizeFound = false;

                for (int a = 0; a < maxPresses; a++) {
                    for (int b = 0; b < maxPresses; b++) {
                        long sumX = buttonA.x * a + buttonB.x * b;
                        long sumY = buttonA.y * a + buttonB.y * b;
                        if (sumX == machine.x && sumY == machine.y) {
                            System.out.printf("%d %d %n", a, b);
                            prizeFound = true;
                            long tokens = buttonA.tokens * a + buttonB.tokens * b;
                            System.out.println(tokens);
                            if (tokens < minPrizeTokens || minPrizeTokens == 0) {
                                minPrizeTokens = tokens;
                                System.out.println("here");
                                System.out.println(minPrizeTokens);
                            }
                        }
                    }
                }

                if (prizeFound) {
                    System.out.println("found");
                    System.out.println(minPrizeTokens);
                    result += minPrizeTokens;
                }
            }

            return result;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
