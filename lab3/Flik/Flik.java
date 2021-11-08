/** An Integer tester created by Flik Enterprises. */
public class Flik {
    //此处bug是错在data type上了，应该写int，而不是integer，只有在-128-127区间才会正确，超出范围就是错的了。
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }
}
