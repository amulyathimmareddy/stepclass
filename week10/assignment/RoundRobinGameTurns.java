package week10.assignment;



public class RoundRobinGameTurns {

    // Custom Node class for the circular list
    static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
            this.next = null;
        }
    }

    // Circular Singly Linked List using a tail reference
    static class CircularLinkedList {
        Node tail;

        public CircularLinkedList() {
            this.tail = null;
        }

        /**
         * Adds a player to the circular linked list using a tail pointer.
         * The head is always tail.next.
         */
        public void addLast(String name) {
            Node newNode = new Node(name);

            if (tail == null) {
                // First element points to itself
                tail = newNode;
                tail.next = tail;
            } else {
                // Insert after tail and update tail to the new node
                newNode.next = tail.next;
                tail.next = newNode;
                tail = newNode;
            }
        }

        /**
         * Simulates turns in a circle and prints who plays each turn.
         */
        public void printTurns(int turns) {
            if (tail == null || turns <= 0) {
                return;
            }

            // In a circular list, the head (first player) is tail.next
            Node current = tail.next;

            for (int i = 0; i < turns; i++) {
                System.out.print(current.name);
                if (i < turns - 1) {
                    System.out.print(" ");
                }
                current = current.next; // Wraps around automatically
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CircularLinkedList game = new CircularLinkedList();

        // Sample input: players = Asha, Ravi, Neha; turns = 7
        game.addLast("Asha");
        game.addLast("Ravi");
        game.addLast("Neha");

        // Expected Output: Asha Ravi Neha Asha Ravi Neha Asha
        game.printTurns(7);
    }
}
