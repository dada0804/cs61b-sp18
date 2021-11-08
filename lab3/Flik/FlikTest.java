import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testSame(){
        boolean result = Flik.isSameNumber(129,129);
        assertTrue(result);
    }
}
