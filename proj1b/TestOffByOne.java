import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualC(){
        char x0 = 'b';
        char y0 = 'a';
        assertTrue(offByOne.equalChars(x0,y0));
        char x1 = '%';
        char y1 = '&';
        assertTrue(offByOne.equalChars(x1,y1));
        char x2 = 'a';
        char y2 = 'a';
        assertFalse(offByOne.equalChars(x2,y2));
        char x3 = 'z';
        char y3 = 'a';
        assertFalse(offByOne.equalChars(x3,y3));
        char x5 = 'B';
        char y5 = 'a';
        assertFalse(offByOne.equalChars(x5,y5));



    }


}
