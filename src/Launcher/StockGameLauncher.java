/********************************************************************
 *                                                                  *
 *   BÖRSENSPIEL                                                    *
 *   Hochschule Augsburg                                            *
 *   Praktikum Programmieren 2 - Studiengang Bachelor Informatik    *
 *   Sommersemester 2014                                            *
 *                                                                  *
 *                                                                  *
 *   Autor:     Daniel Heigl                                        *
 *              daniel.heigl@hs-augsburg.de                         *
 *   License:   GPL3                                                *
 *   Datum:     01.06.2016                                          *
 *   Version:   5.2                                                 *
 *                                                                  *
 ********************************************************************/
/**
 * StockGameLauncher.java
 * Autor:     Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:     30.05.2014
 * Version:   2.0
 */


package Launcher;
import StockPrice.RandomStockPriceProvider;
import StockPrice.HistoricalStockPriceProvider;
import AccountManager.AccountManagerImpl;
import AccountManager.AccountManagerProxy;
import AccountManager.AccountManager;
import Viewer.StockPriceViewer;
import Viewer.PlayerViewer;
import Viewer.AllViewer;
import Exceptions.PlayerExistsException;
import Command.StockGameCommandProcessor;
import UI.StockGameUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * GameLauncher-Klasse mit main-Methode, die das Spiel startet
 */
public class StockGameLauncher extends Application{
    public static void main (String[] args) throws IOException{
        final HistoricalStockPriceProvider spp= new HistoricalStockPriceProvider();
        //AccountManagerImpl direkt anlegen
        final AccountManagerImpl ami = new AccountManagerImpl(spp);
        //AccountManager über Proxy anlegen
        final AccountManager am;
        final StockGameCommandProcessor commandProcessor;
        
        Properties properties = new Properties();
	InputStream inputStream = new FileInputStream("language.properties");
	properties.load(inputStream);
        Locale.setDefault(new Locale(properties.getProperty("user.language"),properties.getProperty("user.country")));
	System.setProperty("user.country",properties.getProperty("user.country"));
	System.out.println(Locale.getDefault());
        
        String language = new BufferedReader(new FileReader("language.properties")).readLine().split("=")[1];
        
	System.out.println(System.getProperty("user.country"));
	ResourceBundle resourceBundle = ResourceBundle.getBundle(language, Locale.getDefault());
	TimeZone.setDefault(null);
	System.setProperty("user.timezone",resourceBundle.getString("user.timezone"));
        Application.launch();
        
        try {
            am = (AccountManager) AccountManagerProxy.newInstance(ami);
//            final StockPriceViewer sviewer = new StockPriceViewer(spp);
//            final PlayerViewer pviewer = new PlayerViewer(am);
//            final AllViewer viewer = new AllViewer(am, resourceBundle);
            commandProcessor = new StockGameCommandProcessor(am);
//            sviewer.start();
//            pviewer.start();
//            viewer.start();  
//            commandProcessor.process();
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(StockGameLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                System.out.println();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        StockGameUI stockGameUI = new StockGameUI();
        stockGameUI.start(primaryStage);
    }
}