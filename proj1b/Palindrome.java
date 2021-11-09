import java.util.Locale;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque d = new LinkedListDeque();
        for (int i = 0; i < word.length(); i += 1) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    private Boolean compare(String word) {
        if (word == "") {
            return true;
        }
        int start = word.charAt(0);
        int end = word.charAt(word.length() - 1);
        return start - end == 0;
    }

    private String toString(Deque d) {
        d.removeFirst();
        d.removeLast();
        String newWord = "";
        int size = d.size();
        for (int i = 0; i < size; i++) {
            newWord += d.removeFirst();
        }
        return newWord;
    }

    public boolean isPalindrome(String word) {
        /* recursion */

        if (word.length() <= 1) {
            return true;
        }
        Deque d = wordToDeque(word);
        String next = toString(d);
        if (isPalindrome(next)) {
            return compare(word);
        }
        return false;
    }

    /* iteration */
//        word = word.toLowerCase();
//        while (word.length() > 1) {
//            if (! compare(word)){
//                return false;
//            }
//            Deque d = wordToDeque(word);
//            word = toString(d);
//        }
//        return true;


    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;}
        Deque d = wordToDeque(word);
        String next = toString(d);
        if (isPalindrome(next, cc)) {
            return cc.equalChars(word.charAt(0), word.charAt(word.length() - 1)) ;
        }
        return false;

        }


}