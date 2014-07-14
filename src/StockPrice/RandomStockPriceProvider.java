/**
 * RandomStockPriceProvider.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package StockPrice;
import java.util.Random;
import Assets.Share;
import HelpClasses.ConstantValues;

/**
 * Zufälliger Kurs für eine Aktie
 */
public class RandomStockPriceProvider extends StockPriceProvider{
    /**
     * Ändert dem Kurs um einen Zufallswert zwischen 0 und 10
     * Die Richtung der Änderung wird durch eine zweite Zufallszahl bestimmt
     * @param share: Aktie als Share 
     */
    @Override
    public void updateShareRate(Share share){
        Random rand = new Random();
        long diff = rand.nextInt(ConstantValues.MAXSTOCKCHANGE+1);
        long vorzeichen = rand.nextInt(3);
        //Zum Teste des TradeAgent: Kurse sinken nur
        //vorzeichen = 2;
        long aktuell = share.getWert();
        
        if(vorzeichen == 1){
            share.setWert(aktuell+diff);
        }else if(vorzeichen == 2){
            share.setWert(aktuell-diff);
        }
    }
}