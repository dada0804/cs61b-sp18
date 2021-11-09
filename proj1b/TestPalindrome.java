import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome(){
        assertFalse(palindrome.isPalindrome("culus"));
        assertFalse(palindrome.isPalindrome("Rulur"));
        assertFalse(palindrome.isPalindrome("%ulu;"));
        assertTrue(palindrome.isPalindrome("%t%"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("rac:car"));
        assertTrue(palindrome.isPalindrome("r"));
        assertTrue(palindrome.isPalindrome(""));

    }

    @Test
    public void testNewisPalindrome(){
        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("racecar", offByOne));


    }

    @Test
    public void testNisPalindrome(){
        CharacterComparator offByN = new OffByN(5);
        assertTrue(palindrome.isPalindrome("acehf", offByN));
        assertFalse(palindrome.isPalindrome("Ac:hf", offByN));
        assertFalse(palindrome.isPalindrome(":bac?", offByN));

    }


}
