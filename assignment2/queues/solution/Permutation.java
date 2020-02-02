/* *****************************************************************************
 *  Name: Jake Perez
 *  Date: 2/1/2020
 *  Description: Permutation Client
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        int length = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomQueue.enqueue(s);
        }
        for (String s : randomQueue) {
            if (length-- > 0) {
                System.out.println(s);
            }
        }
    }
}
