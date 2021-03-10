package com.company.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BagClient {
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
    }
}

class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;       // Beginning of the bag
    private int n;                  // Number of elements in bag

    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    public static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void add(Item item) {
        Node<Item> oldFirst = first;
        oldFirst = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public static void main(String[] args) {
    }
}
