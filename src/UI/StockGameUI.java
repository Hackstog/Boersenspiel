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
import Assets.ShareItem;
import Assets.ShareDepositAccount;
import StockPrice.StockPriceProvider;
import StockPrice.HistoricalStockPriceProvider;
import StockPrice.RandomStockPriceProvider;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javafx.scene.layout.ColumnConstraints;


public class StockGameUI extends Application{
    private StockPriceProvider spp;
    final AccountManagerImpl am;
    private final StockTimer timer = StockTimer.getInstance();
    private List<ShareItem> sharesOfPlayer = new ArrayList<>();
    private static final int TICK_PERIOD = 1000;
    private final Timer ticker = timer.getTimer();
    static ResourceBundle rb = ResourceBundle.getBundle("de", Locale.getDefault());
    private String lang;
    DecimalFormat decimalFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.getDefault()));	

    public StockGameUI(){
        this.spp = new HistoricalStockPriceProvider();
        this.am = new AccountManagerImpl(spp);
        
        am.createPlayer("Tim");
        am.createPlayer("Daniel");
        try{
            am.buyShare("Daniel", "Audi", 10);
            am.buyShare("Daniel", "Apple", 30);
            am.buyShare("Daniel", "Google", 5);
            am.buyShare("Daniel", "Siemens", 15);
            am.buyShare("Daniel", "Microsoft", 10);
            am.buyShare("Daniel", "VW", 40);
            am.buyShare("Daniel", "BMW", 50);
            am.buyShare("Tim", "Microsoft", 24);
            am.buyShare("Tim", "BMW", 50);
        }catch(Exception e){
        }
        
    }
    
    /**
     * Definition der Elemente
     */
    private final Scene scene = new Scene(new VBox(), 800, 600);
    private final GridPane gridPane = new GridPane();
    private final GridPane buttonPane = new GridPane();
    private final GridPane leftPane = new GridPane();
    private final GridPane rightPane = new GridPane();
    private final Stage stage = new Stage();
    private final MenuBar menuBar = new MenuBar();
    private final List<RadioButton> radioButtons = new ArrayList<>();
    private final ComboBox playerDropDown = new ComboBox();
    private ObservableList<Player> playerDropDownData = FXCollections.observableArrayList();
    private final Menu menuStockGame = new Menu(rb.getString("WindowTitle"));
    private final MenuItem exit = new MenuItem(rb.getString("MenuItem_exit"));
    private final Menu menuPlayer = new Menu(rb.getString("MenuPlayer"));
    private final MenuItem createPlayer = new MenuItem(rb.getString("MenuItem_createPlayer"));
    private final MenuItem deletePlayer = new MenuItem(rb.getString("MenuItem_deletePlayer"));  
    private final Menu menuSettings = new Menu(rb.getString("MenuSettings"));
    private final Menu language = new Menu(rb.getString("MenuItem_language"));
    private final ToggleGroup languages = new ToggleGroup();
    RadioMenuItem lang_de = new RadioMenuItem("Deutsch");
    RadioMenuItem lang_en = new RadioMenuItem("Englisch");
    RadioMenuItem lang_it = new RadioMenuItem("Italiano");
    RadioMenuItem lang_fr = new RadioMenuItem("Français");
    private final Menu provider = new Menu(rb.getString("MenuItem_provider"));
    private final ToggleGroup providers = new ToggleGroup();
    RadioMenuItem provider_random = new RadioMenuItem(rb.getString("Provider_random"));
    RadioMenuItem provider_historical = new RadioMenuItem(rb.getString("Provider_historical"));
    private final MenuItem parameter = new MenuItem(rb.getString("MenuItem_parameter"));
    private final Menu menuHelp = new Menu("?");
    private final MenuItem help = new MenuItem(rb.getString("MenuItem_help"));
    private final MenuItem about = new MenuItem(rb.getString("MenuItem_about"));
    Button buy = new Button();
    Button sell = new Button();
    Button getTrans = new Button();
    Button agent = new Button();
    final Label playerNameLabel = new Label();
    final Label playerCashValue = new Label();
    final Label shares_head = new Label (rb.getString("Shares"));

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(rb.getString("WindowTitle"));
        //Erzeugt das Menu
        menuStockGame.getItems().add(exit);
        menuPlayer.getItems().add(createPlayer);
        menuPlayer.getItems().add(deletePlayer);
        language.getItems().add(lang_de);
        language.getItems().add(lang_en);
        language.getItems().add(lang_it);
        language.getItems().add(lang_fr);
        lang_de.setToggleGroup(languages);
        lang_en.setToggleGroup(languages);
        lang_it.setToggleGroup(languages);
        lang_fr.setToggleGroup(languages);
        languages.selectToggle(lang_de);
        menuSettings.getItems().add(language);
        provider.getItems().add(provider_random);
        provider.getItems().add(provider_historical);
        provider_random.setToggleGroup(providers);
        provider_historical.setToggleGroup(providers);
        providers.selectToggle(provider_historical);
        menuSettings.getItems().add(provider);
        menuSettings.getItems().add(parameter);
        menuHelp.getItems().add(help);
        menuHelp.getItems().add(about);
        menuBar.getMenus().addAll(menuStockGame, menuPlayer, menuSettings, menuHelp);
        
        //Legt ein GridPane an
        gridPane.setVgap(10);
        gridPane.setHgap(20);
        leftPane.setVgap(10);
        leftPane.setHgap(5);
        rightPane.setVgap(10);
        rightPane.setHgap(10);
        buttonPane.setVgap(10);
        buttonPane.setHgap(10);
        buttonPane.setPadding(new Insets(0, 0, 0, 0));
        
        //Liste der Aktien als RadioButtons
        int i = 2;
        shares_head.setFont(new Font(16));
        leftPane.add(shares_head, 1, 1);
        ToggleGroup shares = new ToggleGroup();
        for(Share s : spp.getAllSharesAsSnapshot()){
            RadioButton r = new RadioButton(s.getName());
            r.setToggleGroup(shares);
            leftPane.add(r, 1, i);
            String value = decimalFormat.format(((double) (s.getWert()/100))*Double.valueOf(rb.getString("CurrencyExchangeValue")));
            Text kurs = new Text(value+" "+rb.getString("Currency"));
            leftPane.add(kurs, 10, i);
            i++;
        }
        
        
        //Liste der Spieler als DropDown-Menü
        for(Player p : am.getPlayer()){
            playerDropDownData.add(p);
        }
        playerDropDown.setItems(playerDropDownData);
        playerDropDown.setMaxWidth(Double.MAX_VALUE);
        rightPane.add(playerDropDown, 1, 1);
        
        
        //Aktien des Spielers als Scrollbare Liste
        ScrollPane shareList = new ScrollPane();

        shareList.setMinHeight(200);
        shareList.setMinWidth(400);
        
        playerNameLabel.setFont(new Font(16));
        rightPane.add(playerNameLabel, 1, 2);
        rightPane.add(playerCashValue, 1, 3);
        rightPane.add(shareList, 1, 4);
 

        int j = 6;
        //Buttons für Aktionen
        buy.setText(rb.getString("Button_buy"));
        buy.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(buy, 1, 1);
        getTrans.setText(rb.getString("Button_trans"));
        getTrans.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(getTrans, 2, 1);
        j++;
        sell.setText(rb.getString("Button_sell"));
        sell.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(sell, 1, 2);
        agent.setText(rb.getString("Button_agent"));
        agent.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(agent, 2, 2);
        rightPane.add(buttonPane, 1, 5);
        j++;
        
        gridPane.add(leftPane, 1, 1);
        gridPane.add(rightPane, 2, 1);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        //Event Handler
        //Ausgewählten Spieler ermitteln
        String playerName;
        
        playerDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> ov, Player old, Player neu) {
                playerNameLabel.setText(neu.toString());
                String value = decimalFormat.format(((double) (neu.getCashAccount().getWert()/100))*Double.valueOf(rb.getString("CurrencyExchangeValue")));
                playerCashValue.setText(rb.getString("CashAccountValue")+" "+value+" "+rb.getString("Currency"));
                sharesOfPlayer = neu.getDepositAccount().getPakete();
                GridPane list = new GridPane();
                list.setHgap(5);
                list.setVgap(5);
                int j = 1;
                for(ShareItem paket : sharesOfPlayer){
                    Text name = new Text(paket.getName());
                    Text anzahl = new Text(String.valueOf(paket.getAnzahl()));
                    list.add(name, 1, j);
                    list.add(anzahl, 20, j);
                    j++;
                }
               shareList.setContent(list);
            }
        });
                
        //Spiel beenden
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        
        //Spracheinstellung
        lang_de.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("de", Locale.getDefault());
            setLanguage();
        });
        lang_en.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("en", Locale.getDefault());
            setLanguage();
        });
        lang_it.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("it", Locale.getDefault());
            setLanguage();
        });
        lang_fr.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("fr", Locale.getDefault());
            setLanguage();
        });
        
        //Providereinstellung
        provider_historical.setOnAction((ActionEvent t) -> {
            this.spp = new HistoricalStockPriceProvider();
            System.out.println("Hist");
        });
        provider_random.setOnAction((ActionEvent t) -> {
           this.spp = new RandomStockPriceProvider();
           System.out.println("Rand");
        });
        
        //About-Popup
        about.setOnAction((ActionEvent t) -> {
            AboutPopUp about = new AboutPopUp();
            about.get().show();
        });
        
        //Help-Popup
        help.setOnAction((ActionEvent t) -> {
            HelpPopUp help = new HelpPopUp();
            help.get().show();
        });
    }
    
    
    private void setLanguage(){
        menuStockGame.setText(rb.getString("WindowTitle"));
        exit.setText(rb.getString("MenuItem_exit"));
        menuPlayer.setText(rb.getString("MenuPlayer"));
        createPlayer.setText(rb.getString("MenuItem_createPlayer"));
        deletePlayer.setText(rb.getString("MenuItem_deletePlayer"));  
        menuSettings.setText(rb.getString("MenuSettings"));
        language.setText(rb.getString("MenuItem_language"));
        provider.setText(rb.getString("MenuItem_provider"));
        provider_random.setText(rb.getString("Provider_random"));
        provider_historical.setText(rb.getString("Provider_historical"));
        parameter.setText(rb.getString("MenuItem_parameter"));
        help.setText(rb.getString("MenuItem_help"));
        about.setText(rb.getString("MenuItem_about"));
        buy.setText(rb.getString("Button_buy"));
        sell.setText(rb.getString("Button_sell"));
        getTrans.setText(rb.getString("Button_trans"));
        agent.setText(rb.getString("Button_agent"));
        shares_head.setText(rb.getString("Shares"));
        if(!(playerCashValue.getText().equals(""))){
            playerCashValue.setText(rb.getString("CashAccountValue")+" "+playerCashValue.getText().split(": ")[1].split(" ")[0]+" "+rb.getString("Currency"));
        }
    }
}
