/**
 * StockTimer.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   1.0
 */


package Timer;
import java.util.Timer;

/**
 * Timer für zeitbasierte Aktionen
 */
public class StockTimer {
	private final Timer timer = new Timer ();
	private static final StockTimer StockTimerObj = new StockTimer ();

        /**
         * Liefert die Instanz des Timers zurück
         * @return StockTimer
         */
	public static StockTimer getInstance ()
	{
		return StockTimerObj;
	}

        /**
         * Liefert den Timer zurück
         * @return Timer 
         */
	public Timer getTimer() 
	{
		return this.timer;
	}
}
