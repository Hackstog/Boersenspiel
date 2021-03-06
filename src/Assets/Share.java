/**
 * Share.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   2.4
 */


package Assets;
import java.util.ArrayList;
import java.util.List;

/**
 * Aktie
 */
public class Share extends Asset implements Comparable<Share>{
    private List<Long> lastRates = new ArrayList<>();
    private String googleID;
    
    /**
     * Legt ein neues Share-Objekt mit den als Parameter angegebenen Namen und Kurs an
     * @param name: Name der Aktie als String
     * @param kurs: Kurs der Aktie als long
     */
    public Share(String name, long kurs){
        this.name = name;
        this.wert = kurs;
    }
    
    /**
     * Liefert den Namen der Aktie zurück
     * @return Aktienname als String 
     */
    public String getName(){
        return name;
    }
    
    /**
     * Überschreibt abstrakte Methode aus Asset
     * Liefert den Wert der Aktie zurück
     * @return Wert als long
     */
    @Override
    public long getWert(){
        return wert;
    }
    
    /**
     * Überprüft, das als Parameter angegebene Object eine Instanz von Share ist
     * Wenn dies zutrifft wird das Object zu einer Share gecastet und die beiden Namen und Kurse verglichen
     * @param share: Aktie als Object
     * @return Ob 2 Aktien übereinstimmen als boolean
     */
    @Override
    public boolean equals(Object share){
        boolean b = false;
        if(share instanceof Share){
            Share s = (Share) share;
            if(s.getName().equals(name) && s.getWert() == wert){
                b = true;
            }
        }
        return b;
    }
    
    /**
     * Überschreibt abstrakte Methode aus Asset
     * Liefer den Namen sowie den aktuellen Kurs der Aktie
     * @return Aktien mit zugehörigem Kurs als String 
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * compareTo zum Vergleich der Aktien in TreeSet-Collection
     * @param share
     * @return 
     */
    @Override
    public int compareTo (Share share){
        return name.compareTo(share.getName());
    }
    
    /**
     * Liefert die Liste der letzten Aktienpreise zurück
     * @return 
     */
    public List<Long> getLastRates(){
        return lastRates;
    }
    
    /**
     * Liefert die GoogleID der Aktie zurück
     * @return 
     */
    public String getGoogleID(){
        return googleID;
    }
}
