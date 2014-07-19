/**
 * StockPriceProvider.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package StockPrice;
import Assets.Share;
import Exceptions.ShareNotFoundException;
import Timer.StockTimer;
import HelpClasses.CollectionObjectFinder;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementiert das Interface StockPriceInfo
 */
abstract public class StockPriceProvider implements StockPriceInfo{
    /**
     * Shares als TreeSet wegen Sortierung
     */
    public TreeSet<Share> shares;
    
    /**
     * Ruft den Timer als Singleton auf
     */
    StockTimer stimer = StockTimer.getInstance();
    private final CollectionObjectFinder collectionObjectFinder = CollectionObjectFinder.getInstance();

    public StockPriceProvider() {
        this.shares = new TreeSet<>();
        shares.add(new Share("Audi", 10000));
        shares.add(new Share("VW", 10000));
        shares.add(new Share("BMW", 10000));
        shares.add(new Share("Apple", 10000));
        shares.add(new Share("Siemens", 10000));
        shares.add(new Share("Google", 10000));
        shares.add(new Share("Microsoft", 10000));
        shares.add(new Share("Intel", 10000));
        shares.add(new Share("Facebook", 10000));
    }
    
    /**
     * Prüft ob eine Aktie existiert
     * @param share: Aktie als Share
     * @return Ob die Aktie gelistet ist als boolean
     */
    @Override
    public boolean isShareListed(Share share){
        boolean b = false;
        if(shares.contains(share)){
            b = true;
        }
        return b;
    }
    
    /**
     * Sucht eine angegebene Aktie und gibt ihren Wert zurück
     * @param shareName: Name der Aktie als String
     * @return Wert als long
     */
    @Override
    public long getCurrentShareRate(String shareName) throws Exception{
        return getShare(shareName).getWert();
    }
    
    /**
     * Abstrakte Methode, die in den verschiedenen Providerklassen überschrieben wird
     * @param share: Aktie als Share
     */
    abstract public void updateShareRate(Share share);
    
    /**
     * Ruft für jede Aktie die Methode updateShareRate auf
     */
    public void updateShareRates(){
        shares.stream().forEach((share) -> {
            updateShareRate(share);
            share.getLastRates().add(share.getWert());
            if(share.getLastRates().size() > 21){
                share.getLastRates().remove(0);
            }
        });
    }
    
    /**
     * Startet die Aktualisierung der Aktienkurse
     */
    public void startUpdate(){ 
        stimer.getTimer().scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run(){updateShareRates();}
        }, 2000, 1000);
    }
    
    /**
     * Sucht nach einer Aktie mit einem bestimmten Namen
     * @param name: Name der Aktie als String
     * @return Share
     */
    public Share getShare(String name) throws Exception{
        return (Share) collectionObjectFinder.search(this.getAllSharesAsSnapshot(), name, new ShareNotFoundException());
    }
    
    /**
     * Gibt alle Aktien zurück
     * @return TreeSet<Share>
     */
    @Override
    public TreeSet<Share> getAllSharesAsSnapshot(){
        return this.shares;
    }
    
    /**
     * Gibt die Anzahl der vorhandenen Aktien zurück
     * @return Anzahl als int
     */
    public int getNumberOfShares(){
        return shares.size();
    }
}