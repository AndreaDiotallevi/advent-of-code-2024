package day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17Part2 {
    public static List<Long> part2(List<Integer> program, List<Long> solutions) {
        if (program.isEmpty()) {
            return solutions;
        }

        int output = program.get(0);
        List<Integer> rest = program.subList(1, program.size());

        List<Long> nextSolutions = new ArrayList<>();
        for (Long solutionSoFar : solutions) {
            for (int candidateValue = 0; candidateValue < 8; candidateValue++) {
                long a = (solutionSoFar << 3) ^ candidateValue;

                long b1 = (long) a % 8;
                long b2 = b1 ^ 6;
                long c1 = (long) Math.floor(a / Math.pow(2, b2));
                long b3 = b2 ^ c1;
                long b4 = b3 ^ 7;
                boolean matching = (b4 % 8) == output;

                if (matching) {
                    nextSolutions.add(a);
                }
            }
        }

        return part2(rest, nextSolutions);
    }

    public static void run() {
        List<Long> initialSolutions = new ArrayList<>();
        initialSolutions.add(0L);

        List<Integer> program = new ArrayList<>(Arrays.asList(2, 4, 1, 6, 7, 5, 4, 4, 1, 7, 0, 3, 5, 5, 3, 0));
        List<Integer> reversedProgram = new ArrayList<>(program);
        java.util.Collections.reverse(reversedProgram);

        List<Long> allSolutions = part2(reversedProgram, initialSolutions);
        System.out.println(allSolutions);
    }
}
