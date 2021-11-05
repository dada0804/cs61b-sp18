public class LinkedListDeque<T> {
    private IntNode sentinel;
    private int size;

    public class IntNode {
        private IntNode prev;
        public T item;
        public IntNode next;

        public IntNode(IntNode p, T i, IntNode n) {
            prev = p;
            item = i;
            next = n;

        }
    }

    /* add and remove operations must not involve any looping or recursion
     Constant time */
    /* Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        sentinel.next = new IntNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        sentinel.prev = new IntNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
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
        IntNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            p = p.next;
        }
        System.out.println();
    }

    /* Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return first;
    }

    /* Removes and returns the item at the back of the deque.
    If no such item exists, returns null.*/
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return last;

    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque! */
    /* iteration */

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        IntNode p = sentinel.next;
        int x = 0;
        while (p.next != null) {
            if (x == index) {
                return p.item;
            }
            p = p.next;
            x += 1;
        }
        return null;

    }


    /* recursion */
    private T getR(IntNode p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getR(p.next, index - 1);
    }

    public T getRecursive(int index) {
        return getR(sentinel.next, index);

    }

    /* Creates an empty linked list deque. */
    public LinkedListDeque() {
        /*不能直接写IntNode(sentinel.next, null, s.prev）因为此时sentinel是空的*/
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /* Creates a deep copy of other
      A walkthrough that provides a solution for this copy constructor is available at https://www.youtube.com/watch?v=JNroRiEG7U4)*/
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }
//    public static void main(String[] args){
//        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
//        lld.removeFirst();
//        lld.addFirst(2);
//        lld.addLast(5);
//        lld.printDeque();
//        System.out.println(lld.removeLast());
//        System.out.println(lld.size);
//    }

}


