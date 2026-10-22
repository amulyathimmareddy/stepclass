package week10.assignment;



public class ReverseTheQueue {

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
     * Reverses the singly linked list in place using three references.
     * No new nodes are created.
     */
    public static Node reverse(Node head) {
        Node prev = null;
        Node current = head;
        Node next = null;

        while (current != null) {
            next = current.next; // Store next node[cite: 20]
            current.next = prev; // Reverse the link[cite: 20]
            prev = current;      // Move prev one step ahead[cite: 20]
            current = next;      // Move current one step ahead[cite: 20]
        }

        // prev will be the new head of the reversed list[cite: 20]
        return prev;
    }

    // Helper method to display the linked list in the required format[cite: 20]
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

    // Helper method to construct a linked list from an array
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
        // Sample 1: queue = 1 -> 2 -> 3 -> 4[cite: 20]
        Node sample1 = buildList(new int[]{1, 2, 3, 4});
        sample1 = reverse(sample1);
        printList(sample1); // Expected Output: 4 -> 3 -> 2 -> 1 -> null[cite: 20]

        // Sample 2: queue = 9[cite: 20]
        Node sample2 = buildList(new int[]{9});
        sample2 = reverse(sample2);
        printList(sample2); // Expected Output: 9 -> null[cite: 20]
    }
}
