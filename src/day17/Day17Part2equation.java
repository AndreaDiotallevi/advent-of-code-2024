package day17;

import java.util.*;

public class Day17Part2equation {
    public static void processFile() {
        List<Integer> program = new ArrayList<>(Arrays.asList(0, 3, 5, 4, 3, 0));

        // Long a = 0L;
        // boolean exit = false;
        Map<Integer, Integer> myMap = new HashMap<>();

        for (int a = 0; a < 80; a++) {
            // System.out.println("a=" + a);
            for (int n : program) {
                Long b1 = (long) a % 8;
                Long b2 = b1 ^ 6;
                Long c1 = (long) Math.floor(a / Math.pow(2, b2));
                Long b3 = b2 ^ c1;
                Long b4 = b3 ^ 7;
                boolean matching = (b4 % 8) == n;
                // System.out.println(matching);
                if (matching) {
                    System.out.println();
                    System.out.println("a=" + a);
                    System.out.println("n=" + n);
                    myMap.put(a, n);
                    break;
                    // a = a1;
                    // break;
                }
            }
        }

        System.out.println(myMap);

        // for (int i = program.size() - 1; i >= 0; i--) {
        // num = 8 * num + program.get(i);
        // System.out.println(num);
        // }
        // num *= 8;
        // System.out.println(a);
        // return a;
    }

    // public static void processFile() {
    // findAFromOutput(0);
    // }

    // public static int findAFromOutput(int targetOutput) {
    // for (int a = 0; a < 30; a++) { // Arbitrary range, can be adjusted
    // int b = a % 8;
    // int c = b ^ 6;
    // int shiftedA = a >> c;
    // int calculatedOutput = ((c ^ shiftedA) ^ 7) % 8;

    // System.out.println(a + "-" + calculatedOutput);

    // // if (calculatedOutput == targetOutput) {
    // // System.out.println(a);
    // // return a;
    // // }
    // }
    // return -1; // Return -1 if no solution is found
    // }
}
