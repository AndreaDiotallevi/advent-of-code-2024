package day21;

import java.awt.Point;
// import java.io.*;
import java.util.*;

public class Day21Part1 {
    public static List<Keypad> keypads = new ArrayList<>();

    public static class Keypad {
        public char[][] buttons;
        public Point current;
        public List<Point> directions = new ArrayList<>(Arrays.asList(
                new Point(0, 1),
                new Point(1, 0),
                new Point(0, -1),
                new Point(-1, 0)));

        public Keypad(char[][] buttons, Point current) {
            this.buttons = buttons;
            this.current = current;
        }

        public List<Point> getShortestPath(char c) {
            return new ArrayList<>();
        }

        public List<Character> translatePathToChars() {
            return new ArrayList<>();
        }

        public boolean isValid(int x, int y) {
            return (x >= 0 && y >= 0 && x <= buttons.length - 1 && y <= buttons[0].length - 1 && buttons[x][y] != '#');
        }

        public void printButtons() {
            for (int x = 0; x < this.buttons.length; x++) {
                for (int y = 0; y < this.buttons[0].length; y++) {
                    System.out.print(buttons[x][y]);
                }
                System.out.println();
            }
            System.out.println("Current is: " + buttons[current.x][current.y]);
            System.out.println();
        }
    }

    public static void setup() {
        char[][] numericKeypadButtons = new char[4][3];
        numericKeypadButtons[0][0] = '7';
        numericKeypadButtons[0][1] = '8';
        numericKeypadButtons[0][2] = '9';
        numericKeypadButtons[1][0] = '4';
        numericKeypadButtons[1][1] = '5';
        numericKeypadButtons[1][2] = '6';
        numericKeypadButtons[2][0] = '1';
        numericKeypadButtons[2][1] = '2';
        numericKeypadButtons[2][2] = '3';
        numericKeypadButtons[3][0] = '#';
        numericKeypadButtons[3][1] = '0';
        numericKeypadButtons[3][2] = 'A';
        Keypad numericKeypad = new Keypad(numericKeypadButtons, new Point(3, 2));
        keypads.add(numericKeypad);
        numericKeypad.printButtons();

        char[][] directionalKeypadButtons = new char[2][3];
        directionalKeypadButtons[0][0] = '#';
        directionalKeypadButtons[0][1] = '^';
        directionalKeypadButtons[0][2] = 'A';
        directionalKeypadButtons[1][0] = '<';
        directionalKeypadButtons[1][1] = 'v';
        directionalKeypadButtons[1][2] = '>';
        Keypad directionalKeypad1 = new Keypad(directionalKeypadButtons, new Point(0, 2));
        Keypad directionalKeypad2 = new Keypad(directionalKeypadButtons, new Point(0, 2));
        Keypad directionalKeypad3 = new Keypad(directionalKeypadButtons, new Point(0, 2));
        keypads.add(directionalKeypad1);
        keypads.add(directionalKeypad2);
        keypads.add(directionalKeypad3);
        directionalKeypad1.printButtons();
    }

    public static void run() {
        setup();
    }
}
