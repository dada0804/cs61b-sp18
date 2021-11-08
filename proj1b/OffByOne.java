public class OffByOne implements CharacterComparator {
        @Override
        public boolean equalChars(char x, char y){
                int a = x;
                int b = y;
                int diff = a - b;
                return diff == 1 | diff == -1;
        }

}