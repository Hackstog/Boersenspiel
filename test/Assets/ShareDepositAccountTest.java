/**
 * ShareDepositAccountTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package Assets;
import java.util.TreeMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit-Test zur Klasse ShareDepositAccount
 */
public class ShareDepositAccountTest {
    /**
     * 2 Aktien anlegen
     * Mit diesen 2 Aktienpakete erstellen (Zur einfacheren Berechnung haben beide Pakete den Wert 500)
     * Damit ShareDepositAccount anlegen
     */
    private final Share s1 = new Share("Aktie1", 100);
    private final Share s2 = new Share("Aktie2", 50);
    private final ShareDepositAccount depot;

    public ShareDepositAccountTest() {
        this.depot = new ShareDepositAccount();
    }
    
    /**
     * Testet das Einfügen von Aktienpaketen in das Depot und die Methode search
     */
    @Test
    public void testInsert(){
        depot.insert(s1, 100);
        depot.insert(s2, 50);
        assertTrue("Suche Aktienpaket mit search nachdem es mit insert eingefügt wurde", depot.getPakete().containsKey(s1));
    }

    /**
     * Prüft ob die Methode getWert des Depots mit 2 Aktienpaketen im Wert von je 500 auch 1000 zurückgibt
     */
    @Test
    public void testGetWert(){
        depot.insert(s1, 100);
        depot.insert(s2, 10);       
        assertEquals("Aufruf von getWert()", depot.getWert(), 10500);
    }
}
