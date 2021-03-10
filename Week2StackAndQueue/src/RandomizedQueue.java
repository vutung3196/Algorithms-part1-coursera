import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int MINIMUM_STORAGE_SIZE = 2;
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[MINIMUM_STORAGE_SIZE];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Add an item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isStorageFull()) {
            doubleStorage();
        }
        items[size++] = item;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NullPointerException();
        }
        int indexOfItemToReturn = StdRandom.uniform(size);
        Item returnValue = items[indexOfItemToReturn];
        size--;
        items[indexOfItemToReturn] = items[size];
        items[size] = null;
        if (isStorageOversized()) {
            halveStorage();
        }
        return returnValue;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int indexOfItemToReturn = StdRandom.uniform(size);
        return items[indexOfItemToReturn];
    }

    private void doubleStorage() {
        resizeStorage(items.length * 2);
    }

    private boolean isStorageFull() {
        return items.length == size;
    }

    private void halveStorage() {
        resizeStorage(items.length / 2);
    }

    private boolean isStorageOversized() {
        return items.length > MINIMUM_STORAGE_SIZE && size <= items.length / 4;
    }

    private void resizeStorage(int newSize) {
        // copy array
        Item[] newItemStorage = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newItemStorage[i] = items[i];
        }
        items = newItemStorage;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private Item[] iteratorItems;
        private int index;

        public RandomIterator() {
            iteratorItems = copyRandomQueueItems();
            StdRandom.shuffle(iteratorItems);
        }

        private Item[] copyRandomQueueItems() {
            Item[] copiedItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copiedItems[i] = items[i];
            }
            return copiedItems;
        }

        @Override
        public boolean hasNext() {
            return index < iteratorItems.length;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return iteratorItems[index++];
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    public static void main(String[] args) {

    }
}
