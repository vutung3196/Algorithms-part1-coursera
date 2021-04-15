import edu.princeton.cs.algs4.StdOut;

public class TestingBST {
    public static void main(String[] args) {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        int n = keys.length;
        BinarySearchTree<String, Integer> st = new BinarySearchTree<>();
        for (int i = 0; i < n; i++) {
            st.put(keys[i], i);
        }
        StdOut.println("size = " + st.size());
        StdOut.println("min  = " + st.min());
        StdOut.println("max  = " + st.max());
        StdOut.println();

        // print keys in order using allKeys()
        StdOut.println("Testing keys()");
        StdOut.println("--------------------------------");
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
        StdOut.println();
        var root = st.root;
        System.out.println(st.isBST(root));
    }
}
