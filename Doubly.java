public class Doubly {
    static class Node {
        int data;
        Node prev;
        Node next;

        Node(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Doubly() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void addFirst(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void addAtIndex(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }

        if (index == 0) {
            addFirst(value);
            return;
        }

        if (index == size) {
            addLast(value);
            return;
        }

        Node newNode = new Node(value);
        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        Node prevNode = current.prev;
        newNode.prev = prevNode;
        newNode.next = current;
        prevNode.next = newNode;
        current.prev = newNode;
        size++;
    }

    public void set(int index, int value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = value;
    }

    public int removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }

        int removed = head.data;

        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        size--;
        return removed;
    }

    public int removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }

        int removed = tail.data;

        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return removed;
    }

    public int removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        Node prevNode = current.prev;
        Node nextNode = current.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        size--;
        return current.data;
    }

    public boolean removeByValue(int value) {
        Node current = head;

        while (current != null) {
            if (current.data == value) {
                if (current == head) {
                    removeFirst();
                    return true;
                }
                if (current == tail) {
                    removeLast();
                    return true;
                }

                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public void replaceFirstOccurrence(int oldValue, int newValue) {
        Node current = head;

        while (current != null) {
            if (current.data == oldValue) {
                current.data = newValue;
                return;
            }
            current = current.next;
        }
    }

    public void replaceKTimes(int oldValue, int newValue, int k) {
        if (k <= 0) {
            return;
        }

        int count = 0;
        Node current = head;

        while (current != null && count < k) {
            if (current.data == oldValue) {
                current.data = newValue;
                count++;
            }
            current = current.next;
        }
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Doubly Linked List: empty");
            return;
        }

        Node current = head;
        System.out.print("Doubly Linked List: ");

        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Doubly list = new Doubly();

        list.addLast(100);
        list.addLast(50);
        list.addLast(100);
        list.addLast(70);
        list.addLast(100);
        list.display();

        list.replaceFirstOccurrence(100, 90);
        System.out.println("After replacing first 100 with 90:");
        list.display();

        list.replaceKTimes(100, 90, 2);
        System.out.println("After replacing first 2 occurrences of 100 with 90:");
        list.display();

        list.addFirst(10);
        list.addAtIndex(2, 25);
        System.out.println("After addFirst and addAtIndex:");
        list.display();

        list.set(2, 99);
        System.out.println("After set index 2 = 99:");
        list.display();

        list.removeFirst();
        list.removeByValue(90);
        list.removeAtIndex(1);
        System.out.println("After remove operations:");
        list.display();
    }
}
