/**
 * StockPriceProviderTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package StockPrice;
import org.junit.Test;
import static org.junit.Assert.*;
import Assets.Share;

/**
 * JUnit-Test zur Klasse ShareItem
 */
public class StockPriceProviderTest {
    Share share = new Share ("Aktienname", "ID", 100);
    StockPriceProvider spp = new ConstStockPriceProvider();
    
    /**
     * Testet die Methode isShareListed
     */
    @Test
    public void testIsShareListed(){
        assertFalse("Aufruf der Methode isShareListed()" , spp.isShareListed(share));
    }
    
    /**
     * Testet die Methode getCurrentShareRate
     * Da als StockPriceProvider ConstStockProvider definiert ist bleibt der Initianalwert beibehalten
     * Für die Aktie Audi liegt dieser bei 300
     */
    @Test
    public void testGetCurrentShareRate() throws Exception{
        assertEquals("Aufruf der Methode getCurrentShareRate()", spp.getCurrentShareRate("Audi"), 300);
    }
    
    /**
     * Testet die Methode getShare
     */
    @Test
    public void testGetShare() throws Exception{
        assertEquals("Aufruf der Methode getShare()", spp.getShare("Audi").getName(), "Audi");
    }
    
    /**
     * Testet die Methode getAllSharesAsSnapshot
     */
    @Test
    public void testGetAllSharesAsSnapshot(){
        assertEquals("Aufruf der Methode getAllSharesAsSnapshot()", spp.getAllSharesAsSnapshot().first().getName(), "Apple");
    }
    
    /**
     * Testet die Methode getNumberOfShares
     */
    @Test
    public void testGetNumberOfShares(){
        assertEquals("Aufruf der Methode getNumberOfShares()", spp.getNumberOfShares(), 7);
    }
}
