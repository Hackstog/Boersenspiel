/**
 * StockGameUI.java
 * Autor:   Daniel Heigl <daniel.heigl@hs-augsburg.de>
 * Datum:   14.07.2014
 * Version: 1.0
 */


package UI;
import AccountManager.AccountManagerImpl;
import Assets.Share;
import StockPrice.StockPriceProvider;
import StockPrice.HistoricalStockPriceProvider;
import StockPrice.RandomStockPriceProvider;
import StockPrice.RandomStockPriceProvider;
import StockPrice.OnlineStockPriceProvider;
import Player.Player;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Currency;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StockGameUI extends Application{
    private static StockPriceProvider spp = new HistoricalStockPriceProvider();
    static AccountManagerImpl am = new AccountManagerImpl(spp);
    static Timer timer = new java.util.Timer();
    private static TreeMap<Share, Integer> sharesOfPlayer = new TreeMap<>();
    static ResourceBundle rb = ResourceBundle.getBundle("de", Locale.getDefault());
    static DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00 ¤");	
 

    public StockGameUI(){
        spp.startUpdate();
        decimalFormat.setMinimumFractionDigits(2);
        
//        try {          
//            am.createPlayer("Daniel");
//            am.buyShare("Daniel", "Audi", 3);
//            am.buyShare("Daniel", "Google", 10);
//        } catch (Exception ex) {
//            Logger.getLogger(StockGameUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    /**
     * Definition der Elemente
     */
    private final Scene scene = new Scene(new VBox(), 800, 600);
    private final Scene popup = new Scene(new VBox(), 300, 200);
    private final Stage stage = new Stage();
    private final Stage secondaryStage = new Stage();
    private static final BorderPane borderPane = new BorderPane();
    private static final GridPane gridPane = new GridPane();
    private static final GridPane rightPane = new GridPane();
    private static final GridPane buttonPane = new GridPane();
    private static final GridPane buySellPane = new GridPane();
    private static final GridPane leftPane = new GridPane();
    private static final GridPane graphPane = new GridPane();
    static ScrollPane shareList = new ScrollPane();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuStockGame = new Menu(rb.getString("WindowTitle"));
    private final Menu menuPlayer = new Menu(rb.getString("MenuPlayer"));
    private final Menu menuSettings = new Menu(rb.getString("MenuSettings"));
    private final Menu language = new Menu(rb.getString("MenuItem_language"));
    private final Menu provider = new Menu(rb.getString("MenuItem_provider"));
    private final Menu menuHelp = new Menu("?");
    private final MenuItem exit = new MenuItem(rb.getString("MenuItem_exit"));
    private final MenuItem createPlayer = new MenuItem(rb.getString("MenuItem_createPlayer"));
    private final MenuItem deletePlayer = new MenuItem(rb.getString("MenuItem_deletePlayer"));
    private final MenuItem parameter = new MenuItem(rb.getString("MenuItem_parameter"));    
    private final MenuItem help = new MenuItem(rb.getString("MenuItem_help"));
    private final MenuItem about = new MenuItem(rb.getString("MenuItem_about"));
    private final ToggleGroup languages = new ToggleGroup();
    private final ToggleGroup providers = new ToggleGroup();
    private static final ToggleGroup shares = new ToggleGroup();
    private final RadioMenuItem lang_de = new RadioMenuItem("Deutsch");
    private final RadioMenuItem lang_enGB = new RadioMenuItem("Englisch (GB)");
    private final RadioMenuItem lang_enUS = new RadioMenuItem("Englisch (US)");
    private final RadioMenuItem lang_it = new RadioMenuItem("Italiano");
    private final RadioMenuItem lang_fr = new RadioMenuItem("Français");
    private final RadioMenuItem provider_random = new RadioMenuItem(rb.getString("Provider_random"));
    private final RadioMenuItem provider_historical = new RadioMenuItem(rb.getString("Provider_historical"));
    private final RadioMenuItem provider_online = new RadioMenuItem(rb.getString("Provider_online"));
    private final List<RadioButton> shareButtons = new ArrayList<>();
    private static final ObservableList<Player> playerDropDownData = FXCollections.observableArrayList();
    private static final ObservableList<String> shareDropDownData = FXCollections.observableArrayList();
    private static final ComboBox playerDropDown = new ComboBox();
    private final ComboBox shareDropDown = new ComboBox();    
    private final Button buy = new Button();
    private final Button sell = new Button();
    private final Button getTrans = new Button();
    private final Button agent = new Button();
    private final Button buyOk = new Button(rb.getString("Button_buy"));
    private final Button abbort = new Button(rb.getString("Button_abbort"));
    private final Button sellOk = new Button(rb.getString("Button_sell"));
    final static Label playerNameLabel = new Label();
    final static Label playerCashValue = new Label();
    final static Label status = new Label();
    final static Label shares_head = new Label (rb.getString("Shares"));
    final static Label choosenShare = new Label();
    final static NumberAxis xAxis = new NumberAxis("", 0, 20, 1);
    final static NumberAxis yAxis = new NumberAxis("", 0, 1200, 100);
    final static LineChart<Number,Number> chart = new LineChart<>(xAxis,yAxis);
    static String selectedShareToTrade;
    static int selectedBuyAmount;
    static TextField number = new TextField();

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(rb.getString("WindowTitle"));
        //Erzeugt das Menu
        menuStockGame.getItems().add(exit);
        menuPlayer.getItems().add(createPlayer);
        menuPlayer.getItems().add(deletePlayer);
        language.getItems().add(lang_de);
        language.getItems().add(lang_enUS);
        language.getItems().add(lang_enGB);
        language.getItems().add(lang_it);
        language.getItems().add(lang_fr);
        lang_de.setToggleGroup(languages);
        lang_enUS.setToggleGroup(languages);
        lang_enGB.setToggleGroup(languages);
        lang_it.setToggleGroup(languages);
        lang_fr.setToggleGroup(languages);
        languages.selectToggle(lang_de);
        menuSettings.getItems().add(language);
        provider.getItems().add(provider_random);
        provider.getItems().add(provider_historical);
        provider_random.setToggleGroup(providers);
        provider_historical.setToggleGroup(providers);
        provider_online.setToggleGroup(providers);
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
        rightPane.setPadding(new Insets(40, 10, 10, 10));
        buttonPane.setVgap(10);
        buttonPane.setHgap(10);
        buttonPane.setPadding(new Insets(0, 0, 0, 0));
        buySellPane.setVgap(10);
        buySellPane.setHgap(10);
        buySellPane.setPadding(new Insets(0, 0, 0, 0));
        
        
        //Liste der Aktien + Graph
        listShares();
        updateShares();
        graphPane.add(chart, 1, 1);
        
        
        //Aktienkurs als Graph
        xAxis.minHeight(250);
        xAxis.setMinorTickVisible(false);
        xAxis.setTickLabelsVisible(false);
        chart.minWidth(100);
        chart.maxWidth(100);
        chart.minHeight(100);
        chart.maxHeight(100);
        chart.setLegendVisible(false);
        chart.setAnimated(false);

        
        //Liste der Spieler als DropDown-Menü
        listPlayer();
        playerDropDown.setItems(playerDropDownData);
        playerDropDown.setMaxWidth(Double.MAX_VALUE);
        playerDropDown.setVisibleRowCount(7);
        rightPane.add(playerDropDown, 1, 1);
        
        
        //Aktien des Spielers als Scrollbare Liste
        shareList.setMinHeight(200);
        shareList.setMinWidth(500);
        playerNameLabel.setFont(new Font(16));
        rightPane.add(playerNameLabel, 1, 4);
        rightPane.add(playerCashValue, 1, 5);
        rightPane.add(shareList, 1, 6);
 

        //Buttons für Aktionen
        buy.setText(rb.getString("Button_buy"));
        buy.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(buy, 1, 1);
        getTrans.setText(rb.getString("Button_trans"));
        getTrans.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(getTrans, 2, 1);
        sell.setText(rb.getString("Button_sell"));
        sell.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(sell, 1, 2);
        agent.setText(rb.getString("Button_agent"));
        agent.setMaxWidth(Double.MAX_VALUE);
        buttonPane.add(agent, 2, 2);
        rightPane.add(buttonPane, 1, 7);
        
        
        //Buy/Sell Bereich
        rightPane.add(buySellPane, 1, 8);
        
        //Statusleiste 
       status.setPadding(new Insets(20, 0, 0, 10));


        gridPane.add(leftPane, 1, 1);
        gridPane.add(graphPane, 1, 2);
        borderPane.setTop(menuBar);
        borderPane.setCenter(rightPane);
        borderPane.setLeft(gridPane);
        borderPane.setBottom(status);
        
        ((VBox) scene.getRoot()).getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        secondaryStage.setScene(popup);
        
        
        
        //Event Handler
        /**
         * Ausgewälten Spieler ermitteln und seine Aktien anzeigen
         */
        playerDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> ov, Player old, Player neu) {
                if(neu != null){
                    playerNameLabel.setText(neu.toString());
                    listSharesOfPlayer(neu);
                    buySellPane.getChildren().clear();
                }
            }
        });
        
        
        /**
         * Ausgewählte Aktie ermittelnt und Graph erzeugen
         */
        shares.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old, Toggle neu) -> {
            RadioButton r = (RadioButton) neu.getToggleGroup().getSelectedToggle();
            choosenShare.setText(r.getText());
            try {
                Share s = spp.getShare(r.getText());
                List<Long> graphEntries = s.getLastRates();
                XYChart.Series series = new XYChart.Series();
                int j = 0;
                for(long l : graphEntries){
                    Double d = (l/100)/(Double.valueOf(rb.getString("CurrencyExchangeValue")));
                    series.getData().add(new XYChart.Data(j, d));
                    j++;
                }
                chart.getData().clear();
                chart.getData().add(series);
            } catch (Exception ex) {
            }
        });
        
        
        //Spiel beenden
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        
        //Spracheinstellung
        lang_de.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("de", Locale.GERMANY);
            status.setText("Sprache umgestellt auf Deutsch");
            decimalFormat.setCurrency(Currency.getInstance(Locale.GERMANY));
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY)); 
            setLanguage();
        });
        lang_enGB.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("enUS", Locale.US);
            status.setText("Language changed to englisch (Great Britain)");
            decimalFormat.setCurrency(Currency.getInstance(Locale.UK));
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.UK)); 
            setLanguage();
        });
        lang_enUS.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("enGB", Locale.US);
            status.setText("Language changed to englisch (United States)");
            decimalFormat.setCurrency(Currency.getInstance(Locale.US));
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US)); 
            setLanguage();
        });
        lang_it.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("it", Locale.ITALY);
            decimalFormat.setCurrency(Currency.getInstance(Locale.ITALY));
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ITALY));
            setLanguage();
        });
        lang_fr.setOnAction((ActionEvent t) -> {
            rb = ResourceBundle.getBundle("fr", Locale.FRANCE);
            decimalFormat.setCurrency(Currency.getInstance(Locale.FRANCE));
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
            setLanguage();
        });
        
        //Providereinstellung
        provider_historical.setOnAction((ActionEvent t) -> {
            StockGameUI.spp = new HistoricalStockPriceProvider();
            spp.startUpdate();
            status.setText(rb.getString("Status_providerHist"));
        });
        provider_random.setOnAction((ActionEvent t) -> {
           StockGameUI.spp = new RandomStockPriceProvider();
           spp.startUpdate();
           status.setText(rb.getString("Status_providerRand"));
        });
        provider_online.setOnAction((ActionEvent t) -> {
            StockGameUI.spp = new OnlineStockPriceProvider();
            spp.startUpdate();
            status.setText(rb.getString("Status_providerOnline"));
        });
        
        //About-Popup
        about.setOnAction((ActionEvent t) -> {
            AboutPopUp about = new AboutPopUp();
            about.start(secondaryStage);
            secondaryStage.show();
        });
        
        //Help-Popup
        help.setOnAction((ActionEvent t) -> {
            HelpPopUp help = new HelpPopUp();
            help.start(secondaryStage);
            secondaryStage.show();
        });
        
        //Player-Popup
        createPlayer.setOnAction((ActionEvent t) -> {
            CreatePlayerPopUp player = new CreatePlayerPopUp();
            player.start(secondaryStage);
            secondaryStage.show();
        });
        deletePlayer.setOnAction((ActionEvent t) -> {
            DeletePlayerPopUp player = new DeletePlayerPopUp();
            player.start(secondaryStage);
            secondaryStage.show();
        });
        
        //Parameter-Popup
        parameter.setOnAction((ActionEvent t) -> {
            ParameterPopUp params = new ParameterPopUp();
            params.start(secondaryStage);
            secondaryStage.show();
        });
        
        //Buttons
        //Transaktion-Button
        getTrans.setOnAction((ActionEvent ) ->{
            if(playerNameLabel.getText() != null){
                try {
                    am.getTransactions(playerNameLabel.getText(), "t", "html");
                    status.setTextFill(Color.web("green"));
                    status.setText(rb.getString("Status_exportSuccess"));
                } catch (Exception ex) {
                    status.setTextFill(Color.web("red"));
                    status.setText(rb.getString("Status_exportError"));
                }
            }
        });
        
        //StartAgent-Button
        agent.setOnAction((ActionEvent ) ->{
            if(playerNameLabel.getText() != null){
                try {
                    Player p = am.getPlayer(playerNameLabel.getText());
                    am.startAgent(p.getName());
                    status.setTextFill(Color.web("green"));
                    status.setText(rb.getString("Status_agentSuccess"));
                } catch (Exception ex) {
                    status.setTextFill(Color.web("red"));
                    status.setText(rb.getString("Status_agentError"));
                }
            }
        });
        
        //Buy-Button
        buy.setOnAction((ActionEvent t) -> {
            buySellPane.getChildren().clear();
            shareDropDownData.clear();
            for(Share s : spp.getAllSharesAsSnapshot()){
                shareDropDownData.add(s.getName());
            }
            shareDropDown.setItems(shareDropDownData);
            shareDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old, String neu) {
                    selectedShareToTrade = neu;
                }
            });
            buySellPane.add(shareDropDown, 1, 1);
            buySellPane.add(number, 2, 1);
            buySellPane.add(buyOk, 3, 1);
            buyOk.setDefaultButton(true);
            buySellPane.add(abbort, 4, 1);
        });
        
        
        //Buy-Ok-Button
        buyOk.setOnAction((ActionEvent t) -> {       
            try {
                int n = Integer.parseInt(number.getText());
                am.buyShare(playerNameLabel.getText(), selectedShareToTrade, n);
                status.setTextFill(Color.web("green"));
                status.setText(rb.getString("Status_bought"));
                number.setText("");
                buySellPane.getChildren().clear();
            } catch (Exception ex) {
                status.setTextFill(Color.web("red"));
                status.setText(rb.getString("Status_buyError"));
            }
        });
        
        
        //Sell-Button
        sell.setOnAction((ActionEvent t) -> {
            buySellPane.getChildren().clear();
            shareDropDownData.clear();
            for(Map.Entry<Share, Integer> entry : sharesOfPlayer.entrySet()){
                Share s = entry.getKey();
                shareDropDownData.add(s.getName());
            }
            shareDropDown.setItems(shareDropDownData);
            shareDropDown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old, String neu) {
                    selectedShareToTrade = neu;
                }
            });
            buySellPane.add(shareDropDown, 1, 1);
            buySellPane.add(number, 2, 1);
            buySellPane.add(sellOk, 3, 1);
            sellOk.setDefaultButton(true);
            buySellPane.add(abbort, 4, 1);
        });

        //Buy-Ok-Button
        sellOk.setOnAction((ActionEvent t) -> {       
            try {
                int n = Integer.parseInt(number.getText());
                am.sellShare(playerNameLabel.getText(), selectedShareToTrade, n);
                status.setTextFill(Color.web("green"));
                status.setText(rb.getString("Status_sold"));
                number.setText("");
                buySellPane.getChildren().clear();
            } catch (Exception ex) {
                status.setTextFill(Color.web("red"));
                status.setText(rb.getString("Status_sellError"));
            }
        });
        
        //Abbort-Button
        abbort.setOnAction((ActionEvent t) -> {
            buySellPane.getChildren().clear();
        });
        
        //Programm beenden, wenn Fenster geschlossen wird
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
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
        abbort.setText(rb.getString("Button_abbort"));
        buyOk.setText(rb.getString("Button_buy"));
        sellOk.setText(rb.getString("Button_sell"));
        if(!(playerCashValue.getText().equals(""))){
            playerCashValue.setText(rb.getString("CashAccountValue")+" "+playerCashValue.getText().split(": ")[1].split(" ")[0]);
        }
        listShares();
    }
    
    public static void listPlayer(){
        playerDropDownData.clear();
        am.getPlayer().stream().forEach((p) -> {
            playerDropDownData.add(p);
        });
    }
    
    public static void listShares(){
        leftPane.getChildren().clear();
        Text head = new Text(rb.getString("Shares"));
        head.setFont(new Font(16));
        leftPane.add(head, 1, 1);
        int i = 2;
        for(Share s : spp.getAllSharesAsSnapshot()){
            RadioButton r = new RadioButton(s.getName());
            if(s.getName().equals(choosenShare.getText())){
                r.setSelected(true);
            }
            r.setToggleGroup(shares);
            leftPane.add(r, 1, i);
            String value = decimalFormat.format(((double) (s.getWert()/100))/Double.valueOf(rb.getString("CurrencyExchangeValue")));
            Label kurs = new Label(value);
            leftPane.add(kurs, 15, i);
            i++;
        }
    }
    
    public static void listSharesOfPlayer(Player p){
        String value = decimalFormat.format(((double) (p.getCashAccount().getWert()/100)));
        playerCashValue.setText(rb.getString("CashAccountValue")+" "+value);
        sharesOfPlayer = p.getDepositAccount().getPakete();
        GridPane list = new GridPane();
        list.setHgap(5);
        list.setVgap(5);
        int j = 1;
        for(Map.Entry<Share, Integer> entry : sharesOfPlayer.entrySet()){
            try {
                Text name = new Text(entry.getKey().toString());
                Text anzahl = new Text(String.valueOf(entry.getValue()));
                double shareValue = (am.getShare(entry.getKey().getName()).getWert() * entry.getValue())/(Double.valueOf(rb.getString("CurrencyExchangeValue")));
                Text wert = new Text(decimalFormat.format((double) shareValue/100));
                list.add(name, 1, j);
                list.add(anzahl, 16, j);
                list.add(wert, 31, j);
                j++;
            } catch (Exception ex) {
                Logger.getLogger(StockGameUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        shareList.setContent(list);
    }
    
    public static void updateShares(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    listShares();
                    try {
                        listSharesOfPlayer(am.getPlayer(playerNameLabel.getText()));
                    } catch (Exception ex) {
                    }
                });
            }
        }, 1000, 1000);
    }
}