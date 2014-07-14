/**
 * Transaction.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   13.07.2014
 * Version: 1.0
 */


package HelpClasses;
import Assets.Share;
import java.util.Date;
import java.util.Calendar;

/**
 * Verwaltet die Transaktionen eines Spielers
 */
public class Transaction implements Comparable<Transaction>{
    private final String share;
    private final Date date;
    private final int number;
    private final String type;
    private final Calendar cal = Calendar.getInstance();
    private static String sortKey;
    
    public Transaction(Share share, int number, String type){
        this.share = share.getName();
        this.date = cal.getTime();
        this.number = number;
        this.type = type;
    }
    
    /**
     * Legt fest nach welchem Wert die Transaktionen sortiert werden sollen
     * @param t
     * @return 
     */
    @Override
    public int compareTo(Transaction t){
        if(sortKey.equals("t")){
            return date.compareTo(t.getDate());
        }else{
            return share.compareTo(t.getShareName());
        }
    }
    
    /**
     * Liefert den Namen der Aktie zurück
     * @return String
     */
    public String getShareName(){
        return share;
    }
    
    /**
     * Liefert das Datum der Transaktion zurück
     * @return Date
     */
    public Date getDate(){
        return date;
    }
    
    /**
     * Liefert die Anzahl der Transaktion zurück
     * @return String
     */
    public String getType(){
        return type;
    }

    /**
     * Legt den Schlüssel der Sortierung fest
     * @param s
     */
    public static void setSortKey(String s) throws IllegalArgumentException{
        if(!(s.equals("s")) && !(s.equals("t"))){
            throw new IllegalArgumentException("Ungültiger Sortierschlüssel");
        }
        sortKey = s;
    }
    
    /**
     * Gibt die Parameter der Transaktion als String zurück
     * @return 
     */
    @Override
    public String toString(){
        return date + " | " + share + " | " + number + " | " + type;
    }
}
