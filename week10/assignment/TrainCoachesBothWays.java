package week10.assignment;


public class TrainCoachesBothWays {

    // Custom Doubly Linked List Node
    static class Node {
        String name;
        Node prev;
        Node next;

        Node(String name) {
            this.name = name;
            this.prev = null;
            this.next = null;
        }
    }

    // Doubly Linked List with head and tail references
    static class DoublyLinkedList {
        Node head;
        Node tail;

        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
        }

        /**
         * Appends a coach to the end (guard end) of the train.
         */
        public void addLast(String name) {
            Node newNode = new Node(name);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }

        /**
         * Finds and unlinks a coach node by name.
         */
        public void remove(String name) {
            if (head == null || name == null) {
                return;
            }

            Node current = head;

            // Search for the node with the matching name
            while (current != null && !current.name.equals(name)) {
                current = current.next;
            }

            // Coach not found
            if (current == null) {
                return;
            }

            // If the node to delete is the head
            if (current == head) {
                head = head.next;
                if (head != null) {
                    head.prev = null;
                } else {
                    tail = null; // List became empty
                }
            } 
            // If the node to delete is the tail
            else if (current == tail) {
                tail = tail.prev;
                if (tail != null) {
                    tail.next = null;
                } else {
                    head = null; // List became empty
                }
            } 
            // Node is somewhere in the middle: fix both neighbors
            else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
        }

        /**
         * Prints the train from the engine end to the guard end[cite: 21].
         */
        public void printForward() {
            System.out.print("Forward: ");
            Node current = head;
            while (current != null) {
                System.out.print(current.name);
                if (current.next != null) {
                    System.out.print(" <-> ");
                }
                current = current.next;
            }
            System.out.println();
        }

        /**
         * Prints the train from the guard end to the engine end[cite: 21].
         */
        public void printBackward() {
            System.out.print("Backward: ");
            Node current = tail;
            while (current != null) {
                System.out.print(current.name);
                if (current.prev != null) {
                    System.out.print(" <-> ");
                }
                current = current.prev;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DoublyLinkedList train = new DoublyLinkedList();

        // Sample input: coaches = Engine, A1, B1, B2, Guard; remove B1[cite: 21]
        train.addLast("Engine");
        train.addLast("A1");
        train.addLast("B1");
        train.addLast("B2");
        train.addLast("Guard");

        train.remove("B1");

        // Expected output display[cite: 21]
        train.printForward();
        train.printBackward();
    }
}
