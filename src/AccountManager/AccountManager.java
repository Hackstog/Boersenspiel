/**
 * AccountManager.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   2.0
 */


package AccountManager;
import Assets.Share;
import Player.Player;
import Player.TradeAgent;
import HelpClasses.Transaction;
import StockPrice.StockPriceProvider;
import Exceptions.PlayerExistsException;
import Exceptions.PlayerNotFoundException;
import Exceptions.ShareNotFoundException;
import java.util.List;

/**
 * Interface zur Steurung des Spielablaufs
 */
public interface AccountManager {
    public void createPlayer(String playerName, long startKapital) throws Exception;
    public void createPlayer(String palyerName) throws Exception;
    public Share getShare(String shareName) throws Exception;
    public Player getPlayer(String playerName) throws Exception;
    public List<Player> getPlayer();
    public List<TradeAgent> getAgent();
    public void buyShare(String playerName, String shareName, int anzahl) throws Exception;
    public void sellShare(String playerName, String shareName, int anzahl) throws Exception;
    public long getValueOfCashAccount(String playerName) throws Exception;
    public long getValueOfDepositAccount(String playerName) throws Exception;
    public long getValueOfAll(String playerName) throws Exception;
    public long getKurs(Share share);
    public String getShares();
    public void startAgent(String playerName) throws Exception;
    public StockPriceProvider getStockPriceProvider();
    public void getTransactions(String playerName, String sorted) throws Exception;
    public void getTransactionsWithShare(String playerName, String shareName) throws Exception;
}
