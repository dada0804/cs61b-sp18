public class OffByN implements CharacterComparator {
    private static int BY;

    public OffByN(int n) {
        BY = n;
    }

    @Override
    public boolean equalChars(char x, char y){
        if (! Character.isLetter(x) | !Character.isLetter(y)){
            return false;
        }
        int a = x;
        int b = y;
        int diff = a - b;
        return diff == BY | diff == -BY;
    }



}