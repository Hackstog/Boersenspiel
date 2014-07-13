/**
 * PlayerViewer.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Viewer;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.text.DateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import AccountManager.AccountManager;
import Player.Player;
import Timer.StockTimer;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Ausgabefenster für Spieler und ihre zugehörigen Vermögenswerte
 */
public class PlayerViewer extends JFrame {
    private final AccountManager am;
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
         * Definiert die Textausgabe als String;
         * Gibt in einer Zeile die Spieler und ihren Kontostand aus,
         * darunter Zeilenweise die Aktien in dessen AktienDepot
         * @return Textausgabe als String mit HTML-Tags
         */
        private String createText() {     
            String output = "<html><body><br>"; 
//            for(int i=0; am.getPlayer()[i]!=null; i++){
//                output += am.getPlayer()[i].toString()+"<br>";
//                output += am.getPlayer()[i].getDepositAccount().toString()+"<br><br>";
//            }
            ListIterator<Player> iterator = am.getPlayer().listIterator();
            while(iterator.hasNext()){
                Player p = iterator.next();
                output += p.toString()+"<br>";
                output += p.getDepositAccount().toString()+"<br><br>";
            }
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormatter = DateFormat.getDateTimeInstance();
            output += "<br>"+dateFormatter.format(date) + "</body></html>";
            return output;
        }
    }

    public PlayerViewer(AccountManager am) {
        this.am = am;
        clockLabel = new JLabel("loading ...");
        add("Center", clockLabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(200, 200);
        setVisible(true);
    }

    public void start() {
        ticker.scheduleAtFixedRate(new TickerTask(), 1000, TICK_PERIOD);
    }
}