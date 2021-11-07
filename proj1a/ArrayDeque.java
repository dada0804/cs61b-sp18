public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 2;
        nextLast = 3;
    }

    /* add and remove operations must not involve any looping or recursion
     Constant time */
    /* Adds an item of type T to the front of the deque. */
    private int minusOne(int index) {
        index -= 1;
        return (index + items.length) % items.length;
    }

    private int addOne(int index) {
        index += 1;
        return index % items.length;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];

        System.arraycopy(items, nextFirst, a, 0, items.length - nextFirst);
        if (nextFirst > 0) {
            System.arraycopy(items, 0, a, items.length - nextFirst, nextLast);
        }
        items = a;
        nextFirst = 0;
        nextLast = size + 1;
    }

    private void downsize(int capacity) {
        T[] a = (T[]) new Object[capacity * 2];
        if (nextFirst > nextLast) {
            System.arraycopy(items, nextFirst, a, 0, items.length - nextFirst);
            System.arraycopy(items, 0, a, items.length - nextFirst, nextLast);
        } else {
            System.arraycopy(items, nextFirst, a, 0, size + 1);
        }
        items = a;
        nextFirst = 0;
        nextLast = size + 1;
    }


    public void addFirst(T item) {
        if (size == items.length - 2) {
            resize((items.length) * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;

    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length - 2) {
            resize((items.length) * 2);
        }
        items[nextLast] = item;
        nextLast = addOne(nextLast);
        size += 1;
    }


    /* Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque.*/
    /* constant time */
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.
    Once all the items have been printed, print out a new line.*/
    public void printDeque() {
        for (int i = nextFirst; i < nextFirst + size; i += 1) {
            System.out.print(items[addOne(i)] + " ");
        }
        System.out.println();

    }

    /* Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = items[addOne(nextFirst)];
        nextFirst = addOne(nextFirst);
        items[nextFirst] = null;
        size -= 1;
        if (items.length / (size + 2) > 3) {
            downsize(size + 2);
        }
        return first;
    }

    /* Removes and returns the item at the back of the deque.
    If no such item exists, returns null.*/
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = items[minusOne(nextLast)];
        nextLast = minusOne(nextLast);
        items[nextLast] = null;
        size -= 1;
        if (items.length / (size + 2) > 3) {
            downsize(size + 2);
        }
        return last;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque! */
    /* iteration */

    public T get(int index) {
        return items[addOne(nextFirst + index)];
    }
}
    //API无法通过，所以删掉

    /* Creates a deep copy of other
      A walkthrough that provides a solution for this copy constructor is available at https://www.youtube.com/watch?v=JNroRiEG7U4)*/
//    public ArrayDeque(ArrayDeque other) {
//        T[] items = (T[]) new Object[other.items.length];
//        System.arraycopy(other, 0, items, 0, items.length);
//    }

//    public static void main(String[] args) {
//        ArrayDeque<Integer> lld = new ArrayDeque<Integer>();
//        lld.addFirst(0);
//        lld.addLast(1);
//        lld.removeFirst();
//        lld.removeFirst();
//
//        lld.addLast(4);
//        lld.addLast(5);
//        lld.addFirst(6);
//        System.out.println(lld.get(0));
//        lld.addFirst(8);
//        lld.addLast(9);
//        lld.addLast(10);
//        lld.addFirst(11);
//        lld.printDeque();
//
//        System.out.println(lld.get(3));
//        lld.removeLast();
//        lld.addLast(14);
//        lld.removeLast();
//        System.out.println(lld.get(4));
//        lld.removeLast();
//        lld.removeLast();
//        lld.addFirst(19);
//        lld.removeFirst();
//        System.out.println(lld.removeLast());
//    }




