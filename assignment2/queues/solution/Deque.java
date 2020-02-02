/* *****************************************************************************
 *  Name: Jake  Perez
 *  Date: 2/1/2020
 *  Description: Deque Data Structure
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head = null;
    private Node tail = null;
    private int count = 0;

    private class Node {
        private final Item item;
        private Node next;

        public Node(Item value) {
            this.item = value;
            this.next = null;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public Item getItem() {
            return item;
        }

        public Node getNext() {
            return next;
        }

    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item value) {
        if (value == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        if (count == 0) {
            head = new Node(value);
            tail = head;
        }
        else {
            Node newHead = new Node(value);
            newHead.setNext(head);
            head = newHead;
        }
        count++;
    }

    public void addLast(Item value) {
        if (value == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        if (count == 0) {
            tail = new Node(value);
            head = tail;
        }
        else {
            Node newTail = new Node(value);
            tail.setNext(newTail);
            tail = newTail;
        }
        count++;
    }

    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Empty Deque");
        }
        else {
            Item value = head.getItem();
            if (head.equals(tail)) {
                head = null;
                tail = null;
            }
            else {
                head = head.getNext();
            }
            count--;
            return value;
        }
    }

    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("Empty Deque");
        }
        else {
            Item value;
            if (tail.equals(head)) {
                value = tail.getItem();
                head = null;
                tail = null;
                count--;
                return value;
            }
            value = tail.getItem();
            Node newTail = head;
            while (!newTail.getNext().equals(tail)) {
                newTail = newTail.getNext();
            }
            tail = newTail;
            count--;
            return value;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("No remove on iterator.");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Done.");
            }
            Item currentValue = current.getItem();
            current = current.getNext();
            return currentValue;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        deque.addLast(6);
        deque.addFirst(12);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.addLast(4);
        deque.addLast(5);
        deque.addFirst(0);
        for (int value : deque) {
            System.out.println(value);
        }
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();

        System.out.println(deque.removeLast());
    }

}

