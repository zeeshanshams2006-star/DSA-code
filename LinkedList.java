/**
 * LinkedList
 */
public class LinkedList {
    // Node represents one element in the singly linked list
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    // 1. Insert at beginning
    public void insertAtBeginning(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            System.out.println("Inserted " + value + " as first node.");
            return;
        }
        newNode.next = head;
        head = newNode;
        System.out.println("Inserted " + value + " at beginning.");
    }

    // 2. Insert at end
    public void insertAtEnd(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            System.out.println("Inserted " + value + " as first node.");
            return;
        }

        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
        System.out.println("Inserted " + value + " at end.");
    }

    // 3. Insert at a specific position
    public void insertAtPosition(int position, int value) {
        if (position < 1) {
            System.out.println("Invalid position. Position must be >= 1.");
            return;
        }

        if (position == 1) {
            insertAtBeginning(value);
            return;
        }
        if (position == length() + 1) {
            insertAtEnd(value);
            return;
        }

        Node newNode = new Node(value);
        Node temp = head;
        int currentPosition = 1;

        while (temp != null && currentPosition < position - 1) {
            temp = temp.next;
            currentPosition++;
        }

        if (temp == null) {
            System.out.println("Position is out of range. Node not inserted.");
            return;
        }

        newNode.next = temp.next;
        temp.next = newNode;
        System.out.println("Inserted " + value + " at position " + position + ".");
    }

    // 4. Delete from beginning
    public void deleteAtBeginning() {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        System.out.println("Deleted " + head.data + " from beginning.");
        head = head.next;
    }

    // 5. Delete from end
    public void deleteAtEnd() {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        if (head.next == null) {
            System.out.println("Deleted " + head.data + " from end.");
            head = null;
            return;
        }

        Node temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }

        System.out.println("Deleted " + temp.next.data + " from end.");
        temp.next = null;
    }

    // 6. Delete by value
    public void deleteByValue(int value) {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        if (head.data == value) {
            head = head.next;
            System.out.println("Deleted " + value + ".");
            return;
        }

        Node temp = head;
        while (temp.next != null && temp.next.data != value) {
            temp = temp.next;
        }

        if (temp.next == null) {
            System.out.println(value + " not found in the list.");
            return;
        }

        temp.next = temp.next.next;
        System.out.println("Deleted " + value + ".");
    }

    // 7. Delete at a specific position
    public void deleteAtPosition(int position) {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        if (position < 1) {
            System.out.println("Invalid position.");
            return;
        }

        if (position == 1) {
            deleteAtBeginning();
            return;
        }

        Node temp = head;
        int currentPosition = 1;

        while (temp != null && currentPosition < position - 1) {
            temp = temp.next;
            currentPosition++;
        }

        if (temp == null || temp.next == null) {
            System.out.println("Position is out of range.");
            return;
        }

        System.out.println("Deleted " + temp.next.data + " from position " + position + ".");
        temp.next = temp.next.next;
    }

    // 8. Search for a value
    public boolean search(int key) {
        Node temp = head;
        while (temp != null) {
            if (temp.data == key) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    // 9. Find length of the list
    public int length() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    // 10. Reverse the list
    public void reverse() {
        Node previous = null;
        Node current = head;
        Node nextNode;

        while (current != null) {
            nextNode = current.next;
            current.next = previous;
            previous = current;
            current = nextNode;
        }

        head = previous;
        System.out.println("List reversed successfully.");
    }

    // 11. Display the list
    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node temp = head;
        System.out.print("Linked List: ");
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.insertAtEnd(10);
        list.insertAtEnd(20);
        list.insertAtBeginning(5);
        list.insertAtPosition(2, 15);
        list.display();

        System.out.println("Length: " + list.length());
        System.out.println("Search 15: " + list.search(15));

        list.deleteByValue(20);
        list.deleteAtPosition(2);
        list.display();

        list.reverse();
        list.display();
    }
}
