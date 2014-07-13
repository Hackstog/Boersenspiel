/**
 * CashAccount.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   1.1
 */


package Assets;

/**
 * Bargeldkonto des Spielers
 */
public class CashAccount extends Asset{
    /**
     * Legt einen neuen CashAccount mit dem als Parameter angegebenen Kapital an
     * @param startkapital: Startkapital des CashAccounts als Long
     */
    public CashAccount(long startkapital){
        this.wert = startkapital;
    }
    
    /**
     * Überschreibt abstrakte Methode aus Asset
     * Liefert den Wert des CashAccount zurück
     * @return Wert als long
     */
    @Override
    public long getWert(){
        return wert;
    }
    
    /**
     * Ändert den Wert des CashAccount um den angegebenen Wert
     * Positiver Wert: Erhöhung;  Negativer Wert: Verringerung
     * @param diff: Wert um den der Accountwert geändert werdne soll als long
     */
    public void changeWert(long diff){
        this.wert = getWert() + diff;
    }
    
    /**
     * Liefert den Kontostand als String zurück
     * @return Kontostand als String
     */
    @Override
    public String toString(){
        return "Kontostand: "+wert;
    }
}