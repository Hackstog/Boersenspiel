/**
 * AllViewer.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     31.05.2014
 * Version:   2.1
 */


package Viewer;
import AccountManager.AccountManager;
import Assets.Share;
import Player.Player;
import StockPrice.StockPriceProvider;
import Timer.StockTimer;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 * Gemeinsames Ausgabefenster für Spieler und Aktien
 */
public class AllViewer extends JFrame {
    private static final long serialVersionUID = 1L;
    private final AccountManager am;
    private final StockPriceProvider stockPP;
    private final StockTimer timer = StockTimer.getInstance();
    private static final int TICK_PERIOD = 1000;
    private final Timer ticker = timer.getTimer();
    private final JLabel clockLabel;
    
    private class TickerTask extends TimerTask {
        @Override
        public void run() {
            String output = createText();
            clockLabel.setText(output);
            clockLabel.repaint();
        }

        /**
         * Definiert die Textausgabe als String im Format:
         * 1. Zeilenweise Aktien mit aktuellem Kurs
         * 2. Spieler mit Kontostand, darunter Zeilenweise deren Aktien mit Anzahl
         * 3. Aktuelles Datum und Uhrzeit
         * @return Ausgabe als String mit HTML-Tags
         */
        private String createText() {     
            String output = "<html><body>Aktien:<br>"; 
            for (Share share : stockPP.shares) {
                output += share.toString()+", Aktueller Kurs: "+share.getWert()+"<br>";
            }
            output += "<br><br><br>Spieler:<br>";
            /**
             * Ausgabe Spieler + Konto + zeilenweise Aktien
             */
//            for(int i=0; am.getPlayer()[i]!=null; i++){
//                output += am.getPlayer()[i].toString()+"<br>";
//                output += am.getPlayer()[i].getDepositAccount().toString()+"<br><br>";
//            }
            ListIterator<Player> iterator = am.getPlayer().listIterator();
            while(iterator.hasNext()){
                Player p = iterator.next();
                output += p.toString()+", Kontostand: "+(double) p.getCashAccount().getWert()/100+" €<br>";
                output += p.getDepositAccount().toString()+"<br><br>";
            }
                      
            /**
             * Datum
             */
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormatter = DateFormat.getDateTimeInstance();
            output += "<br>"+dateFormatter.format(date) + "</body></html>";
            return output;
        }
    }
    
    /**
     * Definiert ein Viewerfenster
     * @param accountManager: AccountManager
     */
    public AllViewer(AccountManager accountManager) {
        this.am = accountManager;
        this.stockPP = accountManager.getStockPriceProvider();
        clockLabel = new JLabel("loading ...");
        add("Center", clockLabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200, 200);
        setVisible(true);
    }

    /**
     * Zeichnet das Fenster und aktualisiert die Werte
     */
    public void start() {
        ticker.scheduleAtFixedRate(new TickerTask(), 1000, TICK_PERIOD);
    }
}