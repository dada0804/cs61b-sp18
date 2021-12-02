import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        while (!unsorted.isEmpty()){
            Item x = unsorted.dequeue();
            if (x.compareTo(pivot) < 0){
                less.enqueue(x);
            }
            else if(x.compareTo(pivot) > 0){
                greater.enqueue(x);
            }
            else{
                equal.enqueue(pivot);
            }
        }

    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!

        //!!! 本来这里写的是items.size()== 1就return，会出现overflow，因为此时equal一直都等于这个值，就会一直recursion
        //升序的也会出现问题，123，quicksort 12的话，再sort 1 返回1，然后又sort 12 ？
           if (items.isEmpty()){
               return items;
           }
            Item pivot = getRandomItem(items);
            Queue less = new Queue();
            Queue equal = new Queue();
            Queue greater = new Queue();
            partition(items, pivot, less, equal, greater);
            items = catenate(catenate(less, equal), greater);
            if (!less.isEmpty() || greater.isEmpty()){
                quickSort(less);
                quickSort(greater);
            }
            return items;
    }
    public static void main(String[] args){
        Queue<Integer> num = new Queue<Integer>();
        num.enqueue(8);
        num.enqueue(8);
        num.enqueue(8);
        num.enqueue(8);
        num.enqueue(8);

//        num.enqueue(1);
//        num.enqueue(2);
//        num.enqueue(3);
//        num.enqueue(4);
//        num.enqueue(5);

        QuickSort x = new QuickSort();
        Queue<Integer> y = new Queue<>();
        y = x.quickSort(num);
        for (int i : y){
            System.out.print(i);
        }

    }
}
