package day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17Part2correct {
    public static Long processFile() {
        List<Integer> program = new ArrayList<>(Arrays.asList(0, 3, 5, 4, 3, 0));
        Long num = 0L;
        for (int i = program.size() - 1; i >= 0; i--) {
            num = 8 * num + program.get(i);
            System.out.println(num);
        }
        num *= 8;
        System.out.println(num);
        return num;
    }
}
