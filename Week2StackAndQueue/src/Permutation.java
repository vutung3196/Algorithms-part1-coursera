import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt("5");
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        String input;
        for (int i = 0; i < k; i++) {
            input = StdIn.readString();
            randomizedQueue.enqueue(input);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
