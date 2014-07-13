/**
 * PlayerTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package Player;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit-Test zur Klasse CashAccount
 */
public class PlayerTest {
    /**
     * 2 Spieler anlegen umd beide Konstruktoren zu testen
     */
    private final Player player1 = new Player("player1");
    private final Player player2 = new Player("player2", 5000);
    
    /**
     * Testet die getName-Methode für beide Konstruktoren
     */
    @Test
    public void testGetName(){
        assertEquals("Aufruf von getName()", player1.getName(), "player1");
        assertEquals("Aufruf von getName()", player2.getName(), "player2");
    }
    
    /**
     * Testet die getCashAccount-Methode für beide Konstruktoren
     */
    @Test
    public void testGetCashAccount(){
        assertEquals("Aufruf von getCashAccount()", player1.getCashAccount().getWert(), 500000);
        assertEquals("Aufruf von getCashAccount()", player2.getCashAccount().getWert(), 5000);
    }
    
    /**
     * Testet die getDepositAccount-Methode für beide Konstruktoren
     */
    @Test
    public void testGetDepositAccount(){
        assertEquals("Aufurf von getDepositAccount()", player1.getDepositAccount().getWert(), 0);
        assertEquals("Aufurf von getDepositAccount()", player2.getDepositAccount().getWert(), 0);
    }
}
