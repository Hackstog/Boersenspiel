/**
 * StockGameUI.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package UI;
import AccountManager.AccountManager;
import AccountManager.AccountManagerImpl;
import Assets.Share;
import StockPrice.StockPriceProvider;
import StockPrice.HistoricalStockPriceProvider;
import Timer.StockTimer;
import Player.Player;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StockGameUI extends Application{
    final StockPriceProvider spp;
    final AccountManagerImpl am;
    private final StockTimer timer = StockTimer.getInstance();
    private static final int TICK_PERIOD = 1000;
    private final Timer ticker = timer.getTimer();
    private final ResourceBundle rb = ResourceBundle.getBundle("de", Locale.getDefault());
    private String lang;
    DecimalFormat decimalFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.getDefault()));	

    public StockGameUI(){
        this.spp = new HistoricalStockPriceProvider();
        this.am = new AccountManagerImpl(spp);
        
        am.createPlayer("Tim");
        am.createPlayer("Daniel");
    }
    
    private final Scene scene = new Scene(new VBox(), 800, 600);
    private final GridPane gridPane = new GridPane();
    private final Stage stage = new Stage();
    private final MenuBar menuBar = new MenuBar();
    private final List<RadioButton> radioButtons = new ArrayList<>();
    private final ComboBox playerDropDown = new ComboBox();
    
    /**
     * Definition des Menüs
     */
    private final Menu menuStockGame = new Menu(rb.getString("WindowTitle"));
    private final MenuItem exit = new MenuItem(rb.getString("MenuItem_exit"));
    private final Menu menuPlayer = new Menu(rb.getString("MenuPlayer"));
    private final MenuItem createPlayer = new MenuItem(rb.getString("MenuItem_createPlayer"));
    private final MenuItem deletePlayer = new MenuItem(rb.getString("MenuItem_deletePlayer"));  
    private final Menu menuSettings = new Menu(rb.getString("MenuSettings"));
    private final MenuItem language = new MenuItem(rb.getString("MenuItem_language"));
    private final MenuItem provider = new MenuItem(rb.getString("MenuItem_provider"));
    private final MenuItem parameter = new MenuItem(rb.getString("MenuItem_parameter"));
    private final Menu menuHelp = new Menu("?");
    private final MenuItem help = new MenuItem(rb.getString("MenuItem_help"));
    private final MenuItem about = new MenuItem(rb.getString("MenuItem_about"));

    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(rb.getString("WindowTitle"));
        //Erzeugt das Menu
        menuStockGame.getItems().add(exit);
        menuPlayer.getItems().add(createPlayer);
        menuPlayer.getItems().add(deletePlayer);
        menuSettings.getItems().add(language);
        menuSettings.getItems().add(parameter);
        menuHelp.getItems().add(help);
        menuHelp.getItems().add(about);
        menuBar.getMenus().addAll(menuStockGame, menuPlayer, menuSettings, menuHelp);
        
        //Legt ein GridPane an
        gridPane.setVgap(10);
        gridPane.setHgap(50);
        
        //Liste der Aktien als RadioButtons
        int i = 1;
        for(Share s : spp.getAllSharesAsSnapshot()){
            RadioButton r = new RadioButton(s.getName());
            gridPane.add(r, 1, i);
            Text kurs = new Text(String.valueOf(decimalFormat.format((double) s.getWert()/100))+" "+rb.getString("Currency"));
            gridPane.add(kurs, 2, i);
            i++;
        }

        //Liste der Spieler als DropDown-Menü
        for(Player p : am.getPlayer()){
            playerDropDown.getItems().add(p.getName());
        }
        gridPane.add(playerDropDown, 3, 1);
        int j = 2;
        Player p = (Player) playerDropDown.getValue();
        System.out.println(p);
        
        //Spielerinformationen
        if(p != null){
            Text name = new Text(p.getName());
            gridPane.add(name, 3, i);
            j++;
            Text cash = new Text(String.valueOf(p.getCashAccount().getWert()));
            gridPane.add(name, 3, i);
            j++;
            
        }

        
        //Buttons für Aktionen
        Button buy = new Button(rb.getString("Button_buy"));
        buy.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(buy, 3, j);
        Button getTrans = new Button(rb.getString("Button_trans"));
        getTrans.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(getTrans, 4, j);
        j++;
        Button sell = new Button(rb.getString("Button_sell"));
        sell.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(sell, 3, j);
        Button agent = new Button(rb.getString("Button_agent"));
        agent.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(agent, 4, j);
        j++;
        
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
