/**
 * Share.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package Assets;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit-Test zur Klasse Share
 */
public class ShareTest {
    /**
     * Zwei Aktien anlegen
     */
    private final Share share1 = new Share("ShareName", 500);
    private final Share share2 = new Share("ShareName", 500);
    
    
    /**
     * Test der getWert-Methode
     */
    @Test
    public void testGetWert(){
        assertEquals("Aufruf von getWert()", share1.getWert(), 500);
    }
    
    /**
     * Test der getName-Methode
     */
    @Test
    public void testGetName(){
        assertEquals("Aufruf von getName()", share2.getName(), "ShareName");
    }
    
    /**
     * Test der equals-Methode
     */
    @Test
    public void testEquals(){
        assertTrue("Aufruf der equals-Methode", share1.equals(share2));
    }
}
