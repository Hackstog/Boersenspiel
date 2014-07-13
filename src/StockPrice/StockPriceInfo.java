/**
 * StockPriceInfo.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package StockPrice;
import Assets.Share;
import java.util.TreeSet;

/**
 * Interface zur Steuerung des Aktienkurses
 */
public interface StockPriceInfo {
    public boolean isShareListed(Share share);
    public long getCurrentShareRate(String shareName) throws Exception;
    public TreeSet<Share> getAllSharesAsSnapshot();
}