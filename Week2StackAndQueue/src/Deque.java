import java.util.Iterator;
import java.util.NoSuchElementException;


// Dequeue. A double-ended queue or deque (pronounced “deck”)
// is a generalization of a stack and
// a queue that supports adding and removing items
// from either the front or the back of the data structure.
// using arrays and linked list
public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node back;
    private int size = 0;

    private class Node {
        private Item item;
        private Node left;
        private Node right;
    }

    // construct an empty deque
    public Deque() {
        front = null;
        back = null;
        size = 0;
    }

    // is the deque empty
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        if (size == 0) {
            back = newNode;
        } else if (size == 1) {
            newNode.right = back;
            back.left = newNode;
        } else {
            front.left = newNode;
            newNode.right = front;
        }
        front = newNode;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        if (size == 0) {
            front = newNode;
        } else if (size == 1) {
            newNode.left = front;
            front.right = newNode;
        } else {
            back.right = newNode;
            newNode.left = back;
        }
        back = newNode;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is currently empty");
        }
        Item returnItem = front.item;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            front.right.left = null;
            front = front.right;
        }
        size--;
        return returnItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is currently empty");
        }
        Item returnItem = back.item;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            back.left.right = null;
            back = back.left;
        }
        size--;
        return returnItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<Item> {
        private Node currentItem = front;

        @Override
        public boolean hasNext() {
            return currentItem != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element available. Reached end of queue.");
            }
            Item value = currentItem.item;
            currentItem = currentItem.right;
            return value;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("1. Size: " + deque.size());
        System.out.println("Should be empty: " + deque.isEmpty());
        deque.addFirst("A");
        System.out.println("2. Size: " + deque.size());
        deque.addFirst("2");
        deque.addLast("3");
        System.out.println("2. Size: " + deque.size());

        //        deque.addLast("B");
//        System.out.println("3. Size: " + deque.size());
//        deque.addLast("C");
//        System.out.println("4. Size: " + deque.size());
//        System.out.println("FIRST: " + deque.front.item);
//        System.out.println("LAST: " + deque.back.item);
//        deque.addFirst("E");
//        System.out.println("NEW FIRST: " + deque.front.item);
//        System.out.println("AND LAST Should be C: " + deque.back.item);
//        System.out.println("Should not be empty: " + deque.isEmpty());
//        deque.removeFirst();
//        System.out.println("NEW FIRST SHOULD BE A " + deque.front.item);
//        deque.removeLast();
//        System.out.println("NEW LAST SHOULD BE B " + deque.back.item);
    }
}
