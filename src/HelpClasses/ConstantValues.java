/**
 * ConstantValues.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     02.06.2014
 * Version:   2.0
 */


package HelpClasses;
/**
 * Definiert Parameter des Projekts
 * 
 */
public class ConstantValues {
    /**
     * Startkapital für neue Spieler
     */
    private static int startKapital = 500000;
    public static int getStartKapital(){return startKapital;}
    public static void setStartKapital(int i){startKapital = i;}
    
    /**
     * Legt den maximalen Wert fest, um den der RandomStockPriceProvider den Aktienkurs ändert
     */
    public static int maxStockChange = 10;
    public static int getMaxStockChange(){return maxStockChange;}
    public static void setMaxStockChange(int i){maxStockChange = i;}
            
    /**
     * Wert um den der Aktienkurs höher sein muss als der Einkaufspreis, damit der TradeAgent Aktien verkauft
     */
    public static int sellDiff = 10;
    public static int getSellDiff(){return sellDiff;}
    public static void setSellDiff(int i){sellDiff = i;}
    
    /**
     * Anteil der gehaltenen Aktien den der TradeAgent verkaufen soll
     * Alle = 100
     */
    public static int sellPerc = 100;
    public static int getSellPerc(){return sellPerc;}
    public static void setSellPerc(int i){sellPerc = i;}
    
    /**
     * Legt fest, wie oft der Aktienkurs gefallen sein soll, bevor der TradeAgent Aktien kauft
     */
    public static int buyWhen = 2;
    public static int getBuyWhen(){return buyWhen;}
    public static void setBuyWhen(int i){buyWhen = i;}
    
    /**
     * Legt fest, wie viele Aktien der TradeAgent Maximal kaufen darf
     */
    public static int buyMax = 20;
    public static int getBuyMax(){return buyMax;}
    public static void setBuyMax(int i){buyMax = i;}
    
    /**
     * Maximale Anzahl an Einträgen in der Transaktionsliste
     */
    public static int maxTransactions = 50;
    public static int getMaxTransactions(){return maxTransactions;}
    public static void setMaxTransactions(int i){maxTransactions = i;}
}
