/**
 * SocketStockPriceProvider.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   17.07.2014
 * Version: 1.0
 */


package StockPrice;
import Assets.Share;
import java.io.InputStream;
import java.io.BufferedReader;

public class OnlineStockPriceProvider extends StockPriceProvider{
    /**
     * Ändert den Kurs der Aktie basierend auf aus dem Internet abgerufenen Werten
     * @param share: Aktie als Share
     */
    @Override
    public void updateShareRate(Share share){
        String url = "https://www.google.com/finance?q="+share.getGoogleID()+"&sq=audi&sp=1&ei=LI7HU4iVBuP3wAPPk4HgDw";
        try{
            
        }catch(Exception e){
            System.out.println("Fehler");
        }
    }
}

