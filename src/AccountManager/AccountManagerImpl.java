/**
 * AccountManagerImpl.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     29.05.2014
 * Version:   3.0
 */


package AccountManager;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import Assets.Share;
import Assets.ShareItem;
import Player.Player;
import Player.TradeAgent;
import HelpClasses.CollectionObjectFinder;
import HelpClasses.Transaction;
import Exceptions.PlayerExistsException;
import Exceptions.NotEnoughMoneyException;
import Exceptions.NotEnoughSharesException;
import Exceptions.PlayerNotFoundException;
import Exceptions.WrongNumberException;
import StockPrice.StockPriceProvider;
import HelpClasses.ConstantValues;

/**
 * Implementierung des Interface AccountManager 
 */
public class AccountManagerImpl implements AccountManager{
    private final List<Player> player = new ArrayList<>();
    private final List<TradeAgent> agent = new ArrayList<>();
    private final StockPriceProvider stockPP;
    private final CollectionObjectFinder collectionObjectFinder = CollectionObjectFinder.getInstance();
    
    /**
     * Erzeugt einen neuen Logger
     * Wird nicht länger benötigt, das Logging über Proxy läuft
     * @deprecated
     */
    @Deprecated
    public static final Logger logger = Logger.getLogger(AccountManagerImpl.class.getName());
    
    /**
     * Erzeugt einen neuen AccountManager
     * Weißt dem Logger einen neuen Filehandler zu
     * @param stockPriceProvider: StockPriceProvider
     */
    public AccountManagerImpl(StockPriceProvider stockPriceProvider){
        this.stockPP = stockPriceProvider;
//        try{
//            Handler handler = new FileHandler( "log.txt" );
//            logger.addHandler( handler );
//        }catch (IOException e) {
//            logger.log(Level.SEVERE, "Exception: ", e);
//        }
    }
    
    /**
     * Durchläuft das Array und speichert an der nächsten freien Stelle den neuen Spieler
     * @param playerName: Name des Spielers als String
     * @param startKapital: Startkapital des Spielers als long
     * @deprecated
     */
    @Override
    @Deprecated
    public void createPlayer(String playerName, long startKapital) throws PlayerExistsException{
        if(collectionObjectFinder.exists(this.getPlayer(), playerName)){
            throw new PlayerExistsException();
        }
        this.getPlayer().add(new Player(playerName, startKapital)); 
    }
    
    /**
     * Überlagerte Methode: Wenn kein Kapital angegeben ist, wird der Standardkonstrutkor aufgerufen
     * @param playerName: Name des Spielers als String
     */
    @Override
    public void createPlayer(String playerName) throws PlayerExistsException{
        if(collectionObjectFinder.exists(this.getPlayer(), playerName)){
            throw new PlayerExistsException();
        }else{
            this.getPlayer().add(new Player(playerName));
        }
    }
    
    /**
     * Wählt eine Aktie aus dem Array aus
     * @param name: Name der Aktie als String
     * @return Share: Aktie als Share
     */
    @Override
    public Share getShare(String name) throws Exception{
        return stockPP.getShare(name);
    }
    
    /**
     * Wählt einen Spieler aus dem Array aus
     * @param name: Name des gesuchten Spielers als String
     * @return Player: Spieler
     */
    @Override
    public Player getPlayer(String name) throws Exception{
        Player p = (Player) collectionObjectFinder.search(this.player, name, new PlayerNotFoundException());
        return p;
    }
    
    /**
     * Liefert alle Spieler als Liste zurück
     * @return List<Player>
     */
    @Override
    public List<Player> getPlayer(){
        return this.player;
    }
    
    /**
     * Liefert alle Agenten Zurück
     * @return List<TradeAgent>
     */
    @Override
    public List<TradeAgent> getAgent(){
        return this.agent;
    }
    
    /**
     * Erstellt ein Aktienpaket aus der angegebenen Aktie und der Anzahl,
     * Fügt dieses in das Aktiendepots des Spielers ein
     * Verringert den Kontostand des Spielers um den Wert der gekauften Aktien
     * @param playerName: Name des Spielers der die Aktie kaufen soll als String
     * @param shareName: Name der Aktie die gekauft werden soll als String
     * @param anzahl: Anzahl der zu kaufenden Aktien als Int
     */
    @Override
    public void buyShare(String playerName, String shareName, int anzahl) throws Exception{
        Share share = getShare(shareName);
        ShareItem paket = new ShareItem(share, anzahl);
         /**
         * Try-Block:
         * Wenn negative Anzahl an Aktien gekauft werden soll (Oder 0) -> Exception
         * Wenn Wert des Pakets den Kontostand übersteigt -> Exception
         * Ansonsten Aktienpakten mit dem selben Namen suchen:
         * Wenn nicht vorhanen Paket in DepositAccount einfügen, ansonsten Anzahl in vorhandenem Paket ändern
         */       
        try{
            if(anzahl <= 0){
                throw new WrongNumberException();
            }else if(paket.getWert() > getPlayer(playerName).getCashAccount().getWert()){
                throw new NotEnoughMoneyException();
            }else{
                if(getPlayer(playerName).getDepositAccount().search(shareName)==null){
                    getPlayer(playerName).getDepositAccount().insert(paket);
//                    logger.fine("Neues Aktienpaket anlegen für Aktie '"+shareName+"'!");
                }else{
                    getPlayer(playerName).getDepositAccount().search(shareName).changeAnzahl(anzahl);
//                    logger.fine("Anzahl von Aktie '"+shareName+"' um"+anzahl+" erhöht!");
                }
                getPlayer(playerName).getCashAccount().changeWert(-1*anzahl*share.getWert());
                getPlayer(playerName).getTransactions().add(new Transaction(share, anzahl, "Bought"));
                if(getPlayer(playerName).getTransactions().size() > ConstantValues.MAXTRANSACTIONS){
                    getPlayer(playerName).getTransactions().remove(0);
                }
            }
        }catch(Exception e){
//            logger.log(Level.SEVERE, "Exception: ", e);
        }
//        logger.log(Level.INFO, "Erfolgreich!");
    }
    
    /**
     * Durchsucht das Aktiendepot des Spielers nach einem Paket der angebenen Aktie
     * Verringert die Anzahl der Aktien in diesem Paket
     * Erhöht den Kontostand des Spielers um den Wert der verkauften Aktien
     * @param playerName: Name des Spieler der die Aktien verkaufen soll als String
     * @param shareName: Name der zu verkaufenden Aktie als String
     * @param anzahl: Anzahl der zu verkaufenden Aktien als Int
     */
    @Override
    public void sellShare(String playerName, String shareName, int anzahl) throws Exception{ 
        Share share = getShare(shareName);
        /**
         * Try-Block:
         * Wenn negative Anzahl an Aktien Verkauft werden soll (oder 0) -> Exception
         * Wenn mehr Aktien verkauft werden sollen als vorhanden -> Exception
         * Wenn alle Aktien verkauft werden sollen -> Ganzes Paket löschen
         * Ansonsten Anzahl im Paket öndern
         */
        try{
            ShareItem paket = this.getPlayer(playerName).getDepositAccount().search(shareName);
            if(anzahl <= 0){
                throw new WrongNumberException();
            }else if(anzahl > paket.getAnzahl()){
                throw new NotEnoughSharesException();
            }else{
                if(anzahl == paket.getAnzahl()){
                    this.getPlayer(playerName).getDepositAccount().remove(paket);
//                    logger.fine("Komplettes Aktienpaket "+shareName+" gelöscht!");
                }else{
                    paket.changeAnzahl(-anzahl);
//                    logger.fine("Anzahl der Aktien "+shareName+" um "+anzahl+" verringert!");
                }
                getPlayer(playerName).getCashAccount().changeWert(anzahl*share.getWert());
                getPlayer(playerName).getTransactions().add(new Transaction(share, anzahl, "Sold"));
                if(getPlayer(playerName).getTransactions().size() > ConstantValues.MAXTRANSACTIONS){
                    getPlayer(playerName).getTransactions().remove(0);
                }
            }
        }catch(Exception e){
//            logger.log(Level.SEVERE, "Exception: ", e);
        }
//        logger.log(Level.INFO, "Erfolgreich!");
    }
    
    /**
     * Wählt den Spieler aus und gibt den Wert dessen CashAccounts zurück
     * @param playerName: Name des Spielers als String
     * @return Kontostand als long
     */
    @Override
    public long getValueOfCashAccount(String playerName) throws Exception{
        return this.getPlayer(playerName).getCashAccount().getWert();
    }
    
    /**
     * Wählt den Spieler aus und gibt den Wert dessen ShareDepositAccounts zurück
     * @param playerName: Name des Spielers als String
     * @return Aktiendepotwert als long
     */
    @Override
    public long getValueOfDepositAccount(String playerName) throws Exception{
        return this.getPlayer(playerName).getDepositAccount().getWert();
    }
    
    /**
     * Ruft die Funktionen getValueOfCashAccount und getValueOfDepositAccount auf und gibt die addierten Werte zurück
     * @param playerName: Name des Spielers als String
     * @return Gesamtvermögen des Spielers als Long
     */
    @Override
    public long getValueOfAll(String playerName) throws Exception{
        long a = this.getValueOfCashAccount(playerName);
        long b = this.getValueOfDepositAccount(playerName);
        return a+b;
    }
    
    /**
     * Gibt den aktuellen Kurs einer angegebenen Aktie zurück
     * @param share: Aktie als Share
     * @return Kurs einer Aktie als long
     */
    @Override
    public long getKurs(Share share){
       return share.getWert();
    }
    
    /**
     * Durchläuft das Array der Aktien und gibt diese als zeilenweise String zurück
     * @return Alle Aktien als String
     */
    @Override
    public String getShares(){
        String result = "";
        for (Share share : stockPP.shares) {
            if (share == null) {
                break;
            } else {
                result = result + share.toString() + " \n";
            }
        }
        return result;
    }
    
    /**
     * Startet den Agenten
     * @param playerName: Name des Spielers als String
     */
    @Override
    public void startAgent(String playerName) throws Exception{
        this.getAgent().add(new TradeAgent(playerName, this.getStockPriceProvider(), this));
    }
    
    /**
     * Liefert den StockPriceProvider des AccountManagers zurück
     * @return StockPriceProvider
     */
    @Override
    public StockPriceProvider getStockPriceProvider(){
        return stockPP;
    }
    
    /**
     * Liefert die Transaktionen eines Spielers zurück
     * @param playerName
     * @param sortKey
     * @param printType
     */
    @Override
    public void getTransactions(String playerName, String sortKey, String printType) throws Exception{
        try{
            Transaction.setSortKey(sortKey);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        Collections.sort(getPlayer(playerName).getTransactions());
        if (printType.equals("plain")){
            for(Transaction t : getPlayer(playerName).getTransactions()){
                System.out.println(t.toString());
            }
        }else if (printType.equals("html")){
            File transactions = new File("Ausgabe/Transactions_"+playerName+".html");
            File folder = transactions.getParentFile();
            if(!folder.exists() && !folder.mkdirs()){
                throw new IllegalStateException("Ordner konnte nicht erstellt werden!");
            }
            FileWriter fileWriter = new FileWriter(transactions);
            try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
                writer.write("<html>\n\t<head>\n\t\t<title>\n\t\t\tTransaktionen von "+playerName+"\n\t\t</title>\n\t</head>\n\t<body>\n");
                for(Transaction t : getPlayer(playerName).getTransactions()){
                    writer.write("\t\t\t"+t.toString()+"<br>\n");
                }
                writer.write("\t</body>\n</html>");
            }
        }
    }
    
    /**
     * Liefert die Transaktionen eines Spielers zurück
     * @param playerName
     * @param shareName
     * @param printType
     */
    @Override
    public void getTransactionsWithShare(String playerName, String shareName, String printType) throws Exception{
        Transaction.setSortKey("t");
        Collections.sort(getPlayer(playerName).getTransactions());
        if (printType.equals("plain")){
            for(Transaction t : getPlayer(playerName).getTransactions()){
                if(t.getShareName().equals(shareName)){
                System.out.println(t.toString());
            }
            }
        }else if (printType.equals("html")){
            File transactions = new File("Ausgabe/Transactions_"+playerName+".html");
            File folder = transactions.getParentFile();
            if(!folder.exists() && !folder.mkdirs()){
                throw new IllegalStateException("Ordner konnte nicht erstellt werden!");
            }
            FileWriter fileWriter = new FileWriter(transactions);
            try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
                writer.write("<html>\n\t<head>\n\t\t<title>\n\t\t\tTransaktionen von" +playerName+"\n\t\t</title>\n\t</head>\n\t<body>\n");
                for(Transaction t : getPlayer(playerName).getTransactions()){
                    if(t.getShareName().equals(shareName)){
                        writer.write("\t\t\t"+t.toString()+"<br>\n");
                    }
                }
                writer.write("\t</body>\n</html>");
            }
        }
    }
}
