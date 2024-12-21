package day17;

import java.util.*;

public class Day17Part1 {
    static class Computer {
        List<Integer> program;
        Long registerA;
        Long registerB;
        Long registerC;
        Integer instructionPointer;
        String output;
        boolean increaseInstructionPointer;

        public Computer(Long registerA, Long registerB, Long registerC, List<Integer> program) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.program = program;
            this.output = "";
            this.instructionPointer = 0;
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
                    System.out.println(result5A);
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

    public static String processFile() {
        List<Integer> program = new ArrayList<>(Arrays.asList(2, 4, 1, 6, 7, 5, 4, 4, 1, 7, 0, 3, 5, 5, 3, 0));
        Computer computer = new Computer(47910079998866L, 0L, 0L, program);

        Integer instructionPointer = 0;
        while (instructionPointer < program.size() - 1) {
            Integer opcode = program.get(instructionPointer);
            Integer operand = program.get(instructionPointer + 1);
            computer.runInstruction(opcode, operand);
            instructionPointer = computer.instructionPointer;
        }
        System.out.println(computer.output);
        return computer.output;
    }
}
