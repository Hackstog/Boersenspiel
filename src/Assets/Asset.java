/**
 * Asset.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   1.1
 */


package Assets;

/**
 * Oberklasse für alle Vermögenswerte
 */
abstract public class Asset {
    public long wert;
    public String name;
    
    /**
     * Abstrakte Methode, die in den abgeleiteten Klassen überschrieben wird
     * @return Wert als long
     */
    abstract public long getWert();
    
    /**
     * Abstrakte Methode, die in den abgeleiteten Klassen überschrieben wird
     * Ändert den Wert eines Assets
     * @param wertNeu: Neuer Wert als long
     */
    public void setWert(long wertNeu){
        if(wertNeu<0){
            wert = 0;
        }else{
            wert = wertNeu;
        }
    }
    
    /**
     * Abstrakte Metode, die in den abgeleiteten Klassen überschrieben wird
     * @return String 
     */
    @Override
    abstract public String toString();
}
