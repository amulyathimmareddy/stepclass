package week10.assignment;



public class CancelledOrdersCleanup {

    // Custom Node class with data and next fields
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Removes all nodes with the given value and returns the (possibly new) head.
     */
    public static Node removeAll(Node head, int code) {
        // Step 1: Remove matching nodes from the front by advancing head
        while (head != null && head.data == code) {
            head = head.next;
        }

        // If the entire list consisted of target code nodes[cite: 27]
        if (head == null) {
            return null;
        }

        // Step 2: Walk with a previous reference and bypass matching nodes[cite: 27]
        Node current = head;
        while (current.next != null) {
            if (current.next.data == code) {
                // Skip the matching node[cite: 27]
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return head;
    }

    // Helper method to print the linked list in the required format[cite: 27]
    public static void printList(Node head) {
        if (head == null) {
            System.out.println("null");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Helper method to create a linked list from an array
    public static Node buildList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }

        Node head = new Node(values[0]);
        Node current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new Node(values[i]);
            current = current.next;
        }
        return head;
    }

    public static void main(String[] args) {
        // Sample 1: orders = 5 -> 3 -> 5 -> 8 -> 5, code = 5[cite: 27]
        Node sample1 = buildList(new int[]{5, 3, 5, 8, 5});
        sample1 = removeAll(sample1, 5);
        printList(sample1); // Expected Output: 3 -> 8 -> null[cite: 27]

        // Sample 2: orders = 5 -> 5 -> 5, code = 5[cite: 27]
        Node sample2 = buildList(new int[]{5, 5, 5});
        sample2 = removeAll(sample2, 5);
        printList(sample2); // Expected Output: null[cite: 27]
    }
}
