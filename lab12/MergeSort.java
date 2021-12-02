import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        if (items.isEmpty()){
            return null;
        }
        Queue singleItem = new Queue();
        while(!items.isEmpty()){
            Queue x = new Queue();
            x.enqueue(items.dequeue());
            singleItem.enqueue(x);
        }
        return singleItem;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue sortedQueue = new Queue();
        if (q1.isEmpty() && q2.isEmpty()){
            return null;
        }
        while(!q1.isEmpty() || !q2.isEmpty()){
            sortedQueue.enqueue(getMin(q1, q2));
        }
        return sortedQueue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.isEmpty()){
            return null;
        }
        Queue<Queue<Item>> singleItem = new Queue<>();
        Queue sorted = new Queue();
        singleItem = makeSingleItemQueues(items);
        Queue q1 = singleItem.dequeue();
        sorted = mergeSortedQueues(q1, q1);
        while (!singleItem.isEmpty()){
            Queue q3 = singleItem.dequeue();
            sorted = mergeSortedQueues(sorted, q3);
        }

        return sorted;
    }

    public static void main(String[] args){
        Queue<Integer> num = new Queue<Integer>();
//        num.enqueue(3);
//        num.enqueue(8);
//        num.enqueue(1);
//        num.enqueue(7);
//        num.enqueue(2);
//
//        num.enqueue(1);
//        num.enqueue(2);
//        num.enqueue(3);
//        num.enqueue(4);
//        num.enqueue(5);

        MergeSort x = new MergeSort();
        Queue<Integer> y = new Queue<>();
        y = x.mergeSort(num);
        if (y != null) {
            for (int i : y) {
                System.out.print(i);
            }
        }

    }
}
