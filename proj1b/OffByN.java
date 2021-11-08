public class OffByN implements CharacterComparator {
    private static int BY;

    public OffByN(int n) {
        BY = n;
    }

    @Override
    public boolean equalChars(char x, char y){
        int a = x;
        int b = y;
        int diff = a - b;

        return diff == BY | diff == -BY;
    }



}