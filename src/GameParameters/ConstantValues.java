/**
 * ConstantValues.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     02.06.2014
 * Version:   1.0
 */


package GameParameters;
/**
 * Definiert Parameter des Projekts
 * 
 */
public class ConstantValues {
    /**
     * Startkapital für neue Spieler
     */
    public static final int STARTKAPITAL = 500000;
    
    /**
     * Legt den maximalen Wert fest, um den der RandomStockPriceProvider den Aktienkurs ändert
     */
    public static final int MAXSTOCKCHANGE = 10;
            
    /**
     * Wert um den der Aktienkurs höher sein muss als der Einkaufspreis, damit der TradeAgent Aktien verkauft
     */
    public static final int SELLDIFF = 30;
    
    /**
     * Anteil der gehaltenen Aktien den der TradeAgent verkaufen soll
     * Alle = 100
     */
    public static final int SELLPERC = 100;
    
    /**
     * Legt fest, wie oft der Aktienkurs gefallen sein soll, bevor der TradeAgent Aktien kauft
     */
    public static final int BUYWHEN = 4;
    
    /**
     * Legt fest, wie viele Aktien der TradeAgent Maximal kaufen darf
     */
    public static final int BUYMAX = 20;
    
    /**
     * Maximale Anzahl an Einträgen in der Transaktionsliste
     */
    public static final int MAXTRANSACTIONS = 20;
}
