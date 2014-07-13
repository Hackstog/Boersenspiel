/**
 * ShareItem.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   1.1
 */


package Assets;

/**
 * Aktienpaket bestehend aus einer Aktie und der Anzahl
 */
public class ShareItem extends Asset{
    private final Share shares;
    private int anzahl;
    
    /**
     * Erzeugt ein neues Aktienpaket aus der angegebene Aktie und der Anzahl
     * @param shares: Aktie als Share
     * @param anzahl: Anzahl als int
     */
    public ShareItem(Share shares, int anzahl){
        this.shares = shares;
        this.anzahl = anzahl;
    }
    
    /**
     * Überschreibt abstrakte Methode aus Asset
     * Berechnet den Wert des Aktienpaktes aus dem Wert einer einzelnen Aktie und der Anzahl
     * @return Wert als long
     */
    @Override
    public long getWert(){
        return shares.getWert()*anzahl;
    }
    
    /**
     * Ändert die Anzahl der im Paket enthaltenen Aktien
     * @param n: Wert um den die Anzahl geändert werden soll als Int
     */
    public void changeAnzahl(int n){
        anzahl = getAnzahl()+n;
    }
    
    /**
     * Liefert die Anzahl der Aktien im Paket zurück
     * @return Anzahl als int
     */
    public int getAnzahl(){
        return anzahl;
    }
    
    /**
     * Liefert den Namen der Aktie zurück
     * @return Name als String
     */
    public String getName(){
        return shares.name;
    }
    
    /**
     * Liefert die Aktie mit der zugehörigen Anzahl als String zurück
     * @return String 
     */
    @Override
    public String toString(){
        return anzahl+" Stück von Aktie "+shares.getName();
    }
}