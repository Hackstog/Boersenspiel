/**
 * ShareDepositAccount.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   2.0
 */


package Assets;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

/**
 * Aktiendepot eines Spieler als Verkettete Liste von Aktienpaketen
 */
public class ShareDepositAccount extends Asset{
    private final TreeMap<Share, Integer> shareDepot = new TreeMap<>();
    
    /**
     * Fügt ein ShareItem-Element in die Liste ein
     * @param s
     * @param n
     */
    public void insert(Share s, int n){
        shareDepot.put(s, n);
    }
    
    /**
     * Ändert die Anzahl der Aktien, falls diese schon in der Map vorhanden sind
     * Erzeugt einen neuen Eintrag, falls nicht
     */
    public void addShares(Share s, int n){
        if(shareDepot.containsKey(s)){
            n = n + shareDepot.get(s);
        }
        shareDepot.put(s, n);
    }
    
    
    public void removeShares(Share s, int n){
        int inDepot = shareDepot.get(s);
        if(inDepot == n){
            shareDepot.remove(s);
        }else{
        shareDepot.put(s, shareDepot.get(s)-n);
        }
    }
    
        
    /**
     * Entfernt ein ShareItem-Element aus der Liste
     * @param s
     */
    public void remove(Share s){
        shareDepot.remove(s);
    }
    
    
    /**
     * Gibt den Gesamtwert des Depots zurück
     * @return Wert als long
     */
    @Override
    public long getWert(){
        long l = 0;
        for(Map.Entry<Share, Integer> entry : shareDepot.entrySet()){
            Share s = entry.getKey();
            int n = entry.getValue();
            
            l += n * s.getWert();
        }
        return l;
    }
    
    /**
     * Gibt die Listenelemente als String zurück
     * @return Alle Elemente als String 
     */
    @Override
    public String toString(){
        String result = "";        
        for(Map.Entry<Share, Integer> entry : shareDepot.entrySet()){
            Share s = entry.getKey();
            result += s.toString();
            result += "<br>";
        }
        return result;
    }

    public TreeMap<Share, Integer> getPakete(){
        return shareDepot;
    }
}
