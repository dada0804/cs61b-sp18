package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        ArrayList<Integer> params = new ArrayList<>();
        params.add(255);
        params.add(255);
        params.add(255);
        params.add(6);
        params.add(10);
        ComplexOomage co = new ComplexOomage(params);
        System.out.println(co.hashCode());

        deadlyList.add(co);
        ArrayList<Integer> params2 = new ArrayList<>();
        params2.add(255);
        params2.add(255);
        params2.add(254);
        params2.add(6);
        params2.add(6);
        ComplexOomage co2 = new ComplexOomage(params2);
        System.out.println(co2.hashCode());
        deadlyList.add(co2);

        ArrayList<Integer> params3 = new ArrayList<>();
        params3.add(255);
        params3.add(255);
        params3.add(255);
        params3.add(255);
        params3.add(255);
        ComplexOomage co3 = new ComplexOomage(params3);
        deadlyList.add(co3);
        System.out.println(co3.hashCode());

        ArrayList<Integer> params4 = new ArrayList<>();
        params4.add(255);
        params4.add(255);
        params4.add(255);
        params4.add(100);
        params4.add(156);
        ComplexOomage co4 = new ComplexOomage(params4);
        deadlyList.add(co4);
        System.out.println(co4.hashCode());

        ArrayList<Integer> params5 = new ArrayList<>();
        params5.add(255);
        params5.add(255);
        params5.add(255);
        params5.add(200);
        params5.add(20);
        ComplexOomage co5 = new ComplexOomage(params5);
        deadlyList.add(co5);
        System.out.println(co5.hashCode());



        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
