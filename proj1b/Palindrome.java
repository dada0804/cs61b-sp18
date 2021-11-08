public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque d = new LinkedListDeque();
        for (int i = 0; i < word.length(); i += 1) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    private Boolean compare(String word) {
          Character start = word.charAt(0);
        Character end = word.charAt(word.length() - 1);
        return start.compareTo(end) == 0;
    }

    private String toString(Deque d){
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
        while (word.length() > 1) {
            if (! compare(word)){
                return false;
            }
            Deque d = wordToDeque(word);
            word = toString(d);
        }
        return true;

    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        while (word.length() > 1) {
            Character start = word.charAt(0);
            Character end = word.charAt(word.length() - 1);
            if (! cc.equalChars(start, end)){
                return false;
            }
            Deque d = wordToDeque(word);
            word = toString(d);
        }
        return true;
    }

}