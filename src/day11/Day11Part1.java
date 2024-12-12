package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day11Part1 {
    public class MyLinkedlist {
        class Node {
            long value;
            Node next;

            public Node(long value) {
                this.value = value;
                this.next = null;
            }

            public void setNext(Node node) {
                this.next = node;
            }
        }

        Node head;
        Node tail;
        long size;

        public MyLinkedlist() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public void append(long value) {
            // System.out.println(value);
            Node newNode = new Node(value);
            if (size == 0) {
                this.head = newNode;
                this.tail = newNode;
            } else {
                this.tail.next = newNode;
                this.tail = newNode;
            }
            this.size++;
        }

        public void printList() {
            List<Long> printArray = new ArrayList<>();
            Node currentNode = this.head;

            while (currentNode != null) {
                printArray.add(currentNode.value);
                currentNode = currentNode.next;
            }

            // System.out.println(printArray);
        }

        public void changeStones() {
            Node currentNode = this.head;
            Node previousNode = null;

            while (currentNode != null) {
                if (currentNode.value == 0) {
                    // System.out.println("node is zero");
                    currentNode.value = 1;
                    // System.out.println("current node value");
                    // System.out.println(currentNode.value);
                    Node tempNext = currentNode.next;
                    // System.out.println("temp next");
                    // System.out.println(tempNext.value);
                    previousNode = currentNode;
                    currentNode = tempNext;
                    // System.out.println(" ");
                    // System.out.println(previousNode.value);
                    continue;
                }

                String integerString = String.valueOf(currentNode.value);
                int integerStringLength = integerString.length();

                if (integerStringLength % 2 == 0) {
                    // System.out.println("event digits");
                    String subString1 = integerString.substring(0, integerStringLength / 2);
                    String subString2 = integerString.substring(integerStringLength / 2);
                    Node newNode1 = new Node(Long.parseLong(subString1));
                    Node newNode2 = new Node(Long.parseLong(subString2));
                    Node tempNext = currentNode.next;
                    if (previousNode != null) {
                        previousNode.next = newNode1;
                    } else {
                        this.head = newNode1;
                    }
                    previousNode = newNode2;
                    newNode1.next = newNode2;
                    newNode2.next = tempNext;
                    currentNode = tempNext;
                    this.size++;
                    continue;
                }

                currentNode.value = currentNode.value * 2024;
                Node tempNext = currentNode.next;
                previousNode = currentNode;
                currentNode = tempNext;
            }
        }
    }

    public long processFile() {
        MyLinkedlist mylist = new MyLinkedlist();

        try {
            // Create initial linked list
            File file = new File("resources/day11.txt");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] stringArray = line.split(" ");
            for (String s : stringArray) {
                mylist.append(Long.parseLong(s));
            }
            scanner.close();
            mylist.printList();

            // Loop n times
            long times = 25;
            for (long i = 0; i < times; i++) {
                // System.out.println(i);
                mylist.changeStones();
                mylist.printList();
                // System.out.println(" ");
            }

            // long sum=0;
            // return sum;
            return mylist.size;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
