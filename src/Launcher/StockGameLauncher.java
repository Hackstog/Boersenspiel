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
import AccountManager.AccountManagerImpl;
import Logging.AccountManagerProxy;
import AccountManager.AccountManager;
import Viewer.StockPriceViewer;
import Viewer.PlayerViewer;
import Viewer.AllViewer;
import Exceptions.PlayerExistsException;
import Command.StockGameCommandProcessor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GameLauncher-Klasse mit main-Methode, die das Spiel startet
 */
public class StockGameLauncher {
    public static void main (String[] args){
        final RandomStockPriceProvider spp= new RandomStockPriceProvider();
        //AccountManagerImpl direkt anlegen
        final AccountManagerImpl ami = new AccountManagerImpl(spp);
        //AccountManager über Proxy anlegen
        final AccountManager am;
        final AllViewer viewer;
        final StockGameCommandProcessor commandProcessor;
        
        
        try {
            am = (AccountManager) AccountManagerProxy.newInstance(ami);
//        final StockPriceViewer sviewer = new StockPriceViewer(spp);
//        final PlayerViewer pviewer = new PlayerViewer(am);
            viewer = new AllViewer(am);
            commandProcessor = new StockGameCommandProcessor(am);
            

            spp.startUpdate();
            //sviewer.start();
            //pviewer.start();
            viewer.start();  

            try{
                am.createPlayer("Daniel");
                am.buyShare("Daniel", "Apple", 10);
                am.buyShare("Daniel", "Audi", 20);
                am.sellShare("Daniel", "Apple", 8);
                am.createPlayer("Tim");
                am.buyShare("Tim", "Siemens", 50);
                commandProcessor.process();
            }catch(Exception e){
                e.printStackTrace(System.out);
            }

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(StockGameLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                System.out.println();
        }
    }
}