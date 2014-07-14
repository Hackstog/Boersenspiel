/**
 * TradeAgent.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0 
 */


package Player;
import AccountManager.AccountManager;
import AccountManager.AccountManagerImpl;
import Assets.ShareItem;
import Assets.Share;
import HelpClasses.ConstantValues;
import Timer.StockTimer;
import StockPrice.StockPriceProvider;
import java.util.Random;
import java.util.TimerTask;
import java.util.TreeSet;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Agent, der automatisch Aktien kauft und wieder verkauft
 */
public class TradeAgent{
    private final StockTimer timer = StockTimer.getInstance();
    private AccountManager am;
    private String player;
    private StockPriceProvider stockpp;
    private long[] before;
    private long[] now;
    private long[] kauf;
    private int[] tendenz; 
    private boolean init = true;
    
    /**
     * Die als Liste gespeicherten Aktien in ein Array ablegen,
     * da sonst er komplette Agent umgeschrieben werden müsse
     */
    private Share[] sharesAsArray;
    
    /**
     * Erzeugt einen neuen Logger zu Testzwecken
     * 
     */
    public static final Logger logger = Logger.getLogger(AccountManagerImpl.class.getName());
    
    public TradeAgent (String playerName, StockPriceProvider stockPriceProvider, AccountManagerImpl accountManagerImpl){
        this.player = playerName;
        this.stockpp = stockPriceProvider;
        this.am = accountManagerImpl;
        this.start();
        this.sharesAsArray = stockpp.getAllSharesAsSnapshot().toArray(new Share[stockpp.getAllSharesAsSnapshot().size()]);
        before = new long[sharesAsArray.length];
        now = new long[sharesAsArray.length];
        kauf = new long[sharesAsArray.length];
        tendenz = new int[sharesAsArray.length];
    }
    
    /**
     * Verhalten des Agenten:
     * - Ist der Kurs zweimal in Folge gefallen wird die Aktie gekauft
     * - Gekauft wird eine zufällige Anzahl von 10 - 20
     * - Liegt der Kurs um mindestens 10 höher als der Einkaufspreis werden alle gehaltenen Aktien verkauft
     * @throws java.io.IOException wenn ein Fehler auftritt
     */
    public void trade() throws IOException, Exception{
        /**
         * Beim ersten Durchlauf das Tendenz-Array mit Nullen initialisieren
         * Kauf-Array mit long.MAX_VALUE-100 initialiseren (Bei 0 wäre derzeitiger Kurs immer höher und es würden Aktion verkauft werden, die nicht im Depot sind)
         * Gleichzeitig das erste before-Array füllen (später wird als before das vorhergehende now-Array verwendet)
         */
        if(init){
            for(int i=0; i<sharesAsArray.length; i++){
                tendenz[i] = 0;
                kauf[i] = Long.MAX_VALUE-1000;
                before[i] = sharesAsArray[i].getWert();
            }
            init = false;
        }

        /**
         * Kurse aktualisieren
         */
        stockpp.updateShareRates();
        
        /**
         * Neue Kurse in now-Array speichern
         * Kurse in before- und now-Array vergleichen und tendenz-Werte entsprechend anpassen
         */
        for(int i=0; i<sharesAsArray.length; i++){
            now [i] = sharesAsArray[i].getWert();
            if(now[i]<before[i]){
                tendenz[i]++;
            }else if(now[i]>before[i] && tendenz[i]>0){
                tendenz[i]--;
            }
            
            /**
             * Wenn tendenz-Wert einen bestimmen Wert erreicht Zufallswert an Aktien kaufen und Wert zurück auf 0
             * Gleichzeitig Wert im Kauf Array speichern
             * Der zu erreichende Wert wird in der Klasse ConstantValues festgelegt
             */
            if(tendenz[i]>=ConstantValues.BUYWHEN){
                Random rand = new Random();
                int anzahl = rand.nextInt(ConstantValues.BUYMAX+1);
                if(anzahl*now[i] < am.getValueOfCashAccount(player)){
                    am.buyShare(player, sharesAsArray[i].getName(), anzahl);
                    tendenz[i] = 0;
                    if(kauf[i]<now[i]){
                        kauf[i] = now[i];
                    }
                }
            }
            /**
             * Wenn der Kurs mehr als einen bestimmten Wert höher als der Einkaufspreis liegt alle Aktien verkaufen
             * Der konkrete Wert wird in der Klasse ConstantValues festgelegt
             */
            if(now[i]>kauf[i]+ConstantValues.SELLDIFF){
                /**
                 * Ermittelt den Namen der zu verkaufenden Aktie
                 * Prüft ob die Aktie im Depot liegt
                 * Verkauft einen in der Klasse ConstantValues festgelegten Anteil der gehaltenen Aktien
                 * Setzt den Wert im Array 'kauf' zurück auf den Ausgangswert 
                 */
                String shareName = sharesAsArray[i].getName();
                ShareItem paket = am.getPlayer(player).getDepositAccount().search(shareName);
                if(paket!=null){
                    int anzahl = ConstantValues.SELLPERC/100 * paket.getAnzahl();
                    am.sellShare(player, shareName, anzahl);
                    kauf[i]=Long.MAX_VALUE-1000;
                }
            }
            /**
             * Now-Array zu nächstem Before-Array
             */
            before[i] = now[i];
        }
    }
    
    /**
     * Startet den Agenten
     */
    public void start(){
        this.timer.getTimer().scheduleAtFixedRate(new TimerTask(){
            long i = 1;
            @Override
            public void run(){
                try{
                    TradeAgent.this.trade();
                    
                }catch(Exception e){
                    e.printStackTrace(System.out);
                }
            }
        }, 2000, 1000);
    }
}
