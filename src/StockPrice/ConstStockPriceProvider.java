/**
 * ConstStockPriceProvider.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package StockPrice;
import Assets.Share;

/**
 * Konstanter Kurs für eine Aktie
 */
public class ConstStockPriceProvider extends StockPriceProvider{
    /**
     * Lässt den Kurs einer Aktie unverändert
     * @param share: Aktie als Share
     */
    @Override
    public void updateShareRate(Share share){
    }
}
