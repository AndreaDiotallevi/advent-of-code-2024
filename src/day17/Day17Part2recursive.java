package day17;

import java.util.*;
import java.util.stream.Collectors;

public class Day17Part2recursive {
    public static Map<String, String> myMap = new HashMap<>();

    public static List<Integer> program = new ArrayList<>(
            Arrays.asList(2, 4, 1, 6, 7, 5, 4, 4, 1, 7, 0, 3, 5, 5, 3, 0));

    public static String programString = program.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));

    static class Computer {
        Long registerA;
        Long registerB;
        Long registerC;
        Integer instructionPointer;
        String output;
        boolean increaseInstructionPointer;

        public Computer(Long registerA, Long registerB, Long registerC, Integer instructionPointer, String output) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.output = output;
            this.instructionPointer = instructionPointer;
        }

        public void runInstruction(Integer opcode, Integer literalOperand) {
            Long comboOperand = getComboOperand(literalOperand);
            int jumpIndex = 2;
            switch (opcode) {
                case 0:
                    double result0A = registerA / (Math.pow(2, comboOperand));
                    long result0B = (long) Math.floor(result0A);
                    this.registerA = result0B;
                    break;
                case 1:
                    long result1A = registerB ^ literalOperand;
                    this.registerB = result1A;
                    break;
                case 2:
                    long result2A = comboOperand % 8;
                    this.registerB = result2A;
                    break;
                case 3:
                    if (registerA == 0L) {
                        break;
                    }
                    this.instructionPointer = literalOperand;
                    // System.out.println("pointer changed to=" + literalOperand);
                    jumpIndex = 0;
                    break;
                case 4:
                    long result4A = registerB ^ registerC;
                    this.registerB = result4A;
                    break;
                case 5:
                    long result5A = comboOperand % 8;
                    if (this.output.isEmpty()) {
                        this.output = String.valueOf(result5A);
                    } else {
                        this.output += "," + String.valueOf(result5A);
                    }
                    // System.out.println(result5A);
                    break;
                case 6:
                    double result6A = registerA / (Math.pow(2, comboOperand));
                    long result6B = (long) Math.floor(result6A);
                    this.registerB = result6B;
                    break;
                case 7:
                    double result7A = registerA / (Math.pow(2, comboOperand));
                    long result7B = (long) Math.floor(result7A);
                    this.registerC = result7B;
                    break;

                default:
                    throw new Error("opcode out of range = " + opcode);
            }
            this.instructionPointer += jumpIndex;
        }

        public Long getComboOperand(Integer literalOperand) {
            if (literalOperand <= 3)
                return (long) literalOperand;

            if (literalOperand == 4)
                return registerA;

            if (literalOperand == 5)
                return registerB;

            if (literalOperand == 6)
                return registerC;

            return (long) literalOperand;
        }
    }

    public static Long processFile() {
        Long num = 35184372088832L;
        // Long num = 0L;
        boolean exit = false;

        while (!exit) {
            num++;
            // System.out.println(num);
            String result = recursive(num, 0L, 0L, 0, "");
            if (programString.equals(result)) {
                System.out.println("TRUE");
                exit = true;
                break;
            }
            // if (num == 1835L) {
            // break;
            // }
            // if (num == 117440L) {
            // break;
            // }
            // if (!programString.startsWith(result)) {
            // continue;
            // }
        }

        System.out.println(num);
        return num;

        // String result = recursive(117440L, 0L, 0L, 0, "");
        // System.out.println(result);
        // System.out.println(programString.equals(result));
        // return 0L;
    }

    public static String recursive(Long registerA, Long registerB, Long registerC, Integer instructionPointer,
            String output) {

        if (instructionPointer >= program.size() - 1) {
            return output;
        }
        // System.out.println(output);
        // if (output.length() > 2) {
        // System.out.println("before");
        // System.out.println(output);
        // }

        // if (!programString.endsWith(output)) {
        // System.out.println(output);
        // return "NOT";
        // }

        String mapKey = registerA + "-" + registerB + "-" + registerC + "-" + instructionPointer;

        if (myMap.containsKey(mapKey)) {
            // System.out.println("in map");
            // System.out.println(mapKey);
            // System.out.println(myMap.get(mapKey));
            if (output.isEmpty()) {
                return myMap.get(mapKey);
            } else {
                return output + "," + myMap.get(mapKey);
            }
        } else {
            Computer computer = new Computer(registerA, registerB, registerC, instructionPointer, output);
            Integer opcode = program.get(instructionPointer);
            Integer operand = program.get(instructionPointer + 1);
            computer.runInstruction(opcode, operand);
            String result = recursive(computer.registerA, computer.registerB, computer.registerC,
                    computer.instructionPointer,
                    computer.output);

            // System.out.println("after");
            // System.out.println(result);
            // System.out.println(programString.endsWith(result));

            myMap.put(mapKey, result);
            return result;
        }
    }
}

// 1,2,0,1,7,4,1,0,3
