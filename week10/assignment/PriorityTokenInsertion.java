package week10.assignment;


public class PriorityTokenInsertion {

    // Custom Node class with data and next fields
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Singly Linked List implementation
    static class CustomLinkedList {
        Node head;
        int size;

        public CustomLinkedList() {
            this.head = null;
            this.size = 0;
        }

        /**
         * Inserts a token at the front of the list (priority).
         * Time Complexity: O(1)
         */
        public void addFirst(int token) {
            Node newNode = new Node(token);
            newNode.next = head;
            head = newNode;
            size++;
        }

        /**
         * Inserts a token at the end of the list (normal).
         * Time Complexity: O(n)
         */
        public void addLast(int token) {
            Node newNode = new Node(token);

            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }

        /**
         * Inserts a token at a specified 0-based index (rescheduled).
         * Time Complexity: O(n)
         */
        public void insertAt(int index, int token) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }

            // Inserting at index 0 is equivalent to addFirst
            if (index == 0) {
                addFirst(token);
                return;
            }

            // Traverse to the node immediately before the target index
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            // Create new node and link it into place
            Node newNode = new Node(token);
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }

        /**
         * Prints the linked list elements in order.
         */
        public void printList() {
            Node current = head;
            while (current != null) {
                System.out.print(current.data + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        CustomLinkedList list = new CustomLinkedList();

        // Initial list: 101 -> 102 -> 103
        list.addLast(101);
        list.addLast(102);
        list.addLast(103);

        // Sequence of operations from example:
        list.addFirst(100);
        list.addLast(104);
        list.insertAt(2, 150);

        // Expected Output: 100 -> 101 -> 150 -> 102 -> 103 -> 104 -> null
        list.printList();
    }
}
