import java.util.Iterator;

public class LinkQueueOfString {
    private Node first, last;

    private class Node {
        public String item;
        public Node next;
    }

    public LinkQueueOfString() {

    }

    public void enqueue(String item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

    public String dequeue() {
        String item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return 1;
    }
}

class Stack<Item> implements Iterable<Item> {
    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}
