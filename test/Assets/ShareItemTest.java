/**
 * ShareItemTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package Assets;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit-Test zur Klasse ShareItem
 */
public class ShareItemTest {
    /**
     * Neue Aktie anlegen
     * Mit dieser Aktie neues ShareItem zum Testen anlegen
     */
    private Share s = new Share("TestAktie", "ID", 100);
    private ShareItem paket = new ShareItem(s, 50);
    
    /**
     * Prüft ob das mit 50 Aktien mit Wert 100 angelegte ShareItem bei getWert() auch 5000 zurückliefert
     */
    @Test
    public void testGetWert(){
        assertEquals("Aufruf von getWert()", paket.getWert(), 5000);
    }
    
    /**
     * Prüft ob das mit 50 Aktien angelegte ShareItem bei getAnzahl() auch 50 zurückliefert
     */
    @Test
    public void testGetAnzahl(){
        assertEquals("Aufruf von getAnzahl()", paket.getAnzahl(), 50);
    }
    
    /**
     * Prüft ob das mit 50 Aktien angelegete ShareItem nach changeAnzahl(10) bei getAnzahl() 60 zurücliefert
     * getWert wurde bereits getestet
     */
    @Test
    public void testChangeAnzahl(){
        paket.changeAnzahl(10);
        assertEquals("Aufruf von getAnzahl() nach changeAnzahl()", paket.getAnzahl(), 60);
    }
    
    /**
     * Prüft ob das mit Namen "TestAktie" angelegte Aktienpaket bei getName() auch TestAktie zurückliefert
     */
    @Test
    public void testGetName(){
        assertEquals("Aufruf von getName()", paket.getName(), "TestAktie");
    }
    
    /**
     * Testet die toString-Methode
     */
    @Test
    public void testToString(){
        assertEquals("Aufruf von toString()", paket.toString(), "50 Stück von Aktie TestAktie");
    }
}
