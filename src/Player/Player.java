/**
 * Player.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.1
 */


package Player;
import Assets.ShareDepositAccount;
import Assets.CashAccount;
import HelpClasses.ConstantValues;
import HelpClasses.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Definiert einen Spieler mit den zugehörigen Methoden
 */
public class Player {
    private final String name;
    private final ShareDepositAccount aktienDepot;
    private final CashAccount konto;
    private final List<Transaction> transactions = new ArrayList<>();
    
    /**
     * Konstruktur
     * Erzeugt einen neuen Spieler mit dem angegebenen Namen und Kontostand
     * @param name: Name des Spielers als String
     * @param startkapital: Startkapital des Spielers als long
     * @deprecated 
     */
    @Deprecated
    public Player(String name, long startkapital){
        this.name = name;
        this.konto = new CashAccount(startkapital);
        this.aktienDepot = new ShareDepositAccount();
    }
    
    /**
     * Überlagerter Konstruktor:
     * Wird Kein Startkapital sondern nur ein Spielername übergeben wird ein Standardkapital verwendet
     * @param name: Name des Spielers als String
     */
    public Player(String name){
        this.name = name;
        this.aktienDepot = new ShareDepositAccount();
        this.konto = new CashAccount(ConstantValues.STARTKAPITAL);
    }
    
    /**
     * Gibt den Namen des Spielers zurück
     * @return Name als String
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gibt das Aktiendepot des Spielers zurück
     * @return ShareDepositAccount
     */
    public ShareDepositAccount getDepositAccount(){
        return aktienDepot;
    }
    
    /**
     * Gibt das Konto des Spielers zurück
     * @return CashAccount
     */
    public CashAccount getCashAccount(){
        return konto;
    }
    
    /**
     * Gibt den Spielernamen als String zurück
     * @return String
     */
    @Override
    public String toString(){
        return name;
    }
    
    /**
     * Gibt die Liste der Transaktionen des Spielers zurück
     * @return List<Transaction>
     */
    public List<Transaction> getTransactions(){
        return transactions;
    }
}
