/**
 * StockPriceViewer.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Viewer;
import Assets.Share;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import StockPrice.StockPriceProvider;
import Timer.StockTimer;

/**
 * Ausgabefenster für verfügbare Aktien und deren Kurse
 */
public class StockPriceViewer extends JFrame {
    private static final long serialVersionUID = 1L;
    private final StockPriceProvider spp;
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
         * Gibt Zeilenweise die Aktien und ihre Kurse aus
         * @return Textausgabe als String mit HTML-Tags
         */
        private String createText() {     
            String output = "<html><body><br>"; 
            for (Share share : spp.shares) {
                output += share.toString() + "<br>";
            }
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormatter = DateFormat.getDateTimeInstance();
            output += "<br>"+dateFormatter.format(date) + "</body></html>";
            return output;
        }
    }

    public StockPriceViewer(StockPriceProvider spp) {
        this.spp = spp;
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