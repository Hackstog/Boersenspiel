/**
 * ShareDepositAccount.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   2.0
 */


package Assets;
import java.util.ArrayList;
import java.util.List;

/**
 * Aktiendepot eines Spieler als Verkettete Liste von Aktienpaketen
 */
public class ShareDepositAccount extends Asset{
    private final List<ShareItem> pakete = new ArrayList<>();

    /**
     * Fügt ein ShareItem-Element in die Liste ein
     * @param paket: Aktienpaket als ShareItem
     */
    public void insert(ShareItem paket){
        pakete.add(paket);
    }
    
    /**
     * Durchsucht die Liste nach einem Element und liefert eine Referenz darauf zurück
     * Wird das Element nicht gefunden wird 'null' zurückgeliefert
     * @param shareName: Name der Aktie als String
     * @return AktienPaket als ShareItem
     */
    public ShareItem search(String shareName){
        for (ShareItem s : pakete){
            if(shareName.equals(s.getName())){
                return s;
            }
        }
        return null;
    }
    
    /**
     * Entfernt ein ShareItem-Element aus der Liste
     * @param paket: Aktienpaket als ShareItem
     */
    public void remove(ShareItem paket){
        pakete.remove(paket);
    }
    
    
    /**
     * Gibt den Gesamtwert des Depots zurück
     * @return Wert als long
     */
    @Override
    public long getWert(){
        long i = 0;
        i = pakete.stream().map((s) -> s.getWert()).reduce(i, (accumulator, _item) -> accumulator + _item);
        return i;
    }
    
    /**
     * Gibt die Listenelemente als String zurück
     * @return Alle Elemente als String 
     */
    @Override
    public String toString(){
        String result = "";        
        for (ShareItem s : pakete){
            result += s.toString();
            result += "<br>";
        }
        return result;
    }

    public List<ShareItem> getPakete(){
        return pakete;
    }
}
