/**
 * CashAccountTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package Assets;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit-Test zur Klasse CashAccount
 */
public class CashAccountTest {
    /**
     * Neuen CashAccount zum Testen anlegen
     */
    private final CashAccount ca = new CashAccount(5000);
    
    /**
     * Prüft ob der mit 5000 angelegte CaschAccount bei getWert() auch 5000 zurückliefert
     */
    @Test
    public void testGetWert(){
        assertEquals("Aufruf von getWert()", ca.getWert(), 5000);
    }
    
    /**
     * Ruft die Funktion changeWert(1000) auf den CashAccount auf, und prüft ob getWert() anschließend 6000 liefert
     * getWert wurde bereits getestet
     */
    @Test
    public void testChangeWert(){
        ca.changeWert(1000);
        assertEquals("Aufruf von getWert() nachdem changeWert()", ca.getWert(), 6000);
    }
    
    /**
     * Testet die toString-Methode
     */
    @Test
    public void testToString(){
        assertEquals("Aufruf von toString()", ca.toString(), "Kontostand: 5000");
    }
}
