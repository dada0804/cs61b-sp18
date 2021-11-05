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
        if (index < 0) {
            index = items.length + index;
        }
        return index;
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
        T[] a = (T[]) new Object[capacity*2];

        System.arraycopy(items, nextFirst, a, 0, size+1);
        items = a;
        nextFirst = 0;
        nextLast = size + 1;
    }


    public void addFirst(T item) {
        if (size == items.length - 2) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;

    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length - 2) {
            resize(size * 2);
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
        for (int i = addOne(nextFirst); i < addOne(nextFirst) + size; i += 1) {
            System.out.print(items[i % items.length] + " ");
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
        return items[(nextFirst + index + 1) % items.length];

    }

    /* Creates a deep copy of other
      A walkthrough that provides a solution for this copy constructor is available at https://www.youtube.com/watch?v=JNroRiEG7U4)*/
    public ArrayDeque(ArrayDeque other) {
        T[] items = (T[]) new Object[other.items.length];
        System.arraycopy(other, 0, items, 0, items.length);
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> lld = new ArrayDeque<Integer>();
        lld.addLast(0);
        lld.addLast(1);
        lld.removeFirst();
        System.out.println(lld.get(0));

        lld.addLast(4);
        lld.addLast(5);
        lld.addLast(6);

        lld.addLast(7);
        lld.addFirst(8);
        lld.printDeque();

        lld.addFirst(9);
        lld.removeFirst();
        lld.removeFirst();
        lld.removeLast();

        lld.removeFirst();
        lld.removeLast();
        lld.removeLast();

        lld.printDeque();

    }

}


