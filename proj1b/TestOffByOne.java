import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualC(){
        char x1 = 'a';
        char y1 = 'b';
        assertTrue(offByOne.equalChars(x1,y1));
        char x2 = 'a';
        char y2 = 'a';
        assertFalse(offByOne.equalChars(x2,y2));


    }


}
