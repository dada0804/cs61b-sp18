package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(3);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertTrue(arb.isFull());
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        assertTrue(arb.isEmpty());
        arb.enqueue(3);
        int expected = 1;
        assertEquals(expected, arb.fillCount);
        int expected2 = 3;
        assertEquals(expected2, arb.peek());



    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
