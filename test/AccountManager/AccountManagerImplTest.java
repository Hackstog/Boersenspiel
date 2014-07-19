/**
 * AccountManagerImplTest.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     01.06.2014
 * Version:   1.0
 */


package AccountManager;
import org.junit.Test;
import static org.junit.Assert.*;
import StockPrice.ConstStockPriceProvider;
import Assets.*;
import Exceptions.*;

public class AccountManagerImplTest {
    /**
     * Erzeugt einen neuen AccountManager
     */
    private final ConstStockPriceProvider spp = new ConstStockPriceProvider();
    private final AccountManagerImpl am = new AccountManagerImpl(spp);
    private final Share Audi = new Share("Audi", 100);

    
    /**
     * Testet die Methode createPlayer und getPlayer
     */
    @Test
    public void testCreatePlayer() throws Exception{
        am.createPlayer("Spieler1");
        assertEquals("Methode createPlayer()", am.getPlayer("Spieler1").getName(), "Spieler1");
    }
    
    /**
     * Testet die Methode getShare
     */
    @Test
    public void testGetShare() throws Exception{
        assertTrue("Methode getShare()", am.getShare("Audi")!=null);
    }
    
    /**
     * Testet die Methode getPlayer
     */
    @Test
    public void testGetPlayer(){
        am.createPlayer("Spieler1");
        am.createPlayer("Spieler2");
        assertEquals("Methode getPlayer()", am.getPlayer().get(1).getName(), "Spieler2");
    }
    
    /**
     * Testet die Methode buyShare
     */
    @Test
    public void testBuyShare() throws Exception{
        am.createPlayer("Spieler");
        am.buyShare("Spieler", "Audi", 10);
        int aktienImDepot = am.getPlayer("Spieler").getDepositAccount().getPakete().get(Audi);
        assertEquals("Methode buyShare()", aktienImDepot, 10);
    }
    
    /**
     * Testet die Methode sellShare
     */
    @Test
    public void testSellShare() throws Exception{
        am.createPlayer("Spieler");
        am.buyShare("Spieler", "Audi", 50);
        am.sellShare("Spieler", "Audi", 40);
        int aktienImDepot = am.getPlayer("Spieler").getDepositAccount().getPakete().get(Audi);
        assertEquals("Methode buyShare()", aktienImDepot, 10);
    }
    
    /**
     * Testet die Methode getValueOfCashAccount
     */
    @Test
    public void testGetValueOfCashAccount() throws Exception{
        am.createPlayer("Spieler");
        assertEquals("Methode getValueOfCashAccount()", am.getValueOfCashAccount("Spieler"), 500000);
    }
    
    /**
     * Testet die Methode getValueOfDepositAccount
     * Kurs der Aktie Audi wurde in StockPriceProvider mit 300 Initialisiert
     */
    @Test
    public void testGetValueOfDepositAccount() throws Exception{
        am.createPlayer("Spieler");
        am.buyShare("Spieler", "Audi", 10);
        assertEquals("Methode getValueOfDepositAccount()", am.getValueOfDepositAccount("Spieler"), 100000);
    }
    
    /**
     * Methode getValueOfAll wird hier nicht getestet, da diese nur getValueOfDepositAccount und getValueOfCashAccount aufruft und die Werte addiert
     */
    
    /**
     * Testet die Methode getKurs
     * Kurs der Aktie Audi wurde in StockPriceProvider mit 300 Initialisiert
     */
    @Test
    public void testGetKurs() throws Exception{
        Share Audi = am.getShare("Audi");
        assertEquals("Methode getKurs()", am.getKurs(Audi), 10000);
    }
    
    /**
     * Tests für Exceptions
     */

    /**
     * Test der PlayerExistsException werfen soll
     */
    @Test(expected = PlayerExistsException.class)
    public void testPlayerExistsException() throws PlayerExistsException{
        am.createPlayer("Spieler");
        //Erwarte Exception
        am.createPlayer("Spieler");
    }
}
