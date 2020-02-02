/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] randomQueue = (Item[]) new Object[1];
    private Deque<Integer> indicies = new Deque<>();
    private int queueArrayLength = 1;
    private int count = 0;
    private int entryPoint = 0;


    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return count;
    }

    private int findNonEmptyIndex(int length) {
        int index = StdRandom.uniform(length);
        while (randomQueue[index] == null) {
            index = StdRandom.uniform(length);
        }
        return index;
    }

    public void enqueue(Item value) {
        if (value == null) {
            throw new IllegalArgumentException("null not accepted as argument");
        }
        if (indicies.size() == 0) {
            randomQueue[entryPoint++] = value;
        }
        else {
            int index = indicies.removeFirst();
            randomQueue[index] = value;
        }
        count++;
        if (count == queueArrayLength) {
            resize(queueArrayLength * 2);
        }
    }

    private void resize(int value) {
        final Item[] newRandomQueue = (Item[]) new Object[value];
        final Deque<Integer> newIndicies = new Deque<>();
        int j = 0;
        for (int i = 0; i < randomQueue.length; i++) {
            if (randomQueue[i] != null) {
                newRandomQueue[j++] = randomQueue[i];
            }
        }
        randomQueue = newRandomQueue;
        queueArrayLength = value;
        indicies = newIndicies;
        entryPoint = j;
    }

    private void throwArgumentForEmptyQueue() {
        if (size() == 0) {
            throw new NoSuchElementException("Empty RandomQueue");
        }
    }

    public Item dequeue() {
        throwArgumentForEmptyQueue();
        int index = findNonEmptyIndex(queueArrayLength);
        indicies.addLast(index);
        Item returnValue = randomQueue[index];
        randomQueue[index] = null;
        count--;
        if (count == queueArrayLength / 4) {
            resize(queueArrayLength / 2);
        }
        return returnValue;
    }

    public Item sample() {
        throwArgumentForEmptyQueue();
        int index = findNonEmptyIndex(queueArrayLength);
        return randomQueue[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final RandomizedQueue<Item> randomQueueCopy = new RandomizedQueue<>();

        public RandomizedQueueIterator() {
            for (int i = 0; i < queueArrayLength; i++) {
                if (randomQueue[i] != null) {
                    randomQueueCopy.enqueue(randomQueue[i]);
                }
            }
        }

        public boolean hasNext() {
            return randomQueueCopy.size() > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Done.");
            }
            return randomQueueCopy.dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException("No remove supported on this iterator");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(5);
        rq.enqueue(7);
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        for (int i : rq) {
            System.out.println(i);
        }
    }
}
