/**
 * ShareDepositAccountTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package Assets;
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
    private final Share s1 = new Share("Aktie1", "ID", 100);
    private final Share s2 = new Share("Aktie2", "ID", 50);
    private final ShareItem p1 = new ShareItem(s1, 5);
    private final ShareItem p2 = new ShareItem(s2, 10);
    private final ShareDepositAccount depot;

    public ShareDepositAccountTest() {
        this.depot = new ShareDepositAccount();
    }
    
    /**
     * Testet das Einfügen von Aktienpaketen in das Depot und die Methode search
     */
    @Test
    public void testInsert(){
        depot.insert(p1);
        depot.insert(p2);
        assertTrue("Suche Aktienpaket mit search nachdem es mit insert eingefügt wurde", depot.search("Aktie1").getName().equals("Aktie1"));
    }
    
    /**
     * Testet das Löschen von Aktienpaketen aus dem Depot und die Methode search
     */
    @Test(expected = NullPointerException.class)
    public void testRemove() throws NullPointerException{
        depot.insert(p1);
        depot.insert(p2);
        depot.remove(p1);
        //Exception erwartet
        depot.search("Aktie1").getName();
    }
    
    /**
     * Prüft ob die Methode getWert des Depots mit 2 Aktienpaketen im Wert von je 500 auch 1000 zurückgibt
     */
    @Test
    public void testGetWert(){
        depot.insert(p1);
        depot.insert(p2);       
        assertEquals("Aufruf von getWert()", depot.getWert(), 1000);
    }
}
